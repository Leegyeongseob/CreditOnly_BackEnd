package com.kh.Palette_BackEnd.dto.reqdto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestBookReqDto {
    private Long id;

    private String contents;

    private String memberEmail;

    private String coupleName;
}
