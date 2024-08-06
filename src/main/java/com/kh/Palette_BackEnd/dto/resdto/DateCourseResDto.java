package com.kh.Palette_BackEnd.dto.resdto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class DateCourseResDto {
    private Long id;
    private String title;
    private LocalDate date;
    private String coupleName; // 커플 이름 추가
    private List<PlaceDTO> places;


    @Data
    public static class PlaceDTO {
        private Long id;
        private String place_name;
        private String road_address_name;
        private String phone;
        private String place_url;
        private String x;
        private String y;
        private int placeOrder;
    }
}