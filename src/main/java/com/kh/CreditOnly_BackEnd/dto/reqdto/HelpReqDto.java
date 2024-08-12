package com.kh.CreditOnly_BackEnd.dto.reqdto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HelpReqDto {
    private String email;
    private String title;
    private String contents;
}
