package com.kh.CreditOnly_BackEnd.repository;

import com.kh.CreditOnly_BackEnd.entity.ChatConversationEntity;
import com.kh.CreditOnly_BackEnd.entity.ChatMessageEntity;
import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
    List<ChatMessageEntity> findByConversationOrderBySentAt(ChatConversationEntity conversation);

    void deleteAllBySender(String email);
}
