package com.kh.Palette_BackEnd.controller;

import com.kh.Palette_BackEnd.dto.reqdto.DateCourseReqDto;
import com.kh.Palette_BackEnd.dto.resdto.DateCourseResDto;
import com.kh.Palette_BackEnd.entity.DateCourseEntity;
import com.kh.Palette_BackEnd.service.DateCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/course") // 이 컨트롤러의 기본 URL 경로
//@CrossOrigin("http://localhost:3000") // CORS 설정: 특정 클라이언트에서의 접근을 허용
@Slf4j // 롬복을 사용하여 로깅을 위한 Logger를 자동으로 생성
public class DateCourseController {
    @Autowired
    private DateCourseService dateCourseService;

    // 모든 코스 조회 API
    @GetMapping
    public ResponseEntity<List<DateCourseResDto>> getAllCourses() {
        List<DateCourseResDto> courses = dateCourseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    // 특정 코스 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<DateCourseResDto> getCourseById(@PathVariable Long id) {
        return dateCourseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 새로운 코스 생성 API
    @PostMapping
    public ResponseEntity<DateCourseResDto> createCourse(@RequestBody  DateCourseReqDto reqCourseDTO) {
        log.info("Received request to create course with data: {}", reqCourseDTO);
        DateCourseResDto createdCourse = dateCourseService.createCourse(reqCourseDTO);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    // 코스 수정 API
    @PutMapping("/{id}")
    public ResponseEntity<DateCourseResDto> updateCourse(
            @PathVariable Long id,
            @RequestBody  DateCourseReqDto reqCourseDTO
    ) {
        DateCourseResDto updatedCourse = dateCourseService.updateCourse(id, reqCourseDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    // 코스 삭제 API
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        dateCourseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
    // 커플 이름으로 코스 목록 조회 API
    @GetMapping("/search/{coupleName}")
    public ResponseEntity<List<DateCourseResDto>> getCoursesByCoupleName(@PathVariable String coupleName) {
        System.out.println("커플 네임 컨트롤러 : " + coupleName);
        List<DateCourseResDto> courses = dateCourseService.getCoursesByCoupleName(coupleName);
        return ResponseEntity.ok(courses);
    }
}
