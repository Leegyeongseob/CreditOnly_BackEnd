package com.kh.Palette_BackEnd.dto.reqdto;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardReqDto {
    private String title;
    private String imgUrl;
    private String contents;
    private String memberEmail;
    private Long boardListId;
}