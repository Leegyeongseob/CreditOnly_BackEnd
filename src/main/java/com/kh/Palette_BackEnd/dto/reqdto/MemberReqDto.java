package com.kh.Palette_BackEnd.dto.reqdto;


import com.kh.Palette_BackEnd.constant.Authority;
import com.kh.Palette_BackEnd.entity.MemberEntity;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

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
    @Column(length = 7)
    private String registrationNumber;
    private String nickName;
    private String coupleName;


    public MemberEntity toMemberEntity(PasswordEncoder passwordEncoder) {
        return MemberEntity.builder()
                .email(email)
                .pwd(passwordEncoder.encode(pwd))
                .name(name)
                .nickName(nickName)
                .registrationNumber(registrationNumber)
                .authority(Authority.ROLL_USER)
                .build();
    }

}
