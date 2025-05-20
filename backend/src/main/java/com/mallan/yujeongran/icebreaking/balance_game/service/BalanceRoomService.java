package com.mallan.yujeongran.icebreaking.balance_game.service;

import com.mallan.yujeongran.icebreaking.balance_game.dto.request.CreateBalancePlayerRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.UpdateBalanceQuestionCountRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.request.UpdateBalanceTopicRequestDto;
import com.mallan.yujeongran.icebreaking.balance_game.dto.response.BalanceRoomResponseDto;
import com.mallan.yujeongran.icebreaking.balance_game.entity.BalanceRoom;
import com.mallan.yujeongran.icebreaking.balance_game.repository.BalanceRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BalanceRoomService {

    private final BalanceRoomRepository balanceRoomRepository;
    private final BalancePlayerService balancePlayerService;

    @Value("${BALANCE_ROOM_BASE_URL}")
    private String balanceRoomBaseUrl;

    public BalanceRoomResponseDto createRoomWithHost(CreateBalancePlayerRequestDto requestDto) {
        String roomCode = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        String hostId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

        BalanceRoom room = BalanceRoom.builder()
                .roomCode(roomCode)
                .hostId(hostId)
                .hostNickname(requestDto.getNickname())
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

    public String joinRoomAsGuest(String roomCode, String nickname, String profileImage) {
        String playerId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

        balancePlayerService.createPlayer(playerId, nickname, profileImage);
        balancePlayerService.joinRoom(roomCode, playerId, nickname, profileImage);

        BalanceRoom room = balanceRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("방이 존재하지 않습니다."));
        room.setPlayerCount(room.getPlayerCount() + 1);

        return playerId;
    }

    public void updateRoomTopic(String roomCode, UpdateBalanceTopicRequestDto request) {
        BalanceRoom room = balanceRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));
        room.setTopicId(request.getTopicId());
    }

    public void updateRoomQuestionCount(String roomCode, UpdateBalanceQuestionCountRequestDto request) {
        BalanceRoom room = balanceRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));
        room.setQuestionCount(request.getQuestionCount());
    }

}
