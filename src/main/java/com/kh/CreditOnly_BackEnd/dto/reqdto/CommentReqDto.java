package com.kh.CreditOnly_BackEnd.dto.reqdto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentReqDto {
    private Long informationId; // 정보 ID
    private Long memberId; // 사용자 ID
    private String content; // 댓글 내용
    private Long parentId; // 부모 댓글 ID (대댓글인 경우)
}
