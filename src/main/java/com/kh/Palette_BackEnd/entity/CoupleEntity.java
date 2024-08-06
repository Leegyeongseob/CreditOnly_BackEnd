package com.kh.Palette_BackEnd.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="Couple_TB")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoupleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="couple_id")
    private Long id;
    // 커플 이메일(아이디)
    private String firstEmail;
    private String secondEmail;
    @Column(unique = true,nullable = false)
    private String coupleName;

    private String dDay;

    @OneToMany(mappedBy = "couple", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DiaryEntity> diaries;

}
