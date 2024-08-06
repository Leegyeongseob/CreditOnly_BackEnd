package com.kh.Palette_BackEnd.controller;

import com.kh.Palette_BackEnd.dto.ChatRoomResDto;
import com.kh.Palette_BackEnd.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/chatroom")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    @Autowired
    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

//    @PostMapping("/list")
//    public ResponseEntity<List<ChatRoomResDto>> chatRoomList(@RequestBody Map<String, String> email) {
//        return ResponseEntity.ok(chatRoomService.chatRoomList(email.get("email")));
//    }
    // DELETE 요청을 처리하는 핸들러 메서드
    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable String roomId) {
        return chatRoomService.deleteRoom(roomId);
    }
}


