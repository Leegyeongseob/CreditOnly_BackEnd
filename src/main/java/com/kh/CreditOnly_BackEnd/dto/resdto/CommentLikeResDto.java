package com.kh.CreditOnly_BackEnd.dto.resdto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeResDto {
    private Long commentId; // 댓글 ID
    private long likesCount; // 좋아요 수
    private long dislikesCount; // 싫어요 수
}
