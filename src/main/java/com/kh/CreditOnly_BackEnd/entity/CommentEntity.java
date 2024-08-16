package com.kh.CreditOnly_BackEnd.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "comment") // 데이터베이스 테이블 이름
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // 기본 키 자동 생성 전략
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 설정
    @JoinColumn(name = "information_id") // 외래 키 설정
    private InformationEntity information; // 댓글이 속하는 정보 엔티티

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 설정
    @JoinColumn(name = "member_id") // 외래 키 설정
    private MemberEntity member; // 댓글 작성자

    @Column(nullable = false) // 값이 반드시 존재해야 함
    private String content; // 댓글 내용

    @Column(name = "published_date", nullable = false)
    private LocalDateTime publishedDate;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 설정
    @JoinColumn(name = "parent_id") // 부모 댓글을 참조하는 외래 키
    private CommentEntity parent; // 부모 댓글

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentLikeEntity> likes; // 댓글과 좋아요

    // 댓글과 대댓글을 포함한 트리 구조 구현 (부모 댓글 참조)
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentEntity> children; // 자식 댓글들
}
