package com.kh.CreditOnly_BackEnd.entity;

import com.kh.CreditOnly_BackEnd.constant.LikeType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comment_like") // 데이터베이스 테이블 이름
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // 기본 키 자동 생성 전략
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 설정
    @JoinColumn(name = "comment_id") // 외래 키 설정
    private CommentEntity comment; // 좋아요가 달린 댓글

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 설정
    @JoinColumn(name = "member_id") // 외래 키 설정
    private MemberEntity member; // 좋아요를 누른 회원

    @Enumerated(EnumType.STRING) // ENUM 타입을 문자열로 저장
    @Column(name = "like_type", nullable = false) // 좋아요 유형
    private LikeType likeType; // 좋아요 유형 (ENUM 타입 사용)
}
