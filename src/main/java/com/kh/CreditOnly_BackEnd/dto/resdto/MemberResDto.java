package com.kh.CreditOnly_BackEnd.dto.resdto;

import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResDto {
    private String email;
    private String pwd;
    private String name;
    @Column(length = 13)
    private int registrationNumber;


    public static MemberResDto of(MemberEntity member){
        return MemberResDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }

    public static MemberResDto fromMemberEntity(MemberEntity member){
        return MemberResDto.builder()
                .name(member.getName())
                .build();
    }
}
