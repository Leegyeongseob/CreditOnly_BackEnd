package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChattingRoomRepository extends JpaRepository<ChatRoomEntity,String> {
    List<ChatRoomEntity> findAll();

    List<ChatRoomEntity> findByFirstEmailOrSecondEmail(String firstEmail, String secondEmail);

    @Query("SELECT c.roomId FROM ChatRoomEntity c WHERE c.firstEmail = :email OR c.secondEmail = :email")
    List<String> findRoomIdsByEmail(@Param("email") String email);

    ChatRoomEntity deleteByRoomId(String chatRoomEntity);
}
