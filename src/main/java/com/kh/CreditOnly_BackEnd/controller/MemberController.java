package com.kh.CreditOnly_BackEnd.controller;


import com.kh.CreditOnly_BackEnd.dto.reqdto.MemberUpdateReqDto;
import com.kh.CreditOnly_BackEnd.dto.resdto.MemberResDto;
import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import com.kh.CreditOnly_BackEnd.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    // 토큰으로 이메일 불러오기
    @GetMapping("/getEmail")
    public ResponseEntity<String> getEmail(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth: "+ auth);
        String email = memberService.getEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(email);
    }

    // 정보 불러오기
    @PostMapping("/info")
    public ResponseEntity<MemberResDto> memberAxios(@RequestBody Map<String,String> email){
        return ResponseEntity.ok(memberService.memberAxios(email.get("email")));
    }
    //회원 수정
    @PostMapping("/modify")
    public ResponseEntity<String> memberModify(@RequestBody MemberUpdateReqDto memberUpdateReqDto){
        return ResponseEntity.ok(memberService.memberModify(memberUpdateReqDto));
    }
    //회원 삭제
    @PostMapping("/delete")
    public ResponseEntity<String> memberDelete(@RequestBody Map<String,String> email){
        return ResponseEntity.ok(memberService.memberDelete(email.get("email")));
    }

    //프로필url 저장
    @GetMapping("/profileUrlSave")
    public ResponseEntity<Boolean> profileUrlSave(@RequestParam String email,@RequestParam String url){
        return ResponseEntity.ok(memberService.profileUrlSave(email,url));
    }
    //이메일로 프로필url 가져오기
    @GetMapping("/searchProfileUrl")
    public ResponseEntity<String> searchProfileUrl(@RequestParam String email){
        try{
            return ResponseEntity.ok(memberService.searchProfileUrl(email));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Failed to fetch profileUrl: " + e.getMessage());
        }
    }

    // 관리자 이메일 목록 가져오기
    @GetMapping("/adminEmails")
    public List<String> getAdminEmails() {
        return memberService.getAdminEmails();
    }
    //주민등록번호 가져오기
    @GetMapping("/getJumin")
    public ResponseEntity<String> getJumin(@RequestParam String email){
        return ResponseEntity.ok(memberService.getJumin(email));
    }
}
