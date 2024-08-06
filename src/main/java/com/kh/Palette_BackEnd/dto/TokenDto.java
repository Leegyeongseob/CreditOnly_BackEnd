package com.kh.Palette_BackEnd.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    private String grantType;
    private String accessToken;
    private Long tokenExpiresIn;
    private String refreshToken; // 리프레시 토큰
    private Long refreshTokenExpiresIn; // 리프레시 토큰 만료 시간
}
