package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity,Long> {
    List<DiaryEntity> findByCouple(CoupleEntity couple);
    Optional<DiaryEntity> findByCoupleAndAnniversary(CoupleEntity couple, LocalDate anniversary);

}
