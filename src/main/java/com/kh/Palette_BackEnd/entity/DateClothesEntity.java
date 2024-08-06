package com.kh.Palette_BackEnd.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="DateClothes_TB")
public class DateClothesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="dateClothes_id")
    private Long id;
    private String topClothesUrl;
    private String pantsClothesUrl;
    private String onePieceUrl;
    @Column(nullable = false)
    private String shoesUrl;

    // 커플이 둘다
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="couple_id")
    private CoupleEntity couple;


}
