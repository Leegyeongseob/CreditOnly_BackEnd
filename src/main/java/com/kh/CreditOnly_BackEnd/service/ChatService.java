package com.kh.CreditOnly_BackEnd.service;

import com.kh.CreditOnly_BackEnd.entity.ChatConversationEntity;
import com.kh.CreditOnly_BackEnd.entity.ChatMessageEntity;
import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import com.kh.CreditOnly_BackEnd.repository.ChatConversationRepository;
import com.kh.CreditOnly_BackEnd.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {
    private final ChatConversationRepository conversationRepository;
    private final ChatMessageRepository messageRepository;

    @Autowired
    public ChatService(ChatConversationRepository conversationRepository, ChatMessageRepository messageRepository) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    public ChatConversationEntity createConversation(MemberEntity member, String topic) {
        ChatConversationEntity conversation = new ChatConversationEntity();
        conversation.setMember(member);
        conversation.setTopic(topic);
        conversation.setCreatedAt(LocalDateTime.now());
        return conversationRepository.save(conversation);
    }

    public ChatMessageEntity addMessage(ChatConversationEntity conversation, String sender, String content) {
        ChatMessageEntity message = new ChatMessageEntity();
        message.setConversation(conversation);
        message.setSender(sender);
        message.setContent(content);
        message.setSentAt(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<ChatConversationEntity> getUserConversations(MemberEntity member) {
        return conversationRepository.findByMemberOrderByCreatedAtDesc(member);
    }

    public List<ChatMessageEntity> getConversationMessages(ChatConversationEntity conversation) {
        return messageRepository.findByConversationOrderBySentAt(conversation);
    }

    public ChatConversationEntity getConversationById(Long conversationId) {
        return conversationRepository.findById(conversationId).orElseThrow(() -> new RuntimeException("Conversation not found"));
    }

    public void deleteConversation(Long conversationId) {
        conversationRepository.deleteById(conversationId);
    }

    public ChatConversationEntity updateConversation(Long conversationId, String newTopic) {
        ChatConversationEntity conversation = getConversationById(conversationId);
        conversation.setTopic(newTopic);
        return conversationRepository.save(conversation);
    }
}
