package com.kh.CreditOnly_BackEnd.dto.reqdto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformationReqDto {
    private String title; // 정보 제목
    private String content; // 정보 내용
    private String imageUrl; // 이미지 URL
    private String category; // 카테고리
}
