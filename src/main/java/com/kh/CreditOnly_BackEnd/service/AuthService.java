package com.kh.CreditOnly_BackEnd.service;



import com.kh.CreditOnly_BackEnd.constant.Authority;
import com.kh.CreditOnly_BackEnd.dto.TokenDto;
import com.kh.CreditOnly_BackEnd.dto.reqdto.*;
import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import com.kh.CreditOnly_BackEnd.jwt.TokenProvider;
import com.kh.CreditOnly_BackEnd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @PersistenceContext
    EntityManager em;
    //회원가입
    public String signup(MemberReqDto requestDto) {
        try {
            // 이메일에 따라 권한을 설정
            String email = requestDto.getEmail();
            determineAuthority(email,requestDto);

            // Dto to Entity
            MemberEntity member = requestDto.toMemberEntity(passwordEncoder,requestDto.getAuthority());

            // 데이터베이스에 저장
            memberRepository.saveAndFlush(member);
            em.clear();

            return "Success";
        } catch (DataAccessException e) {
            // 데이터 접근 예외 처리 (예: 데이터베이스 접근 오류)
            return "회원가입 실패: 데이터베이스 접근 중 오류가 발생했습니다.";
        } catch (Exception e) {
            // 그 외의 예외 처리
            return "회원가입 중 오류가 발생했습니다.";
        }
    }
    //이메일에 따라 권한을 결정하는 메소드
    private void determineAuthority(String email,MemberReqDto requestDto) {
        // 특정 이메일에 대해 ROLE_ADMIN, 그 외에는 ROLE_USER 설정
        List<String> adminEmails = Arrays.asList(
                "mkk700000@naver.com",
                "98tmddyddl@gmail.com",
                "sungjin5129@gmail.com",
                "can34879@gmail.com",
                "00bsj2@naver.com",
                "ehdudloro1018@gmail.com"
        );
        // 이메일이 adminEmails 리스트에 포함되어 있는지 확인
        if (adminEmails.contains(email)) {
            requestDto.setAuthority(Authority.ROLE_ADMIN);
        } else {
            requestDto.setAuthority(Authority.ROLL_USER);
        }
    }
    // 이메일 중복확인
    public boolean isExistEmail(String Email){
        return memberRepository.existsByEmail(Email);
    }

    // 로그인
    public TokenDto login(LoginReqDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        return tokenProvider.generateTokenDto(authentication);
    }
    // accessToken 재발급
    public String createAccessToken(String refreshToken) {
        Authentication authentication = tokenProvider.getAuthentication(refreshToken);
        return tokenProvider.generateAccessToken(authentication);
    }

    // 아이디 찾기
    public List<String> findIdResult(FindIdReqDto findIdReqDto) {
        List<String> emailList = new ArrayList<>();

        // 입력값 유효성 검사
        if (findIdReqDto == null || findIdReqDto.getName() == null || findIdReqDto.getRegistrationNumber() == null) {
            logger.error("Invalid request data: {}", findIdReqDto);
            throw new IllegalArgumentException("Invalid input data. Name and Registration Number are required.");
        }

        try {
            // 데이터베이스 조회
            List<MemberEntity> members  = memberRepository.findEmailByNameAndRegistrationNumber(
                    findIdReqDto.getName(),
                    findIdReqDto.getRegistrationNumber()
            );

            // 이메일 리스트에 추가
            for (MemberEntity member : members) {
                emailList.add(member.getEmail());
            }
        } catch (Exception ex) {
            // 예외 처리 및 로그 기록
            logger.error("Error occurred while finding emails: {}", ex.getMessage(), ex);
            throw new RuntimeException("An error occurred while processing your request. Please try again later.");
        }

        return emailList;
    }
    // 비밀번호 찾기
    public String findPwdResult(FindPwdReqDto findPwdDto){
        Optional<MemberEntity> memberEntityOpt =memberRepository.findPwdByEmailAndNameAndRegistrationNumber(findPwdDto.getEmail(),findPwdDto.getName(),findPwdDto.getRegistrationNumber());
        if(memberEntityOpt.isPresent()){
            return"Exist";
        }
        else{
            return"";
        }
    }
    // 비밀번호 재설정
    public String updatePwd(UpdatePwdReqDto updatePwdReqDto){
        Optional<MemberEntity> memberEntityOpt = memberRepository.findByEmail(updatePwdReqDto.getEmail());
        try {
            if (memberEntityOpt.isPresent()) {
                MemberEntity member = memberEntityOpt.get();
                member.setPwd(passwordEncoder.encode(updatePwdReqDto.getPwd()));
                memberRepository.saveAndFlush(member);
                em.clear();

            }
            return "Success";
        } catch (DataAccessException e) {
            // 데이터 접근 예외 처리 (예: 데이터베이스 접근 오류)
            return "비밀번호 수정 실패: 데이터베이스 접근 중 오류가 발생했습니다.";
        } catch (Exception e) {
            // 그 외의 예외 처리
            return "비밀번호 수정 중 오류가 발생했습니다.";
        }
    }
}

