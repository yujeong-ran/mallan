package com.mallan.yujeongran.icebreaking.liar_game.service;

import com.mallan.yujeongran.common.exception.DuplicateJoinException;
import com.mallan.yujeongran.icebreaking.liar_game.dto.response.LiarGameResultResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LiarRedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public String createPlayer(String nickname, String profileImage) {
        String playerId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        redisTemplate.opsForValue().set("player:" + playerId + ":nickname", nickname);
        redisTemplate.opsForValue().set("player:" + playerId + ":profileImage", profileImage);
        redisTemplate.expire("player:" + playerId + ":nickname", Duration.ofHours(24));
        redisTemplate.expire("player:" + playerId + ":profileImage", Duration.ofHours(24));
        return playerId;
    }

    public String getNickname(String playerId) {
        return redisTemplate.opsForValue().get("player:" + playerId + ":nickname");
    }

    public String getProfileImage(String playerId) {
        return redisTemplate.opsForValue().get("player:" + playerId + ":profileImage");
    }

    public void createRoom(String roomCode, String hostId) {
        String roomKey = "room:" + roomCode;
        String playerListKey = "room:" + roomCode + ":players";

        redisTemplate.opsForHash().put(roomKey, "hostId", hostId);

        redisTemplate.opsForList().rightPush(playerListKey, hostId);

        redisTemplate.expire(roomKey, Duration.ofHours(24));
        redisTemplate.expire(playerListKey, Duration.ofHours(24));
    }

    public boolean existsPlayer(String playerId) {
        return redisTemplate.hasKey("player:" + playerId);
    }

    public void joinRoom(String roomCode, String playerId, String nickname) {
        List<String> playerIds = redisTemplate.opsForList().range("room:" + roomCode + ":players", 0, -1);
        if (playerIds != null && playerIds.contains(playerId)) {
            throw new DuplicateJoinException("이미 입장 중인 사용자입니다.");
        }
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
        redisTemplate.delete("room:" + roomCode + ":votes");
        redisTemplate.delete("room:" + roomCode + ":liarGuess");
        redisTemplate.delete(playerListKey);

    }

    public String determineLiar(String roomCode) {
        Map<String, Long> tally = redisTemplate.opsForHash().entries("room:" + roomCode + ":votes")
                .values().stream()
                .collect(Collectors.groupingBy(v -> (String) v, Collectors.counting()));

        String guessedLiar = Collections.max(tally.entrySet(), Map.Entry.comparingByValue()).getKey();

        return guessedLiar;
    }

    public void saveVote(String roomCode, String voterId, String targetId) {
        redisTemplate.opsForHash().put("room:" + roomCode + ":votes", voterId, targetId);
    }

    public Map<Object, Object> getVotes(String roomCode) {
        return redisTemplate.opsForHash().entries("room:" + roomCode + ":votes");
    }

    public void saveGuess(String roomCode, String word) {
        redisTemplate.opsForValue().set("room:" + roomCode + ":liarGuess", word);
    }

    public String getGuess(String roomCode) {
        return redisTemplate.opsForValue().get("room:" + roomCode + ":liarGuess");
    }

    public LiarGameResultResponseDto getGameResult(String roomCode, String realWord, String actualLiarId) {

        Map<Object, Object> votes = getVotes(roomCode);
        String votedLiarId = determineLiar(roomCode);
        String liarGuess = getGuess(roomCode);

        boolean liarGuessedCorrectly = realWord.equals(liarGuess);
        boolean liarWasIdentified = actualLiarId != null && actualLiarId.equals(votedLiarId);

        String winner;
        if (liarWasIdentified) {
            winner = liarGuessedCorrectly ? "LIAR" : "CITIZEN";
        } else {
            winner = "LIAR";
        }

        List<String> voteSummary = votes.entrySet().stream()
                .map(entry -> entry.getKey() + " -> " + entry.getValue())
                .collect(Collectors.toList());

        LiarGameResultResponseDto result = new LiarGameResultResponseDto();
        result.setWinnerType(winner);
        result.setRealWord(realWord);
        result.setLiarGuess(liarGuess);
        result.setVotes(voteSummary);

        return result;
    }

    public void assignLiars(String roomCode, List<String> liarIds) {
        String rolesKey = "room:" + roomCode + ":roles";
        List<String> allPlayerIds = getAllPlayerIds(roomCode);

        for (String playerId : allPlayerIds) {
            String role = liarIds.contains(playerId) ? "LIAR" : "CITIZEN";
            redisTemplate.opsForHash().put(rolesKey, playerId, role);
        }

        redisTemplate.expire(rolesKey, Duration.ofHours(24));
    }

    public List<String> getAllPlayerIds(String roomCode) {
        return redisTemplate.opsForList().range("room:" + roomCode + ":players", 0, -1);
    }

    public boolean isLiar(String roomCode, String playerId) {
        Object role = redisTemplate.opsForHash().get("room:" + roomCode + ":roles", playerId);
        return "LIAR".equals(role);
    }

    public String getVotedLiarId(String roomCode) {
        return determineLiar(roomCode);
    }

    public List<String> getLiarIds(String roomCode) {
        String key = "room:" + roomCode + ":roles";
        return redisTemplate.opsForHash()
                .entries(key).entrySet().stream()
                .filter(e -> "LIAR".equals(e.getValue()))
                .map(e -> e.getKey().toString())
                .collect(Collectors.toList());
    }

    public void resetGameSession(String roomCode) {
        redisTemplate.delete("room:" + roomCode + ":roles");
        redisTemplate.delete("room:" + roomCode + ":votes");
        redisTemplate.delete("room:" + roomCode + ":liarGuess");
    }

}
