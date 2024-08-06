package com.kh.Palette_BackEnd.dto.resdto;

import com.kh.Palette_BackEnd.entity.MemberEntity;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResDto {
    private String email;
    private String pwd;
    private String name;
    @Column(length = 7)
    private int registrationNumber;
    private String nickName;
    private String coupleName;


    public static MemberResDto of(MemberEntity member){
        return MemberResDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .nickName(member.getNickName())
                .build();
    }

    public static MemberResDto fromMemberEntity(MemberEntity member){
        return MemberResDto.builder()
                .name(member.getName())
                .build();
    }
}
