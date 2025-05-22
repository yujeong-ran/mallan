package com.mallan.yujeongran.icebreaking.liar_game.service;

import com.mallan.yujeongran.icebreaking.liar_game.dto.request.*;
import com.mallan.yujeongran.icebreaking.liar_game.dto.response.LiarGameResultResponseDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.response.LiarPlayerInfoResponseDto;
import com.mallan.yujeongran.icebreaking.liar_game.dto.response.LiarWaitingRoomResponseDto;
import com.mallan.yujeongran.icebreaking.liar_game.entity.LiarRoom;
import com.mallan.yujeongran.icebreaking.liar_game.entity.LiarWord;
import com.mallan.yujeongran.icebreaking.liar_game.repository.LiarRoomRepository;
import com.mallan.yujeongran.icebreaking.liar_game.repository.LiarWordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LiarRoomService {

    private final LiarRoomRepository liarRoomRepository;
    private final LiarWordRepository liarWordRepository;
    private final LiarPlayerService liarPlayerService;

    @Value("${LIAR_ROOM_BASE_URL}")
    private String liarRoomBaseUrl;

    public Map<String, String> createRoomWithHost(LiarCreatePlayerWithRoomRequestDto request) {
        String playerId = liarPlayerService.createPlayer(request.getNickname(), request.getProfileImage());
        String roomCode = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

        liarPlayerService.createRoom(roomCode, playerId);

        LiarRoom liarRoom = LiarRoom.builder()
                .roomCode(roomCode)
                .hostId(playerId)
                .hostNickname(request.getNickname())
                .playerCount(1)
                .descriptionCount(1)
                .build();

        liarRoomRepository.save(liarRoom);

        Map<String, String> response = new HashMap<>();
        response.put("playerId", playerId);
        response.put("roomCode", roomCode);
        response.put("url", liarRoomBaseUrl + "/" + roomCode);
        return response;
    }


    public void selectTopic(String roomCode, LiarSelectTopicRequestDto requestDto) {
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

    public Map<String, String> joinRoomWithPlayer(String roomCode, LiarJoinRoomRequestDto request) {
        String playerId = liarPlayerService.createPlayer(request.getNickname(), request.getProfileImage());
        liarPlayerService.joinRoom(roomCode, playerId, request.getNickname());

        LiarRoom room = liarRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));
        room.setPlayerCount(room.getPlayerCount() + 1);

        Map<String, String> response = new HashMap<>();
        response.put("playerId", playerId);
        response.put("roomCode", roomCode);
        return response;
    }


    public LiarWaitingRoomResponseDto getWaitingRoomInfo(String roomCode) {
        LiarRoom room = liarRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));

        List<String> playerIds = liarPlayerService.getAllPlayerIds(roomCode);

        List<LiarPlayerInfoResponseDto> players = playerIds.stream()
                .map(id -> LiarPlayerInfoResponseDto.builder()
                        .playerId(id)
                        .nickname(liarPlayerService.getNickname(id))
                        .profileImage(liarPlayerService.getProfileImage(id))
                        .build())
                .collect(Collectors.toList());

        return LiarWaitingRoomResponseDto.builder()
                .roomCode(room.getRoomCode())
                .playerId(room.getHostNickname())
                .playerCount(room.getPlayerCount())
                .descriptionCount(room.getDescriptionCount())
                .players(players)
                .build();
    }

    public void ExitRoom(String roomCode, LiarExitRoomRequestDto request) {
        liarPlayerService.ExitRoom(roomCode, request.getPlayerId());

        LiarRoom room = liarRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));

        int updatedCount = Math.max(0, room.getPlayerCount() - 1);
        room.setPlayerCount(updatedCount);

        if (updatedCount == 0) {
            liarRoomRepository.delete(room);
            liarPlayerService.deleteRoom(roomCode);
        }
    }

    public void deleteRoom(String roomCode, LiarEndGameRequestDto request) {
        String playerId = request.getPlayerId();
        if (!liarPlayerService.isHost(roomCode, playerId)) {
            throw new IllegalArgumentException("방장만 방을 삭제할 수 있습니다.");
        }

        liarPlayerService.deleteRoom(roomCode);
        liarRoomRepository.findByRoomCode(roomCode)
                .ifPresent(liarRoomRepository::delete);
    }

    public int getPlayerCount(String roomCode) {
        return liarPlayerService.getPlayerCount(roomCode);
    }

    public boolean canStartGame(String roomCode) {
        int count = liarPlayerService.getPlayerCount(roomCode);
        return count >= 4 && count <= 12;
    }

    public void updateDescriptionCount(String roomCode, int newRound, String hostId) {
        if (!liarPlayerService.isHost(roomCode, hostId)) {
            throw new IllegalArgumentException("해당 사용자는 방장이 아닙니다.");
        }

        LiarRoom room = liarRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));

        room.setDescriptionCount(newRound);
    }

    public void vote(String roomCode, String voterId, String targetId) {
        liarPlayerService.saveVote(roomCode, voterId, targetId);
    }

    public boolean guessWord(String roomCode, String playerId, String word) {
        if (!liarPlayerService.isLiar(roomCode, playerId)) {
            throw new IllegalArgumentException("해당 플레이어는 라이어가 아닙니다.");
        }
        liarPlayerService.saveGuess(roomCode, word);

        LiarRoom room = liarRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("방이 존재하지 않습니다."));
        return room.getWordForPlayer().equals(word);
    }

    public LiarGameResultResponseDto getGameResult(String roomCode) {
        LiarRoom room = liarRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("방이 존재하지 않습니다."));

        String votedLiarId = liarPlayerService.determineLiar(roomCode);
        return liarPlayerService.getGameResult(roomCode, room.getWordForPlayer(), votedLiarId);
    }

    public Map<String, String> getVotedLiar(String roomCode) {
        String votedLiarId = liarPlayerService.getVotedLiarId(roomCode);
        String nickname = liarPlayerService.getNickname(votedLiarId);
        String profileImage = liarPlayerService.getProfileImage(votedLiarId);

        Map<String, String> result = new HashMap<>();
        result.put("votedLiarId", votedLiarId);
        result.put("nickname", nickname != null ? nickname : "알 수 없음");
        result.put("profileImage", profileImage != null ? profileImage : "default.png");
        return result;
    }


    public String getWordForPlayer(String roomCode, LiarSearchPlayerWordRequestDto request) {
        LiarRoom room = liarRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("방이 존재하지 않습니다."));

        boolean isLiar = liarPlayerService.isLiar(roomCode, request.getPlayerId());
        return isLiar ? room.getWordForLiar() : room.getWordForPlayer();
    }

    public void startGame(String roomCode, String hostId) {
        if (!liarPlayerService.isHost(roomCode, hostId)) {
            throw new IllegalArgumentException("해당 사용자는 방장이 아닙니다.");
        }

        List<String> allPlayerIds = liarPlayerService.getAllPlayerIds(roomCode);
        int liarCount = (allPlayerIds.size() <= 7) ? 1 : 2;

        Collections.shuffle(allPlayerIds);
        List<String> liarIds = allPlayerIds.subList(0, liarCount);

        liarPlayerService.assignLiars(roomCode, liarIds);
    }

    public void restartGame(String roomCode, String hostId) {
        if (!liarPlayerService.isHost(roomCode, hostId)) {
            throw new IllegalArgumentException("해당 사용자는 방장이 아닙니다.");
        }
        liarPlayerService.resetGameSession(roomCode);
    }

}
