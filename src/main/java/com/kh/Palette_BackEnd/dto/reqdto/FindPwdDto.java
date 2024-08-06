package com.kh.Palette_BackEnd.dto.reqdto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindPwdDto {
    private String email;
    private String name;
    private String registrationNumber;
}
