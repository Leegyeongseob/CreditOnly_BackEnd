package com.kh.CreditOnly_BackEnd.repository;

import com.kh.CreditOnly_BackEnd.entity.ChatConversationEntity;
import com.kh.CreditOnly_BackEnd.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatConversationRepository extends JpaRepository<ChatConversationEntity, Long> {
    List<ChatConversationEntity> findByUserOrderByCreatedAtDesc(UserEntity user);
}
