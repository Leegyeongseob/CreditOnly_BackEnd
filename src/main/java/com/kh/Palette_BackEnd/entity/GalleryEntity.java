package com.kh.Palette_BackEnd.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kh.Palette_BackEnd.constant.PagePlusSellStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Gallery_TB")
public class GalleryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="gallery_id")
    private Long id;

    @Column(nullable = false, length = 255)
    private String email;

    private int openPage;

    @Enumerated(EnumType.STRING)
    private PagePlusSellStatus pagePlusSellStatus;

    // 커플모두 볼수 있어야함. 저장 데이터 불러오기
    @ManyToOne
    @JoinColumn(name = "couple_id")
    private CoupleEntity couple;

    // 사진 올리는 계정 조인
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private MemberEntity member;
}
