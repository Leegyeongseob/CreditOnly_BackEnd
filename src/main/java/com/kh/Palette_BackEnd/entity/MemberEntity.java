package com.kh.Palette_BackEnd.entity;


import com.kh.Palette_BackEnd.constant.Authority;
import com.kh.Palette_BackEnd.constant.Sex;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="Member_TB")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="member_id")
    private Long id;
    @Column(unique = true)
    private String email;
    private String pwd;
    private String name;
    private String nickName;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    private String profileImgUrl;
    @Column(length = 7)
    private String registrationNumber;
    @Enumerated(EnumType.STRING)
    private Authority authority;


    //두명을 한 커플로 묶기 위한 조인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="coupleName")
    private CoupleEntity couple;


    // 엔티티가 영속성 컨텍스트에 저장되기 전 자동으로 호출되는 메서드(주민번호로 성별 저장)
    @PrePersist
    @PreUpdate
    private void setSexBasedOnRegistrationNumber() {
        String regNumberStr = this.registrationNumber;
        if (regNumberStr != null && regNumberStr.length() >= 7) {
            char genderChar = regNumberStr.charAt(6); // 0-based index, 6번째 인덱스는 7번째 자리
            if (genderChar == '1' || genderChar == '3') {
                this.sex = Sex.Man;
            } else if (genderChar == '2' || genderChar == '4') {
                this.sex = Sex.Woman;
            } else {
                throw new IllegalArgumentException("유효하지 않은 주민등록번호입니다.");
            }
        } else {
            throw new IllegalArgumentException("유효하지 않은 주민등록번호입니다.");
        }
    }


}
