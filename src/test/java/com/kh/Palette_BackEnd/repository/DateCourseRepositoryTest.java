//package com.kh.Palette_BackEnd.repository;
//
//import com.kh.Palette_BackEnd.entity.DateCourseEntity;
//import com.kh.Palette_BackEnd.entity.PlaceEntity;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//@TestPropertySource(locations = "classpath:application_test.properties")
//class DateCourseRepositoryTest {
//    @Autowired
//    DateCourseRepository dateCourseRepository;
//
//    @Test
//    @DisplayName("장소 저장 테스트")
//    public void createCourseTest() {
//        List<DateCourseEntity> dateCour = new ArrayList<>();
//
//        for (int i = 1; i <= 10; i++) {
//            DateCourseEntity dateCourseEntity = new DateCourseEntity();
//            dateCourseEntity.setTitle("test" + i);
//            dateCourseEntity.set(new ArrayList<>());
//            dateCourseEntity.setDate(LocalDate.now());
//            dateCourseRepository.save(dateCourseEntity);
//
//            PlaceEntity place1 = new PlaceEntity();
//            place1.setPlaceName("테스트 장소 " + i);
//            place1.setRoadAddress("서울특별시 강남구 가로수길 " + i + "번길 " + i + "-" + i);
//            place1.setPhone("02-1234-567" + i);
//            place1.setPlaceUrl("http://place.example.com/" + i);
//            place1.setLatitude(12.34 + i);
//            place1.setLongitude(43.21 + i);
//            place1.setDateCourse(dateCourseEntity); // 연관 관계 설정
//            place1.setDateCourse(dateCourseEntity);
//            dateCour.add(dateCourseEntity);
//
//        }
//    }
//
//    @Test
//    @DisplayName("장소 전체 조회 테스트")
//    public void findCourseAllTest() {
//        this.createCourseTest();
//        List<DateCourseEntity> dateCourseEntityList = dateCourseRepository.findAll();
//        for (DateCourseEntity e : dateCourseEntityList) System.out.println(e.toString());
//    }
//
//}