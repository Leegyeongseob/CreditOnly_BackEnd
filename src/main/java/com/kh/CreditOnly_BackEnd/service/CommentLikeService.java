package com.kh.CreditOnly_BackEnd.service;

import com.kh.CreditOnly_BackEnd.constant.LikeType;
import com.kh.CreditOnly_BackEnd.dto.reqdto.CommentLikeReqDto;
import com.kh.CreditOnly_BackEnd.dto.resdto.CommentLikeResDto;
import com.kh.CreditOnly_BackEnd.entity.CommentEntity;
import com.kh.CreditOnly_BackEnd.entity.CommentLikeEntity;
import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import com.kh.CreditOnly_BackEnd.repository.CommentLikeRepository;
import com.kh.CreditOnly_BackEnd.repository.CommentRepository;
import com.kh.CreditOnly_BackEnd.repository.MemberRepository;
import com.kh.CreditOnly_BackEnd.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    // 댓글에 좋아요 또는 싫어요를 추가하거나 업데이트하는 메서드
    @Transactional
    public CommentLikeResDto likeOrDislikeComment(CommentLikeReqDto commentLikeReqDto) {
        // 댓글 조회
        CommentEntity comment = commentRepository.findById(commentLikeReqDto.getCommentId())
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        // 현재 로그인한 사용자의 MemberEntity 조회
        Long memberId = SecurityUtil.getCurrentMemberId();
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("현재 사용자를 찾을 수 없습니다."));

        // 기존에 해당 댓글에 대해 좋아요 또는 싫어요가 있는지 확인
        CommentLikeEntity existingLike = commentLikeRepository.findByCommentAndMember(comment, member)
                .orElse(null);

        if (existingLike != null) {
            // 기존의 좋아요/싫어요가 있으면 상태 업데이트
            if (existingLike.getLikeType() == commentLikeReqDto.getLikeType()) {
                // 같은 상태일 경우 삭제 처리
                commentLikeRepository.delete(existingLike);
            } else {
                // 상태가 다를 경우 업데이트
                existingLike.setLikeType(commentLikeReqDto.getLikeType());
                commentLikeRepository.save(existingLike);
            }
        } else {
            // 새로 추가
            CommentLikeEntity commentLikeEntity = CommentLikeEntity.builder()
                    .comment(comment)
                    .member(member)
                    .likeType(commentLikeReqDto.getLikeType())
                    .build();
            commentLikeRepository.save(commentLikeEntity);
        }

        // 좋아요 및 싫어요 수를 계산하여 응답 생성
        return calculateLikeDislikeCount(comment.getId());
    }

    // 댓글의 좋아요 및 싫어요 수를 계산하는 메서드
    @Transactional
    public CommentLikeResDto calculateLikeDislikeCount(Long commentId) {
        long likesCount = commentLikeRepository.countByCommentIdAndLikeType(commentId, LikeType.LIKE);
        long dislikesCount = commentLikeRepository.countByCommentIdAndLikeType(commentId, LikeType.DISLIKE);

        return CommentLikeResDto.builder()
                .commentId(commentId)
                .likesCount(likesCount)
                .dislikesCount(dislikesCount)
                .build();
    }
}
