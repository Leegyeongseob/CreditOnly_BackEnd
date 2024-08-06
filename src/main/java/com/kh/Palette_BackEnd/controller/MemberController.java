package com.kh.Palette_BackEnd.controller;


import com.kh.Palette_BackEnd.dto.reqdto.MemberUpdateReqDto;
import com.kh.Palette_BackEnd.dto.resdto.MemberResDto;
import com.kh.Palette_BackEnd.resdto.GalleryResDto;
import com.kh.Palette_BackEnd.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
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
    //커플이름 뽑아오기
    @GetMapping("/renderCoupleNameSearch")
    public ResponseEntity<String> renderCoupleNameSearch(@RequestParam String email){
        return ResponseEntity.ok(memberService.renderCoupleNameSearch(email));
    }
    //커플이름으로
    @PostMapping("/isCoupleTrue")
    public ResponseEntity<Boolean> isCoupleTrue(@RequestBody Map<String,String> coupleName){
        return ResponseEntity.ok(memberService.isCoupleTrue(coupleName.get("coupleName")));
    }
    //프로필url 저장
    @GetMapping("/profileUrlSave")
    public ResponseEntity<Boolean> profileUrlSave(@RequestParam String email,@RequestParam String url){
        return ResponseEntity.ok(memberService.profileUrlSave(email,url));
    }
    //커플 프로필 url을 가져오기
    @GetMapping("/coupleProfileUrl")
    public ResponseEntity<List<String>> coupleProfileUrl(@RequestParam String coupleName,@RequestParam String email){
        return ResponseEntity.ok(memberService.coupleProfileUrl(coupleName, email));
    }

    //이메일로 이름 가져오기
    @GetMapping("/customer")
    public ResponseEntity<String> albumCustomer(@RequestParam String email) {
        try {
            String userName = memberService.albumCustomer(email).getName(); // 이름만 추출
            return ResponseEntity.ok(userName);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to fetch user name: " + e.getMessage());
        }
    }
    //커플 이름으로 첫번째 계정을 뽑아오기
    @GetMapping("/firstEmailGet")
    public ResponseEntity<String> firstEmailGet(@RequestParam String coupleName){
        try {
            String firstEmail = memberService.firstEmailGet(coupleName);
            return ResponseEntity.ok(firstEmail);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Failed to fetch user name: " + e.getMessage());
        }
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

}
