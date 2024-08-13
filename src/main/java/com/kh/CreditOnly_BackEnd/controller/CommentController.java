package com.kh.CreditOnly_BackEnd.controller;

import com.kh.CreditOnly_BackEnd.dto.reqdto.CommentReqDto;
import com.kh.CreditOnly_BackEnd.dto.resdto.CommentResDto;
import com.kh.CreditOnly_BackEnd.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping
    public ResponseEntity<CommentResDto> createComment(@RequestBody CommentReqDto commentReqDto) {
        CommentResDto createdComment = commentService.createComment(commentReqDto);
        return ResponseEntity.ok(createdComment);
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<CommentResDto> updateComment(
            @PathVariable Long id,
            @RequestBody CommentReqDto commentReqDto) {
        CommentResDto updatedComment = commentService.updateComment(id, commentReqDto);
        return ResponseEntity.ok(updatedComment);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    // 특정 정보에 대한 댓글 조회
    @GetMapping("/information/{informationId}")
    public ResponseEntity<List<CommentResDto>> getCommentsByInformationId(
            @PathVariable Long informationId) {
        List<CommentResDto> comments = commentService.getCommentsByInformationId(informationId);
        return ResponseEntity.ok(comments);
    }

    // 모든 댓글 조회 (관리자 전용)
    @GetMapping("/all")
    public ResponseEntity<List<CommentResDto>> getAllComments() {
        List<CommentResDto> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }
}
