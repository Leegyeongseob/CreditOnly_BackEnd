package com.kh.CreditOnly_BackEnd.dto.resdto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HelpResDto {
    private Long id; // 고유 식별자 추가
    private String title;
    private String contents;
    private String createdDate;
}
