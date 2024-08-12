package com.kh.CreditOnly_BackEnd.controller;


import com.kh.CreditOnly_BackEnd.dto.TokenDto;
import com.kh.CreditOnly_BackEnd.dto.reqdto.*;
import com.kh.CreditOnly_BackEnd.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class AuthController {
    private final AuthService authService;
    //아이디 중복 확인
    @PostMapping("/email")
    public ResponseEntity<Boolean> emailIsExist(@RequestBody Map<String,String> email){
        return ResponseEntity.ok(authService.isExistEmail(email.get("email")));
    }
    // accessToken 재발급
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestBody String refreshToken) {
        log.info("refreshToken: {}", refreshToken);
        return ResponseEntity.ok(authService.createAccessToken(refreshToken));
    }
    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody MemberReqDto requestDto){
        return ResponseEntity.ok(authService.signup(requestDto));
    }
    //로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginReqDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
    //아이디 찾기
    @PostMapping("/findIdResult")
    public ResponseEntity<List<String>> findIdResult(@RequestBody FindIdReqDto findIdReqDto){
        return ResponseEntity.ok(authService.findIdResult(findIdReqDto));
    }
    //비밀번호 찾기
    @PostMapping("/findPwdResult")
    public ResponseEntity<String> fondPwdResult(@RequestBody FindPwdReqDto findPwdDto){
        return ResponseEntity.ok(authService.findPwdResult(findPwdDto));
    }
    //비밀번호 재설정
    @PostMapping("/updatePwd")
    public ResponseEntity<String> updatePwd(@RequestBody UpdatePwdReqDto updatePwdReqDto){
        return ResponseEntity.ok(authService.updatePwd(updatePwdReqDto));
    }

}
