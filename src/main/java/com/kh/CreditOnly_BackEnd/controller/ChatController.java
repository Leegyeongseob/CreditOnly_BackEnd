package com.kh.CreditOnly_BackEnd.controller;

import com.kh.CreditOnly_BackEnd.dto.reqdto.AddMessageDTO;
import com.kh.CreditOnly_BackEnd.dto.reqdto.CreateConversationDTO;
import com.kh.CreditOnly_BackEnd.dto.reqdto.UpdateConversationDTO;
import com.kh.CreditOnly_BackEnd.entity.ChatConversationEntity;
import com.kh.CreditOnly_BackEnd.entity.ChatMessageEntity;
import com.kh.CreditOnly_BackEnd.entity.UserEntity;
import com.kh.CreditOnly_BackEnd.service.ChatService;
import com.kh.CreditOnly_BackEnd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;

    @Autowired
    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @PostMapping("/conversations")
    public ResponseEntity<ChatConversationEntity> createConversation(@RequestBody CreateConversationDTO request, @AuthenticationPrincipal UserDetails userDetails) {
        UserEntity user = userService.getUserByUsername(userDetails.getUsername());
        ChatConversationEntity conversation = chatService.createConversation(user, request.getTopic());
        return ResponseEntity.ok(conversation);
    }

    @PostMapping("/messages")
    public ResponseEntity<ChatMessageEntity> addMessage(@RequestBody AddMessageDTO request) {
        ChatConversationEntity conversation = chatService.getConversationById(request.getConversationId());
        ChatMessageEntity message = chatService.addMessage(conversation, request.getSender(), request.getContent());
        return ResponseEntity.ok(message);
    }

    @GetMapping("/conversations")
    public ResponseEntity<List<ChatConversationEntity>> getUserConversations(@AuthenticationPrincipal UserDetails userDetails) {
        UserEntity user = userService.getUserByUsername(userDetails.getUsername());
        List<ChatConversationEntity> conversations = chatService.getUserConversations(user);
        return ResponseEntity.ok(conversations);
    }

    @GetMapping("/conversations/{conversationId}/messages")
    public ResponseEntity<List<ChatMessageEntity>> getConversationMessages(@PathVariable Long conversationId) {
        ChatConversationEntity conversation = chatService.getConversationById(conversationId);
        List<ChatMessageEntity> messages = chatService.getConversationMessages(conversation);
        return ResponseEntity.ok(messages);
    }

    @DeleteMapping("/conversations/{conversationId}")
    public ResponseEntity<Void> deleteConversation(@PathVariable Long conversationId) {
        chatService.deleteConversation(conversationId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/conversations/{conversationId}")
    public ResponseEntity<ChatConversationEntity> updateConversation(@PathVariable Long conversationId, @RequestBody UpdateConversationDTO request) {
        ChatConversationEntity updatedConversation = chatService.updateConversation(conversationId, request.getNewTopic());
        return ResponseEntity.ok(updatedConversation);
    }
}

