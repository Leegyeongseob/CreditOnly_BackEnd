package com.kh.CreditOnly_BackEnd.controller;

import com.kh.CreditOnly_BackEnd.service.ChatBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chatbot")
@RequiredArgsConstructor
@Slf4j
public class ChatBotController {
    private final ChatBotService chatBotService;
    //ChatBot 데이터 받아오기
    @GetMapping("/fetch")
    public ResponseEntity<List<Map<String, Object>>> searchChatBotDataFetch(@RequestParam String activeTopic,@RequestParam String message){
        return ResponseEntity.ok(chatBotService.searchChatBotDataFetch(activeTopic,message));
    }
}
