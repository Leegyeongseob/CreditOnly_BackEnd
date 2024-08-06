package com.kh.Palette_BackEnd.dto.reqdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomReqDto { // 채팅방 생성 요청 시 전달되는 데이터
    private String name;
    private String sender;
    private String receiver;
}
