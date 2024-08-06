package com.kh.Palette_BackEnd.controller;

import com.kh.Palette_BackEnd.dto.ChatMessageDto;
import com.kh.Palette_BackEnd.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat2")
@CrossOrigin(origins = "http://localhost:3000")
public class CoupleEmailController {
    private final ChatService chatService;
    //커플이름 뽑아오기
    @PostMapping("/coupleEmail")
    public ResponseEntity<List<String>> coupleEmail(@RequestBody Map<String,String> email) {
        return ResponseEntity.ok(chatService.coupleEmail(email.get("email")));
    }
}

