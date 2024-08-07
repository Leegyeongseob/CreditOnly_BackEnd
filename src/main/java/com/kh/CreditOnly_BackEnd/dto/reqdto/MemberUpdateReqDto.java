package com.kh.CreditOnly_BackEnd.dto.reqdto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberUpdateReqDto {
    private String email;
    private String updateEmail;
    private String pwd;
    private String name;
    private String nickName;
    private String coupleName;

}
