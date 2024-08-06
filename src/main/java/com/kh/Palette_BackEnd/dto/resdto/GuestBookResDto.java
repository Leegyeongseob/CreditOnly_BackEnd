package com.kh.Palette_BackEnd.dto.resdto;

import com.kh.Palette_BackEnd.entity.GuestBookEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestBookResDto {
    private Long id;

    private LocalDateTime regDateTime;

    private String contents;

    private String memberNickName;

    private String imgUrl;

    private String coupleName;

    public static GuestBookResDto convertToResponseDto(GuestBookEntity entity) {
        return GuestBookResDto.builder()
                .id(entity.getId())
                .regDateTime(entity.getRegDate())
                .contents(entity.getContents())
                .memberNickName(entity.getMember().getNickName())
                .imgUrl(entity.getMember().getProfileImgUrl())
                .coupleName(entity.getCouple().getCoupleName())
                .build();
    }
}

