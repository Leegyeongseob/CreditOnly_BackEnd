package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.dto.resdto.DateCourseResDto;
import com.kh.Palette_BackEnd.entity.DateCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DateCourseRepository extends JpaRepository<DateCourseEntity, Long> {

    List<DateCourseEntity> findByCouple_CoupleName(String coupleName);

}
