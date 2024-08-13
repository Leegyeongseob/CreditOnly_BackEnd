package com.kh.CreditOnly_BackEnd.dto.reqdto;

import com.kh.CreditOnly_BackEnd.constant.LikeType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeReqDto {
    private Long commentId; // 댓글 ID
    private Long memberId; // 사용자 ID
    private LikeType likeType; // 좋아요 또는 싫어요 타입
}
