package com.kh.CreditOnly_BackEnd.dto.resdto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResDto {
    private Long id; // 댓글 ID
    private Long informationId; // 정보 ID
    private Long memberId; // 사용자 ID
    private String memberName;
    private String memberImg;
    private String content; // 댓글 내용
    private LocalDateTime publishedDate; // 작성날짜
    private Long parentId; // 부모 댓글 ID
    private Set<CommentResDto> children; // 대댓글 리스트
}
