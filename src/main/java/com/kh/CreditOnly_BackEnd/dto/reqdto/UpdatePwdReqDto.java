package com.kh.CreditOnly_BackEnd.dto.reqdto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePwdReqDto {
    private String email;
    private String pwd;
}
