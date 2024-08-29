package com.kh.CreditOnly_BackEnd.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "ConsumerSurveyIndex_TB")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerSurveyIndexEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "CONTENT", columnDefinition = "TEXT")
    private String content;

    @Column(name = "CONTENT_keyword")
    private String contentKeyword;

    @Column(name = "WORD")
    private String word;

    @Column(name = "WORD_keyword")
    private String wordKeyword;

    @Column(name = "_index")
    private String index;

    @Column(name = "_score")
    private int score;

    @Column(name = "_type")
    private String type;
}