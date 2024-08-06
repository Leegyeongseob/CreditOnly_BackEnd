package com.kh.Palette_BackEnd.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "DateCourse_TB")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DateCourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dateCourse_id")
    private Long id;

    // 데이트 코스명
    private String title;

    // 코스 장소들
    @OneToMany(mappedBy = "dateCourse", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("placeOrder ASC") // order 필드를 기준으로 오름차순 정렬
    private final List<PlaceEntity> places = new ArrayList<>();

    // 저장 날짜
    private LocalDate date;

    // 커플이 둘다 저장된 코스를 볼 수 있어야 함.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupleName")
    private CoupleEntity couple;

    @PrePersist
    public void prePersist() {
        date = LocalDate.now();
    }
}
