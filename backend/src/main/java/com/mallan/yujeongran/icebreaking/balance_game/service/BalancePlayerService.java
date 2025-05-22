package com.mallan.yujeongran.icebreaking.balance_game.service;

import com.mallan.yujeongran.common.exception.DuplicateJoinException;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.BalanceJoinRoomRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BalancePlayerService {

    private final RedisTemplate<String, String> redisTemplate;

    public void createPlayer(String playerId, String nickname, String profileImage) {
        redisTemplate.opsForValue().set("player:" + playerId + ":nickname", nickname);
        redisTemplate.opsForValue().set("player:" + playerId + ":profileImage", profileImage);
        redisTemplate.expire("player:" + playerId + ":nickname", Duration.ofHours(24));
        redisTemplate.expire("player:" + playerId + ":profileImage", Duration.ofHours(24));
    }

    public void createRoom(String roomCode, String hostId) {
        String roomKey = "room:" + roomCode;
        String playerListKey = roomKey + ":players";

        redisTemplate.opsForHash().put(roomKey, "hostId", hostId);
        redisTemplate.opsForList().rightPush(playerListKey, hostId);
        redisTemplate.expire(roomKey, Duration.ofHours(24));
        redisTemplate.expire(playerListKey, Duration.ofHours(24));
    }

    public void joinRoom(String roomCode, String playerId, BalanceJoinRoomRequestDto request) {
        String playerListKey = "room:" + roomCode + ":players";
        List<String> players = redisTemplate.opsForList().range(playerListKey, 0, -1);

        if (players != null && players.contains(playerId)) {
            throw new DuplicateJoinException("이미 입장한 플레이어입니다.");
        }

        redisTemplate.opsForList().rightPush(playerListKey, playerId);
        redisTemplate.opsForValue().set("player:" + playerId + ":nickname", request.getNickname());
        redisTemplate.opsForValue().set("player:" + playerId + ":profileImage", request.getProfileImage());
        redisTemplate.expire("player:" + playerId + ":nickname", Duration.ofHours(24));
        redisTemplate.expire("player:" + playerId + ":profileImage", Duration.ofHours(24));
    }


    public void exitRoom(String roomCode, String playerId) {
        String playerListKey = "room:" + roomCode + ":players";
        redisTemplate.opsForList().remove(playerListKey, 1, playerId);
        redisTemplate.delete("player:" + playerId + ":nickname");
        redisTemplate.delete("player:" + playerId + ":profileImage");

        Long remaining = redisTemplate.opsForList().size(playerListKey);
        if (remaining == null || remaining == 0) {
            deleteRoom(roomCode);
        }
    }

    public void deleteRoom(String roomCode) {
        String roomKey = "room:" + roomCode;
        String playerListKey = roomKey + ":players";

        List<String> players = redisTemplate.opsForList().range(playerListKey, 0, -1);
        if (players != null) {
            for (String playerId : players) {
                redisTemplate.delete("player:" + playerId + ":nickname");
                redisTemplate.delete("player:" + playerId + ":profileImage");
            }
        }

        redisTemplate.delete(roomKey);
        redisTemplate.delete(playerListKey);
    }

    public boolean isHost(String roomCode, String playerId) {
        String hostId = (String) redisTemplate.opsForHash().get("room:" + roomCode, "hostId");
        return playerId.equals(hostId);
    }

    public int getPlayerCount(String roomCode) {
        Long count = redisTemplate.opsForList().size("room:" + roomCode + ":players");
        return count != null ? count.intValue() : 0;
    }

    public String getNickname(String playerId) {
        return redisTemplate.opsForValue().get("player:" + playerId + ":nickname");
    }

    public String getProfileImage(String playerId) {
        return redisTemplate.opsForValue().get("player:" + playerId + ":profileImage");
    }

}

