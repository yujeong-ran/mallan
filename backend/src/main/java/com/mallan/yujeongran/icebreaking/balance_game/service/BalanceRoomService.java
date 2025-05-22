package com.mallan.yujeongran.icebreaking.balance_game.service;

import com.mallan.yujeongran.icebreaking.balance_game.dto.request.BalanceCreatePlayerRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.BalanceJoinRoomRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.BalanceUpdateQuestionCountRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.BalanceUpdateTopicRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceJoinRoomResponseDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalancePlayerInfoResponseDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceRoomResponseDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceWaitingRoomResponseDto;
import com.mallan.yujeongran.icebreaking.balance_game.entity.BalanceRoom;
import com.mallan.yujeongran.icebreaking.balance_game.repository.BalanceRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BalanceRoomService {

    private final BalanceRoomRepository balanceRoomRepository;
    private final BalancePlayerService balancePlayerService;
    private final RedisTemplate<String, String> redisTemplate;


    @Value("${BALANCE_ROOM_BASE_URL}")
    private String balanceRoomBaseUrl;

    public BalanceRoomResponseDto createRoomWithHost(BalanceCreatePlayerRequestDto requestDto) {
        String roomCode = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        String hostId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

        BalanceRoom room = BalanceRoom.builder()
                .roomCode(roomCode)
                .hostId(hostId)
                .hostNickname(requestDto.getNickname())
                .topicId(1L)
                .questionCount(5)
                .playerCount(1)
                .isActive(true)
                .build();

        balanceRoomRepository.save(room);
        balancePlayerService.createPlayer(hostId, requestDto.getNickname(), requestDto.getProfileImage());
        balancePlayerService.createRoom(roomCode, hostId);

        return BalanceRoomResponseDto.builder()
                .playerId(hostId)
                .roomCode(roomCode)
                .url(balanceRoomBaseUrl + "/" + roomCode)
                .build();
    }

    public BalanceJoinRoomResponseDto joinRoomAsGuest(String roomCode, BalanceJoinRoomRequestDto request) {
        String playerId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

        balancePlayerService.createPlayer(playerId, request.getNickname(), request.getProfileImage());
        balancePlayerService.joinRoom(roomCode, playerId, request);

        BalanceRoom room = balanceRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("방이 존재하지 않습니다."));
        room.setPlayerCount(room.getPlayerCount() + 1);

        return BalanceJoinRoomResponseDto.builder()
                .playerId(playerId)
                .profileImage(request.getProfileImage())
                .build();
    }

    public void exitRoom(String roomCode, String playerId) {
        String playerKey = "room:" + roomCode + ":players";
        redisTemplate.opsForList().remove(playerKey, 1, playerId);

        redisTemplate.delete("player:" + playerId + ":nickname");
        redisTemplate.delete("player:" + playerId + ":profileImage");

        Long remainingPlayers = redisTemplate.opsForList().size(playerKey);
        if (remainingPlayers != null && remainingPlayers == 0) {
            redisTemplate.delete(playerKey); // 안전하게 삭제

            Optional<BalanceRoom> roomOpt = balanceRoomRepository.findByRoomCode(roomCode);
            roomOpt.ifPresent(balanceRoomRepository::delete);
        }
    }



    public BalanceWaitingRoomResponseDto getWaitingRoomInfo(String roomCode) {
        BalanceRoom room = balanceRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));

        List<String> playerIds = redisTemplate.opsForList().range("room:" + roomCode + ":players", 0, -1);

        List<BalancePlayerInfoResponseDto> players = playerIds.stream()
                .map(id -> BalancePlayerInfoResponseDto.builder()
                        .playerId(id)
                        .nickname(balancePlayerService.getNickname(id))
                        .profileImage(balancePlayerService.getProfileImage(id))
                        .build())
                .collect(Collectors.toList());

        return BalanceWaitingRoomResponseDto.builder()
                .roomCode(room.getRoomCode())
                .hostNickname(room.getHostNickname())
                .topicId(room.getTopicId())
                .questionCount(room.getQuestionCount())
                .players(players)
                .build();
    }

    public void updateRoomTopic(String roomCode, BalanceUpdateTopicRequestDto request) {
        if (!balancePlayerService.isHost(roomCode, request.getPlayerId())) {
            throw new IllegalArgumentException("호스트만 주제를 설정할 수 있습니다.");
        }

        BalanceRoom room = balanceRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));
        room.setTopicId(request.getTopicId());
    }

    public void updateRoomQuestionCount(String roomCode, BalanceUpdateQuestionCountRequestDto request) {
        if (!balancePlayerService.isHost(roomCode, request.getPlayerId())) {
            throw new IllegalArgumentException("호스트만 문항 수를 설정할 수 있습니다.");
        }

        BalanceRoom room = balanceRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));
        room.setQuestionCount(request.getQuestionCount());
        balanceRoomRepository.save(room);
    }


}
