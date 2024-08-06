package com.kh.Palette_BackEnd.dto.reqdto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoupleReqDto {
    private String email;
    private String coupleName;
}
