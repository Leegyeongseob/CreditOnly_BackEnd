package com.kh.Palette_BackEnd.service;

import com.kh.Palette_BackEnd.repository.ChatRepository;
import com.kh.Palette_BackEnd.repository.ChattingRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChattingRoomRepository chattingRoomRepository;
    private final ChatRepository chatRepository;
    @Transactional
    public ResponseEntity<String> deleteRoom(String roomId) {
        try {
            // 1. 채팅방에 연결된 채팅 메시지 삭제
            chatRepository.deleteByChatRoom_RoomId(roomId);

            // 2. 채팅방 삭제
            chattingRoomRepository.deleteById(roomId);

            // 채팅방 삭제 성공 시 메시지 반환
            return ResponseEntity.ok("Room deleted successfully");
        } catch (Exception e) {
            // 예외 발생 시 실패 메시지 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete room: " + e.getMessage());
        }
    }
}
