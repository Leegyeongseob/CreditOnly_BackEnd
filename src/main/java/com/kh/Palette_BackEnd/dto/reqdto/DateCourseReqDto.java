package com.kh.Palette_BackEnd.dto.reqdto;

import com.kh.Palette_BackEnd.entity.DateCourseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DateCourseReqDto {
    private String title;
    private List<PlaceDTO> places;
    private String coupleName;

    @Data
    public static class PlaceDTO {
        private String place_name;
        private String road_address_name;
        private String phone;
        private String place_url;
        private String x;
        private String y;
    }
}