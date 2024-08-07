package com.kh.CreditOnly_BackEnd.service;



import com.kh.CreditOnly_BackEnd.dto.TokenDto;
import com.kh.CreditOnly_BackEnd.dto.reqdto.*;
import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import com.kh.CreditOnly_BackEnd.jwt.TokenProvider;
import com.kh.CreditOnly_BackEnd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @PersistenceContext
    EntityManager em;
    //회원가입
    public String signup(MemberReqDto requestDto) {
        try {
            //Dto to Entity
            MemberEntity member = requestDto.toMemberEntity(passwordEncoder);
            memberRepository.saveAndFlush(member);
            em.clear();
            return "Success";
        }catch (DataAccessException e) {
            // 데이터 접근 예외 처리 (예: 데이터베이스 접근 오류)
            return "회원가입 실패: 데이터베이스 접근 중 오류가 발생했습니다.";
        } catch (Exception e) {
            // 그 외의 예외 처리
            return "회원가입 중 오류가 발생했습니다.";
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
    public String findIdResult(FindIdReqDto findIdReqDto){
        Optional<MemberEntity> memberEntity = memberRepository.findEmailByNameAndRegistrationNumber(findIdReqDto.getName(),findIdReqDto.getRegistrationNumber());
        if(memberEntity.isPresent()){
            return memberEntity.get().getEmail();
        }
        else{
            return "";
        }
    }
    // 비밀번호 찾기
    public String findPwdResult(FindPwdDto findPwdDto){
        Optional<MemberEntity> memberEntityOpt =memberRepository.findPwdByEmailAndNameAndRegistrationNumber(findPwdDto.getEmail(),findPwdDto.getName(),findPwdDto.getRegistrationNumber());
        if(memberEntityOpt.isPresent()){
            MemberEntity memberEntity = memberEntityOpt.get();
            String temporaryPwd = UUID.randomUUID().toString().substring(0, 8);
            memberEntity.setPwd(passwordEncoder.encode(temporaryPwd));
            memberRepository.save(memberEntity);
            return temporaryPwd;
        }
        else{
            return"";
        }
    }
}

