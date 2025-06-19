package com.mallan.yujeongran.icebreaking.coin_game.service;

import com.mallan.yujeongran.icebreaking.admin.service.ManagementInfoService;
import com.mallan.yujeongran.icebreaking.coin_game.dto.request.CoinExitRoomRequestDto;
import com.mallan.yujeongran.icebreaking.coin_game.dto.request.CoinSetRoundRequestDto;
import com.mallan.yujeongran.icebreaking.coin_game.dto.request.CoinStartGameRequestDto;
import com.mallan.yujeongran.icebreaking.coin_game.dto.response.CoinWaitingPlayerResponseDto;
import com.mallan.yujeongran.icebreaking.coin_game.dto.response.CoinWaitingRoomResponseDto;
import com.mallan.yujeongran.icebreaking.coin_game.entity.CoinRoom;
import com.mallan.yujeongran.icebreaking.coin_game.repository.CoinRoomRepository;
import com.mallan.yujeongran.icebreaking.review.enums.GameType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CoinRoomService {

    private final CoinGameService coinGameService;
    private final CoinRoomRepository coinRoomRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final ManagementInfoService managementInfoService;

    public CoinWaitingRoomResponseDto getWaitingRoomInfo(String roomCode) {
        CoinRoom room = coinRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));

        List<String> playerIds = redisTemplate.opsForList().range("coin:room:" + roomCode + ":players", 0, -1);
        List<CoinWaitingPlayerResponseDto> players = playerIds.stream().map(playerId -> {
            String nickname = redisTemplate.opsForValue().get("coin:player:" + playerId + ":nickname");
            String profileImage = redisTemplate.opsForValue().get("coin:player:" + playerId + ":profileImage");
            return CoinWaitingPlayerResponseDto.builder()
                    .playerId(playerId)
                    .nickname(nickname)
                    .profileImage(profileImage)
                    .build();
        }).toList();

        return CoinWaitingRoomResponseDto.builder()
                .roomCode(room.getRoomCode())
                .hostId(room.getHostId())
                .hostNickname(room.getHostNickname())
                .roundCount(room.getRoundCount())
                .players(players)
                .build();
    }

    public void setRound(String roomCode, CoinSetRoundRequestDto request) {
        String hostId = (String) redisTemplate.opsForHash().get("coin:room:" + roomCode, "hostId");

        if (!request.getPlayerId().equals(hostId)) {
            throw new IllegalArgumentException("방장만 라운드를 설정할 수 있습니다.");
        }

        redisTemplate.opsForHash().put("coin:room:" + roomCode, "roundCount", String.valueOf(request.getRoundCount()));

        CoinRoom room = coinRoomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));
        room.setRoundCount(request.getRoundCount());
        coinRoomRepository.save(room);
    }

    public void startGame(String roomCode, CoinStartGameRequestDto request) {
        String hostId = (String) redisTemplate.opsForHash().get("coin:room:" + roomCode, "hostId");

        if (!request.getPlayerId().equals(hostId)) {
            throw new IllegalArgumentException("방장만 게임을 시작할 수 있습니다.");
        }

        managementInfoService.incrementGameCount(GameType.COIN_TRUTH_GAME);

        redisTemplate.opsForValue().set("coin:room:" + roomCode + ":started", "true", Duration.ofHours(24));
    }
    public void leaveRoom(String roomCode, CoinExitRoomRequestDto request) {
        redisTemplate.opsForList().remove("coin:room:" + roomCode + ":players", 1, request.getPlayerId());
        redisTemplate.delete("coin:player:" + request.getPlayerId() + ":nickname");
        redisTemplate.delete("coin:player:" + request.getPlayerId() + ":profileImage");
        redisTemplate.delete("coin:room:" + roomCode + ":score:" + request.getPlayerId());

        coinRoomRepository.findByRoomCode(roomCode).ifPresent(room -> {
            int current = room.getPlayerCount();
            room.setPlayerCount(Math.max(0, current - 1));
            coinRoomRepository.save(room);

            if (room.getPlayerCount() == 0) {
                coinGameService.endGame(roomCode);
            }
        });
    }


}
