package com.mallan.yujeongran.icebreaking.liar_game.service;

import com.mallan.yujeongran.icebreaking.liar_game.dto.request.CreateLiarRoomRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.ExitLiarRoomRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.JoinLiarRoomRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.request.SelectTopicRequestDto;
import com.mallan.yujeongran.icebreaking.liar_game.entity.LiarRoom;
import com.mallan.yujeongran.icebreaking.liar_game.entity.LiarWord;
import com.mallan.yujeongran.icebreaking.liar_game.repository.LiarRoomRepository;
import com.mallan.yujeongran.icebreaking.liar_game.repository.LiarWordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class LiarRoomService {

    private final LiarRoomRepository liarRoomRepository;
    private final LiarWordRepository liarWordRepository;
    private final LiarRedisService liarRedisService;

    public String createRoom(CreateLiarRoomRequestDto requestDto) {
        String roomCode = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

        liarRedisService.createRoom(roomCode, requestDto.getHostId());

        LiarRoom liarRoom = LiarRoom.builder()
                .roomCode(roomCode)
                .hostId(requestDto.getHostId())
                .hostNickname(requestDto.getHostNickname())
                .playerCount(1)
                .build();

        liarRoomRepository.save(liarRoom);

        return roomCode;
    }

    public void selectTopic(String roomCode, SelectTopicRequestDto requestDto) {
        LiarRoom room = liarRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));

        if (!room.getHostId().equals(requestDto.getHostId())) {
            throw new IllegalArgumentException("해당 사용자는 방장이 아닙니다.");
        }

        List<LiarWord> words = liarWordRepository.findByTopicId(requestDto.getTopicId());
        if (words.size() < 2) {
            throw new IllegalArgumentException("단어가 2개 이상 필요합니다.");
        }

        Collections.shuffle(words);
        String wordForPlayer = words.get(0).getWord();
        String wordForLiar = words.get(1).getWord();
        String topicName = words.get(0).getTopic().getName();

        room.setTopic(topicName);
        room.setWordForPlayer(wordForPlayer);
        room.setWordForLiar(wordForLiar);
    }

    public void joinRoom(String roomCode, JoinLiarRoomRequestDto request) {
        liarRedisService.joinRoom(roomCode, request.getPlayerId(), request.getNickname());

        LiarRoom room = liarRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));

        room.setPlayerCount(room.getPlayerCount() + 1);
    }

    public void ExitRoom(String roomCode, ExitLiarRoomRequestDto request) {
        liarRedisService.ExitRoom(roomCode, request.getPlayerId());

        LiarRoom room = liarRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));

        int updatedCount = Math.max(0, room.getPlayerCount() - 1);
        room.setPlayerCount(updatedCount);

        if (updatedCount == 0) {
            liarRoomRepository.delete(room);
            liarRedisService.deleteRoom(roomCode);
        }
    }

    public void deleteRoom(String roomCode) {
        liarRedisService.deleteRoom(roomCode);
        liarRoomRepository.findByRoomCode(roomCode)
                .ifPresent(liarRoomRepository::delete);
    }

    public int getPlayerCount(String roomCode) {
        return liarRedisService.getPlayerCount(roomCode);
    }

    public boolean canStartGame(String roomCode) {
        int count = liarRedisService.getPlayerCount(roomCode);
        return count >= 4 && count <= 12;
    }

}
