package com.kh.Palette_BackEnd.dto;

import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
    public enum MessageType {
        ENTER, TALK, CLOSE
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String receiver; // 추가된 필드
    private String message;
    private LocalDateTime regDate;
}
