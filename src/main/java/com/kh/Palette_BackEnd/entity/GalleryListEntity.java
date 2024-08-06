package com.kh.Palette_BackEnd.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="GalleryList_TB")
public class GalleryListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="galleryList_id")
    private Long id;

    private String imgUrl;


    // 한명의 갤러리에 여러개의 이미지 사진
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="gallery_id")
    private GalleryEntity gallery;
}
