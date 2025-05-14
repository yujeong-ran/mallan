package com.mallan.yujeongran.icebreaking.liar_game.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class LiarRedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public String createPlayer(String nickname) {
        String playerId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        redisTemplate.opsForValue().set("player:" + playerId, nickname);
        redisTemplate.expire("player:" + playerId, Duration.ofHours(24));
        return playerId;
    }

    public String getNickname(String playerId) {
        return redisTemplate.opsForValue().get("player:" + playerId);
    }

    public void createRoom(String roomCode, String hostId) {
        String roomKey = "room:" + roomCode;
        String playerListKey = "room:" + roomCode + ":players";

        // host 정보 저장
        redisTemplate.opsForHash().put(roomKey, "hostId", hostId);

        // host도 플레이어 목록에 추가
        redisTemplate.opsForList().rightPush(playerListKey, hostId);

        // TTL: 24시간 후 만료 (초 단위)
        redisTemplate.expire(roomKey, Duration.ofHours(24));
        redisTemplate.expire(playerListKey, Duration.ofHours(24));
    }

    public void joinRoom(String roomCode, String playerId, String nickname) {
        redisTemplate.opsForValue().set("player:" + playerId, nickname);
        redisTemplate.opsForList().rightPush("room:" + roomCode + ":players", playerId);
    }

    public void ExitRoom(String roomCode, String playerId) {
        redisTemplate.opsForList().remove("room:" + roomCode + ":players", 1, playerId);
        redisTemplate.delete("player:" + playerId);
    }

    public boolean isHost(String roomCode, String hostId) {
        String savedHostId = (String) redisTemplate.opsForHash().get("room:" + roomCode, "hostId");
        return hostId.equals(savedHostId);
    }

    public int getPlayerCount(String roomCode) {
        Long size = redisTemplate.opsForList().size("room:" + roomCode + ":players");
        return size != null ? size.intValue() : 0;
    }

    public void deleteRoom(String roomCode) {
        String playerListKey = "room:" + roomCode + ":players";
        List<String> players = redisTemplate.opsForList().range(playerListKey, 0, -1);

        if (players != null) {
            for (String playerId : players) {
                redisTemplate.delete("player:" + playerId);
            }
        }

        redisTemplate.delete("room:" + roomCode);
        redisTemplate.delete(playerListKey);
    }


}
