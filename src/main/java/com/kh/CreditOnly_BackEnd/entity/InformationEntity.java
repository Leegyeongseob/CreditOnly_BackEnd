package com.kh.CreditOnly_BackEnd.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "information")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(name = "published_date", nullable = false)
    private LocalDateTime publishedDate;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "category", length = 100)
    private String category; // 추가된 카테고리 필드

    // 댓글과 1:N 관계
    @OneToMany(mappedBy = "information", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentEntity> comments;
}

