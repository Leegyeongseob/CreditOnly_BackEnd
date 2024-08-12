package com.kh.CreditOnly_BackEnd.dto.resdto;

import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SettingResDto {
    private String email;
    private String name;
    private String birthDate; // 생년월일 (YYYY-MM-DD)
    private String joinDate;  // 가입일 (YYYY-MM-DD)

    // 주민등록번호에서 생년월일을 추출하는 메서드
    public static String extractBirthDate(String registrationNumber) {
        if (registrationNumber != null && registrationNumber.length() == 13) {
            String birthYear = registrationNumber.substring(0, 2);
            String birthMonth = registrationNumber.substring(2, 4);
            String birthDay = registrationNumber.substring(4, 6);
            char genderChar = registrationNumber.charAt(6);

            // 1900년대 출생자인 경우
            if (genderChar == '1' || genderChar == '2') {
                birthYear = "19" + birthYear;
            }
            // 2000년대 출생자인 경우
            else if (genderChar == '3' || genderChar == '4') {
                birthYear = "20" + birthYear;
            } else {
                throw new IllegalArgumentException("유효하지 않은 주민등록번호입니다.");
            }

            return birthYear + "-" + birthMonth + "-" + birthDay;
        } else {
            throw new IllegalArgumentException("잘못된 입력입니다. 다시 입력바랍니다.");
        }
    }

    // MemberEntity로부터 SettingResDto를 생성하는 메서드
    public static SettingResDto fromMemberEntity(MemberEntity member) {
        return SettingResDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .birthDate(extractBirthDate(member.getRegistrationNumber()))
                .joinDate(member.getDate().toLocalDate().toString())
                .build();
    }
}
