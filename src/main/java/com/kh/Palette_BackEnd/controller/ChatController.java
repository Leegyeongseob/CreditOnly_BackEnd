package com.kh.Palette_BackEnd.controller;

import com.kh.Palette_BackEnd.dto.ChatRoomResDto;
import com.kh.Palette_BackEnd.dto.reqdto.ChatRoomReqDto;
import com.kh.Palette_BackEnd.entity.ChatEntity;
import com.kh.Palette_BackEnd.dto.ChatMessageDto;
import com.kh.Palette_BackEnd.entity.ChatRoomEntity;
import com.kh.Palette_BackEnd.repository.ChattingRoomRepository;
import com.kh.Palette_BackEnd.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    private final ChattingRoomRepository chattingRoomRepository;
    @PostMapping("/new")
    public ResponseEntity<String> createRoom(@RequestBody ChatRoomReqDto chatRoomDto) {
        log.warn("chatRoomDto : {}", chatRoomDto);
        ChatRoomResDto room = chatService.createRoom(chatRoomDto.getName(),chatRoomDto.getSender(),chatRoomDto.getReceiver());
        System.out.println(room.getRoomId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/messages") //savemessage
    public ResponseEntity<Void> saveMessage(@RequestBody ChatMessageDto chatMessageDto) {
        chatService.saveMessage(chatMessageDto.getRoomId(), chatMessageDto.getSender(),chatMessageDto.getReceiver(), chatMessageDto.getMessage() );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ChatRoomResDto>> findAllRoom() {
        return ResponseEntity.ok(chatService.findAllRoom());
    }
    // 방 정보 가져오기
    @GetMapping("/room/{roomId}")
    public ResponseEntity<ChatRoomResDto> findRoomById(@PathVariable String roomId) {
        return ResponseEntity.ok(chatService.findRoomById(roomId));
    }
    @GetMapping("/messages/{roomId}")
    public ResponseEntity<List<ChatMessageDto>> getRecentMessages(@PathVariable String roomId) {
        List<ChatMessageDto> list= chatService.getRecentMessages(roomId);
        return ResponseEntity.ok(list);
    }

}


