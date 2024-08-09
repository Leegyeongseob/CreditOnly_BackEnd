package com.kh.CreditOnly_BackEnd.dto.reqdto;


import com.kh.CreditOnly_BackEnd.constant.Authority;
import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberReqDto {
    private String email;
    private String pwd;
    private String name;
    @Column(length = 13)
    private String registrationNumber;
    private Authority authority;


    public MemberEntity toMemberEntity(PasswordEncoder passwordEncoder,Authority authority) {
        return MemberEntity.builder()
                .email(email)
                .pwd(passwordEncoder.encode(pwd))
                .name(name)
                .registrationNumber(registrationNumber)
                .authority(authority)
                .build();
    }

}
