package com.kh.Palette_BackEnd.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="DiaryCheckList_TB")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiaryCheckListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="diaryCheckList_id")
    private Long id;

    // 체크리스트 내용
    @ElementCollection
    @CollectionTable(name = "event", joinColumns = @JoinColumn(name = "diaryCheckList_id"))
    private List<Event> events;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private DiaryEntity diary;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Event {
        @Column(name = "is_event")
        private Boolean isEvent;
        private String eventText;
    }
}
