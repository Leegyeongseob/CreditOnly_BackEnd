package com.kh.Palette_BackEnd.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Diary_TB")
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="diary_id")
    private Long id;

    private String email;


    private LocalDate anniversary;

    // 해당 날짜 상세내용
    private String dateContents;

    // 해당 날짜의 일기
    private String contents;

    @ManyToOne
    @JoinColumn(name = "couple_id", nullable = false)
    @JsonBackReference
    private CoupleEntity couple;


}
