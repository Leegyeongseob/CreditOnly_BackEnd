package com.kh.CreditOnly_BackEnd.controller;

import com.kh.CreditOnly_BackEnd.dto.reqdto.CommentLikeReqDto;
import com.kh.CreditOnly_BackEnd.dto.resdto.CommentLikeResDto;
import com.kh.CreditOnly_BackEnd.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment-likes")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    // 댓글에 좋아요 또는 싫어요 추가하거나 업데이트
    @PostMapping
    public ResponseEntity<CommentLikeResDto> likeOrDislikeComment(@RequestBody CommentLikeReqDto commentLikeReqDto) {
        CommentLikeResDto response = commentLikeService.likeOrDislikeComment(commentLikeReqDto);
        return ResponseEntity.ok(response);
    }

    // 댓글의 좋아요 및 싫어요 수 조회
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentLikeResDto> getLikeDislikeCount(@PathVariable Long commentId) {
        CommentLikeResDto response = commentLikeService.calculateLikeDislikeCount(commentId);
        return ResponseEntity.ok(response);
    }
}
