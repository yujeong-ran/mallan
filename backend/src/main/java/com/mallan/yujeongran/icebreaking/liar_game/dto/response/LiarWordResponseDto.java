package com.mallan.yujeongran.icebreaking.liar_game.dto.response;

import com.mallan.yujeongran.icebreaking.liar_game.entity.LiarWord;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LiarWordResponseDto {

    private final Long id;
    private final String word;

    public LiarWordResponseDto(Long id, String word) {
        this.id = id;
        this.word = word;
    }

    public static LiarWordResponseDto fromEntity(LiarWord word) {
        return LiarWordResponseDto.builder()
                .id(word.getId())
                .word(word.getWord())
                .build();
    }

}
