package com.kh.Palette_BackEnd.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.Palette_BackEnd.dto.ChatMessageDto;
import com.kh.Palette_BackEnd.dto.ChatRoomResDto;
import com.kh.Palette_BackEnd.entity.BoardEntity;
import com.kh.Palette_BackEnd.entity.ChatEntity;
import com.kh.Palette_BackEnd.entity.ChatRoomEntity;
import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.repository.ChatRepository;
import com.kh.Palette_BackEnd.repository.ChattingRoomRepository;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import com.kh.Palette_BackEnd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;
    private final ChatRepository chatRepository;
    private final CoupleRepository coupleRepository;
    private Map<String, List<WebSocketSession>> userSessions = new HashMap<>();
    private Map<String, ChatRoomResDto> chatRooms;
    private final ChattingRoomRepository chattingRoomRepository;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoomResDto> findAllRoom() { // 채팅방 리스트 반환
        return new ArrayList<>(chatRooms.values());
    }

//    public List<ChatRoomResDto> findAllRoom(String email) {
//        List<String> roomIds = chattingRoomRepository.findRoomIdsByEmail(email);
//        List<ChatRoomResDto> allRooms = new ArrayList<>();
//
//        for (String roomId : roomIds) {
//            // chatRooms에서 roomId에 해당하는 ChatRoomResDto를 가져와서 리스트에 추가
//            if (chatRooms.containsKey(roomId)) {
//                allRooms.add(chatRooms.get(roomId));
//            }
//        }
//
//        return allRooms;
//    }

    public ChatRoomResDto findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    public ChatRoomResDto createRoom(String name, String sender, String receiver) {
        String randomId = UUID.randomUUID().toString();
        log.info("UUID : " + randomId);
        ChatRoomResDto chatRoom = ChatRoomResDto.builder() // 채팅방 생성
                .roomId(randomId)
                .name(name)
                .regDate(LocalDateTime.now())
                .firstEmail(sender)
                .secondEmail(receiver)
                .build();
        chatRooms.put(randomId, chatRoom);  // 방 생성, 키를 UUID로 하고 방 정보를 값으로 저장
        ChatRoomEntity room = ChatRoomEntity.builder()
                .roomId(randomId)
                .firstEmail(sender)
                .secondEmail(receiver)
                .createdAt(LocalDateTime.now())
                .build();
        chattingRoomRepository.save(room);
        ChatRoomEntity newChatRoom = new ChatRoomEntity();
        newChatRoom.setRoomId(chatRoom.getRoomId());
        newChatRoom.setCreatedAt(chatRoom.getRegDate());
        newChatRoom.setFirstEmail(chatRoom.getFirstEmail());
        newChatRoom.setSecondEmail(chatRoom.getSecondEmail());
        chatRooms.put(randomId, chatRoom);
        return chatRoom;
    }


    // 채팅방에 입장한 세션 추가

//    public void removeUserSession(String userId) {
//        userSessions.remove(userId);
//        log.debug("Session removed for user {}: {}", userId);
//    }

    public void removeRoom(String roomId) { // 방 삭제
        ChatRoomResDto room = chatRooms.get(roomId); // 방 정보 가져오기
        if (room != null) { // 방이 존재하면
            if (room.isSessionEmpty()) { // 방에 세션이 없으면
                chatRooms.remove(roomId); // 방 삭제
            }
        }
    }

    // 채팅방에 입장한 세션 추가
    public void addSessionAndHandleEnter(String roomId, WebSocketSession session, ChatMessageDto chatMessage) {
        ChatRoomResDto room = findRoomById(roomId);
        if (room != null) {
            room.getSessions().add(session); // 채팅방에 입장한 세션 추가
            log.debug("새 세션 추가!! " + session);
        }
    }


    public void saveMessage(String roomId, String sender, String receiver, String chatData) {
        ChatRoomEntity chatRoom = chattingRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("해당채팅방없음"));
        ChatEntity chat = ChatEntity.builder()
                .chatRoom(chatRoom)
                .chatData(chatData)
                .sender(sender)
                .receiver(receiver)
                .regDate(LocalDateTime.now())
                .build();
        chatRepository.save(chat);
    }

    public void sendMessageToAll(String roomId, ChatMessageDto message) {
        ChatRoomResDto room = findRoomById(roomId);
        if (room != null) {
            for (WebSocketSession session : room.getSessions()) {
                sendMessage(session, message);
            }
        }
    }

    public List<ChatEntity> getChatMessages(String sender, String receiver) {
        List<ChatEntity> messages = chatRepository.findBySenderAndReceiverOrReceiverAndSenderOrderByRegDateAsc(sender, receiver, sender, receiver);
        return messages;
    }

    private <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    // 세션 수 가져오기
    public int getSessionCount(String roomId) {
        List<WebSocketSession> sessions = userSessions.get(roomId);
        return sessions != null ? sessions.size() : 0;
    }

    //이전채팅 가져오기
    public List<ChatMessageDto> getRecentMessages(String roomId) {
        List<ChatMessageDto> chatList = new LinkedList<>();

        // roomId로 ChatRoomEntity를 찾음
        ChatRoomEntity chatRoom = chattingRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("해당 채팅방이 존재하지 않습니다."));

        // ChatRoomEntity에 해당하는 ChatEntity 리스트를 조회
        List<ChatEntity> chattingList = chatRepository.findByChatRoom(chatRoom);

        // 조회된 ChatEntity를 ChatMessageDto로 변환하여 chatList에 추가
        for (ChatEntity e : chattingList) {
            ChatMessageDto dto = ChatMessageDto.builder()
                    .message(e.getChatData())
                    .roomId(e.getChatRoom().getRoomId())
                    .sender(e.getSender())
                    .receiver(e.getReceiver())
                    .regDate(e.getRegDate())
                    .build();
            chatList.add(dto);
        }
        // 채팅 메시지 리스트 반환
        return chatList;
    }

    public List<String> coupleEmail(String email) {
        log.debug("받은 이메일: {}", email);
        List<String> emailList = new ArrayList<>();
        try {
            Optional<CoupleEntity> coupleEntityOptional = coupleRepository.findByFirstEmailOrSecondEmail(email, email);

            coupleEntityOptional.ifPresent(coupleEntity -> {
                String firstEmail = coupleEntity.getFirstEmail();
                String secondEmail = coupleEntity.getSecondEmail();
                log.debug("커플 엔터티 발견: {}, {}", firstEmail, secondEmail);

                if (email.equals(firstEmail)) {
                    emailList.add(firstEmail);
                    emailList.add(secondEmail);
                } else if (email.equals(secondEmail)) {
                    emailList.add(secondEmail);
                    emailList.add(firstEmail);
                }
            });

            if (!coupleEntityOptional.isPresent()) {
                log.debug("이메일에 해당하는 커플 엔터티를 찾을 수 없음: {}", email);
            }
        } catch (Exception e) {
            log.error("커플 이메일 가져오는 중 오류 발생", e);
        }
        return emailList;
    }

    public void removeSessionAndHandleExit(String roomId, WebSocketSession session, ChatMessageDto chatMessage) {
        ChatRoomResDto room = findRoomById(roomId); // 채팅방 정보 가져오기
        if (room != null) {
            room.getSessions().remove(session); // 채팅방에서 퇴장한 세션 제거 // Sessions는 Set<WebSocketSession> 타입, Set에서 특정 session 제거
            if (chatMessage.getSender() != null) { // 채팅방에서 퇴장한 사용자가 있으면
            }
            log.debug("이거 세션 지웠다 : " + session);
            if (room.isSessionEmpty()) {
                removeRoom(roomId);
            }
        }
    }
}





