package com.kh.CreditOnly_BackEnd.repository;

import com.kh.CreditOnly_BackEnd.constant.LikeType;
import com.kh.CreditOnly_BackEnd.entity.CommentLikeEntity;
import com.kh.CreditOnly_BackEnd.entity.CommentEntity;
import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Long> {

    // 특정 댓글과 사용자에 대한 좋아요/싫어요 상태 조회
    Optional<CommentLikeEntity> findByCommentAndMember(CommentEntity comment, MemberEntity member);

    // 특정 댓글의 좋아요 수 조회
    long countByCommentIdAndLikeType(Long commentId, LikeType likeType);

    void deleteAllByMemberId(Long id);
}
