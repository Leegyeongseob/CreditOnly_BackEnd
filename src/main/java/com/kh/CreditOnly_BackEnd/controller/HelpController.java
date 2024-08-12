package com.kh.CreditOnly_BackEnd.controller;

import com.kh.CreditOnly_BackEnd.dto.reqdto.HelpReqDto;
import com.kh.CreditOnly_BackEnd.dto.resdto.HelpResDto;
import com.kh.CreditOnly_BackEnd.service.HelpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/help")
public class HelpController {

    @Autowired
    private HelpService helpService;

    @PostMapping("/send")
    public ResponseEntity<String> createHelpRequest(@RequestBody HelpReqDto helpReqDto) {
        helpService.createHelpRequest(helpReqDto);
        return ResponseEntity.ok("1:1 문의가 성공적으로 등록되었습니다.");
    }

    @GetMapping("/getAll")
    public List<HelpResDto> getAllHelpRequests(@RequestParam String email) {
        return helpService.getHelpRequestsByEmail(email);
    }
}
