package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<PlaceEntity, Long> {
    // 특정 코스의 모든 장소를 삭제하는 메소드
    void deleteAllByDateCourseId(Long dateCourseId);
}
