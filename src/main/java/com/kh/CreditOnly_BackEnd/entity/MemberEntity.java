package com.kh.CreditOnly_BackEnd.entity;

import com.kh.CreditOnly_BackEnd.constant.Authority;
import com.kh.CreditOnly_BackEnd.constant.Sex;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "Member_TB")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String pwd;

    private String name;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String profileImgUrl;

    @Column(length = 13)
    private String registrationNumber;

    @Enumerated(EnumType.STRING)
    private Authority authority;
    private boolean active;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;
    // 생성 날짜 필드 추가
    @Column(updatable = false) // 생성 후 값이 변경되지 않도록 설정
    private LocalDateTime date;

    // 엔티티가 영속성 컨텍스트에 저장되기 전 자동으로 호출되는 메서드 - DB 저장시 현재 날짜 자동 저장
    @PrePersist
    private void prePersist() {
        setSexBasedOnRegistrationNumber();
        this.date = LocalDateTime.now(); // 현재 날짜와 시간으로 설정
    }

    private void setSexBasedOnRegistrationNumber() {
        String regNumberStr = this.registrationNumber;
        if (regNumberStr != null && regNumberStr.length() == 13) {
            char genderChar = regNumberStr.charAt(6); // 0-based index, 6번째 인덱스는 7번째 자리
            if (genderChar == '1' || genderChar == '3') {
                this.sex = Sex.Man;
            } else if (genderChar == '2' || genderChar == '4') {
                this.sex = Sex.Woman;
            } else {
                throw new IllegalArgumentException("유효하지 않은 주민등록번호입니다.");
            }
        } else {
            throw new IllegalArgumentException("잘못된 입력입니다. 다시 입력바랍니다.");
        }
    }
}
