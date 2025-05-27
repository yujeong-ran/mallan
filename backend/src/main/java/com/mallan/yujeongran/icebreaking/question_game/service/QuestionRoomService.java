package com.mallan.yujeongran.icebreaking.question_game.service;

import com.mallan.yujeongran.icebreaking.question_game.dto.request.*;
import com.mallan.yujeongran.icebreaking.question_game.dto.response.*;
import com.mallan.yujeongran.icebreaking.question_game.entity.QuestionRoom;
import com.mallan.yujeongran.icebreaking.question_game.entity.QuestionTopic;
import com.mallan.yujeongran.icebreaking.question_game.repository.QuestionRoomRepository;
import com.mallan.yujeongran.icebreaking.question_game.repository.QuestionTopicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionRoomService {

    private final RedisTemplate<String, String> redisTemplate;
    private final QuestionRoomRepository questionRoomRepository;
    private final QuestionTopicRepository questionTopicRepository;

    @Value("${QUESTION_ROOM_BASE_URL}")
    private String baseUrl;

    public QuestionCreateRoomResponseDto createRoom(QuestionCreateRoomRequestDto request) {
        String playerId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        String roomCode = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        String nickname = request.getNickname();
        String profileImage = request.getProfileImage();

        redisTemplate.opsForValue().set("question:player:" + playerId + ":nickname", nickname);
        redisTemplate.opsForValue().set("question:player:" + playerId + ":profileImage", profileImage);
        redisTemplate.opsForList().rightPush("question:room:" + roomCode + ":players", playerId);
        redisTemplate.opsForHash().put("question:room:" + roomCode, "hostId", playerId);

        redisTemplate.expire("question:room:" + roomCode, Duration.ofHours(24));
        redisTemplate.expire("question:room:" + roomCode + ":players", Duration.ofHours(24));

        QuestionRoom room = QuestionRoom.builder()
                .roomCode(roomCode)
                .hostId(playerId)
                .hostNickname(nickname)
                .questionCount(0)
                .topicId(0)
                .playerCount(1)
                .url(baseUrl + "/question/" + roomCode)
                .build();
        questionRoomRepository.save(room);

        return QuestionCreateRoomResponseDto.builder()
                .roomCode(roomCode)
                .hostId(playerId)
                .hostNickname(nickname)
                .hostProfileImage(profileImage)
                .url(room.getUrl())
                .build();
    }

    public QuestionJoinRoomResponseDto joinRoom(String roomCode, QuestionJoinRoomRequestDto request) {
        Boolean exists = redisTemplate.hasKey("question:room:" + roomCode);
        if (exists == null || !exists) {
            throw new IllegalArgumentException("해당 방이 존재하지 않습니다.");
        }

        String playerId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        String nickname = request.getNickname();
        String profileImage = request.getProfileImage();

        redisTemplate.opsForValue().set("question:player:" + playerId + ":nickname", nickname);
        redisTemplate.opsForValue().set("question:player:" + playerId + ":profileImage", profileImage);
        redisTemplate.opsForList().rightPush("question:room:" + roomCode + ":players", playerId);

        questionRoomRepository.findByRoomCode(roomCode).ifPresent(room -> {
            room.setPlayerCount(room.getPlayerCount() + 1);
            questionRoomRepository.save(room);
        });

        return QuestionJoinRoomResponseDto.builder()
                .playerId(playerId)
                .nickname(nickname)
                .profileImage(profileImage)
                .build();
    }

    public QuestionExitRoomResponseDto exitRoom(String roomCode, String playerId) {
        String nickname = redisTemplate.opsForValue().get("question:player:" + playerId + ":nickname");

        redisTemplate.opsForList().remove("question:room:" + roomCode + ":players", 1, playerId);
        redisTemplate.delete("question:player:" + playerId + ":nickname");
        redisTemplate.delete("question:player:" + playerId + ":profileImage");

        questionRoomRepository.findByRoomCode(roomCode).ifPresent(room -> {
            int current = room.getPlayerCount();
            room.setPlayerCount(Math.max(0, current - 1));
            questionRoomRepository.save(room);

            if (room.getPlayerCount() == 0) {
                redisTemplate.delete("question:room:" + roomCode);
                redisTemplate.delete("question:room:" + roomCode + ":players");

                questionRoomRepository.delete(room);
            }
        });

        return QuestionExitRoomResponseDto.builder()
                .roomCode(roomCode)
                .playerId(playerId)
                .nickname(nickname)
                .build();
    }

    public QuestionSetQuestionCountResponseDto setQuestionCount(String roomCode, QuestionSetQuestionCountRequestDto request) {
        String hostId = (String) redisTemplate.opsForHash().get("question:room:" + roomCode, "hostId");

        if (!request.getPlayerId().equals(hostId)) {
            throw new IllegalArgumentException("방장만 문제 수를 설정할 수 있습니다.");
        }

        QuestionRoom room = questionRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));

        room.setQuestionCount(request.getQuestionCount());
        questionRoomRepository.save(room);

        return QuestionSetQuestionCountResponseDto.builder()
                .questionCount(request.getQuestionCount())
                .build();
    }

    public QuestionSetTopicResponseDto setTopic(String roomCode, QuestionSetTopicRequestDto request) {
        String hostId = (String) redisTemplate.opsForHash().get("question:room:" + roomCode, "hostId");

        if (!request.getPlayerId().equals(hostId)) {
            throw new IllegalArgumentException("방장만 주제를 설정할 수 있습니다.");
        }

        QuestionRoom room = questionRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));

        room.setTopicId(request.getTopicId());
        questionRoomRepository.save(room);

        QuestionTopic topic = questionTopicRepository.findById((long) request.getTopicId())
                .orElseThrow(() -> new IllegalArgumentException("해당 주제가 존재하지 않습니다."));

        return QuestionSetTopicResponseDto.builder()
                .topicId(topic.getId().intValue())
                .topic(topic.getTopic())
                .build();
    }

    public void startGame(String roomCode, QuestionGameStartRequestDto request) {
        String hostId = (String) redisTemplate.opsForHash().get("question:room:" + roomCode, "hostId");

        if (!request.getPlayerId().equals(hostId)) {
            throw new IllegalArgumentException("방장만 게임을 시작할 수 있습니다.");
        }

        redisTemplate.opsForValue().set("question:room:" + roomCode + ":started", "true", Duration.ofHours(24));
    }

    public QuestionWaitingRoomResponseDto getWaitingRoomInfo(String roomCode) {
        QuestionRoom room = questionRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));

        List<String> playerIds = redisTemplate.opsForList().range("question:room:" + roomCode + ":players", 0, -1);
        if (playerIds == null) {
            throw new IllegalArgumentException("플레이어 정보를 불러올 수 없습니다.");
        }

        List<QuestionPlayInfoResponseDto> players = playerIds.stream()
                .map(id -> QuestionPlayInfoResponseDto.builder()
                        .playerId(id)
                        .nickname(redisTemplate.opsForValue().get("question:player:" + id + ":nickname"))
                        .profileImage(redisTemplate.opsForValue().get("question:player:" + id + ":profileImage"))
                        .build())
                .toList();

        return QuestionWaitingRoomResponseDto.builder()
                .roomCode(room.getRoomCode())
                .hostId(room.getHostId())
                .hostNickname(room.getHostNickname())
                .url(room.getUrl())
                .questionCount(room.getQuestionCount())
                .playerCount(room.getPlayerCount())
                .players(players)
                .build();
    }

}
