package com.kh.Palette_BackEnd.controller;


import com.kh.Palette_BackEnd.dto.TokenDto;
import com.kh.Palette_BackEnd.dto.reqdto.*;
import com.kh.Palette_BackEnd.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    //커플이름 search
    @PostMapping("/coupleNameSearch")
    public ResponseEntity<Boolean> searchCouple(@RequestBody Map<String,String> coupleName){
        return ResponseEntity.ok(authService.coupleNameSearch(coupleName.get("coupleName")));
    }
    //커플이름 Insert
    @PostMapping("/coupleNameInsert")
    public ResponseEntity<String> coupleInsert(@RequestBody CoupleReqDto requestDto){
        return ResponseEntity.ok(authService.coupleNameInsert(requestDto));
    }
    // 커플이름 중복 시 짝 이메일 확인
    @PostMapping("/coupleEmailCheck")
    public ResponseEntity<String>coupleEmailCheck(@RequestBody Map<String,String> coupleName){
        return ResponseEntity.ok(authService.coupleEmailCheck(coupleName.get("coupleName")));
    }
    // 커플에 계정 추가 등록
    @PostMapping("/secondCoupleNameInsert")
    public ResponseEntity<String> secondCoupleNameInsert(@RequestBody CoupleReqDto requestDto){
        return ResponseEntity.ok(authService.secondCoupleNameInsert(requestDto));
    }
    //이메일로 커플이름 search
    @PostMapping("/emailToCoupleNameSearch")
    public ResponseEntity<String>coupleSearch(@RequestBody Map<String,String> email){
        return ResponseEntity.ok(authService.emailToCoupleNameSearch(email.get("email")));
    }
    //두번째 커플 계정 존재 확인
    @PostMapping("/secondEmailExist")
    public ResponseEntity<Boolean> secondEmailExist(@RequestBody Map<String,String> coupleName){
        return ResponseEntity.ok(authService.secondEmailExist(coupleName.get("couple")));
    }
    //아이디 찾기
    @PostMapping("/findIdResult")
    public ResponseEntity<String> findIdResult(@RequestBody FindIdReqDto findIdReqDto){
        return ResponseEntity.ok(authService.findIdResult(findIdReqDto));
    }
    //비밀번호 찾기
    @PostMapping("/findPwdResult")
    public ResponseEntity<String> fondPwdResult(@RequestBody FindPwdDto findPwdDto){
        return ResponseEntity.ok(authService.findPwdResult(findPwdDto));
    }
    //이미 커플이 완성되어 있는지 확인.
    @GetMapping("isExistCouple")
    public ResponseEntity<Boolean> isExistCouple(@RequestParam String coupleName){
        return ResponseEntity.ok(authService.isExistCouple(coupleName));
    }
}
