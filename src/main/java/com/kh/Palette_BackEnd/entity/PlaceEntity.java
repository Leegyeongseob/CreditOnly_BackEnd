package com.kh.Palette_BackEnd.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Place_TB")
@NoArgsConstructor
@AllArgsConstructor
public class PlaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "place_id")
    private Long placeId;

    // 장소 이름
    @Column(nullable = false)
    private  String place_name;

    // 장소 주소
    private String road_address_name;

    // 장소 전화번호
    private String phone;

    // 장소 정보 url
    private String place_url;

    // 장소 위도

    private String x;

    // 장소 경도

    private String y;

    // DateCourseEntity와의 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dateCourse_id")
    private DateCourseEntity dateCourse;

    // 순서
    private int placeOrder;
}
