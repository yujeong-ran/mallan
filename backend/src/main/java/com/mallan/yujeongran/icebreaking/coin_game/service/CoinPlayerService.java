package com.mallan.yujeongran.icebreaking.coin_game.service;

import com.mallan.yujeongran.icebreaking.admin.service.ManagementInfoService;
import com.mallan.yujeongran.icebreaking.coin_game.dto.request.CoinCreateRoomRequestDto;
import com.mallan.yujeongran.icebreaking.coin_game.dto.request.CoinJoinRoomRequestDto;
import com.mallan.yujeongran.icebreaking.coin_game.dto.response.CoinCreateRoomResponseDto;
import com.mallan.yujeongran.icebreaking.coin_game.dto.response.CoinJoinRoomResponseDto;
import com.mallan.yujeongran.icebreaking.coin_game.entity.CoinRoom;
import com.mallan.yujeongran.icebreaking.coin_game.repository.CoinRoomRepository;
import com.mallan.yujeongran.icebreaking.review.enums.GameType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CoinPlayerService {

    private final RedisTemplate<String, String> redisTemplate;
    private final CoinRoomRepository coinRoomRepository;
    private final ManagementInfoService managementInfoService;

    @Value("${COIN_ROOM_BASE_URL}")
    private String coinRoomBaseUrl;

    public CoinCreateRoomResponseDto createRoom(CoinCreateRoomRequestDto request){
        String playerId = UUID.randomUUID().toString().replace("-","").substring(0, 8);
        String roomCode = UUID.randomUUID().toString().replace("-","").substring(0, 8);
        redisTemplate.opsForValue().set("coin:player:" + playerId + ":nickname", request.getNickname());
        redisTemplate.opsForValue().set("coin:player:" + playerId + ":profileImage", request.getProfileImage());
        redisTemplate.expire("coin:player:" + playerId + ":nickname", Duration.ofHours(24));
        redisTemplate.expire("coin:player:" + playerId + ":profileImage", Duration.ofHours(24));

        redisTemplate.opsForHash().put("coin:room:" + roomCode, "hostId", playerId);
        redisTemplate.opsForList().rightPush("coin:room:" + roomCode + ":players", playerId);
        redisTemplate.expire("coin:room:" + roomCode, Duration.ofHours(24));
        redisTemplate.expire("coin:room:" + roomCode + ":players", Duration.ofHours(24));

        CoinRoom room = CoinRoom.builder()
                .roomCode(roomCode)
                .hostId(playerId)
                .hostNickname(request.getNickname())
                .roundCount(3)
                .playerCount(1)
                .createdAt(LocalDateTime.now())
                .build();


        coinRoomRepository.save(room);

        managementInfoService.incrementUserCount(GameType.COIN_TRUTH_GAME);

        return CoinCreateRoomResponseDto.builder()
                .roomCode(roomCode)
                .hostId(playerId)
                .hostNickname(request.getNickname())
                .url(coinRoomBaseUrl + "/coin/" + roomCode)
                .build();
    }

    public CoinJoinRoomResponseDto joinRoom(String roomCode, CoinJoinRoomRequestDto request) {
        Boolean exists = redisTemplate.hasKey("coin:room:" + roomCode);
        if (exists == null || !exists) {
            throw new IllegalArgumentException("해당 방이 존재하지 않습니다.");
        }

        String playerId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

        redisTemplate.opsForValue().set("coin:player:" + playerId + ":nickname", request.getNickname());
        redisTemplate.opsForValue().set("coin:player:" + playerId + ":profileImage", request.getProfileImage());
        redisTemplate.expire("coin:player:" + playerId + ":nickname", Duration.ofHours(24));
        redisTemplate.expire("coin:player:" + playerId + ":profileImage", Duration.ofHours(24));

        redisTemplate.opsForList().rightPush("coin:room:" + roomCode + ":players", playerId);

        coinRoomRepository.findByRoomCode(roomCode).ifPresent(room -> {
            int current = room.getPlayerCount();
            room.setPlayerCount(current + 1);
            coinRoomRepository.save(room);
        });

        managementInfoService.incrementUserCount(GameType.COIN_TRUTH_GAME);

        return CoinJoinRoomResponseDto.builder()
                .playerId(playerId)
                .nickname(request.getNickname())
                .profileImage(request.getProfileImage())
                .build();
    }

}

