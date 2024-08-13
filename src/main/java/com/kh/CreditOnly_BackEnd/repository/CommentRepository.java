package com.kh.CreditOnly_BackEnd.repository;

import com.kh.CreditOnly_BackEnd.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// CommentEntity를 위한 레포지토리 인터페이스
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // 특정 정보 ID에 따른 댓글들을 조회하는 메서드
    List<CommentEntity> findByInformationId(Long informationId);
}
