package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.DiaryCheckListEntity;
import com.kh.Palette_BackEnd.entity.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiaryCheckListRepository extends JpaRepository<DiaryCheckListEntity,Long> {
    Optional<DiaryCheckListEntity> findByDiary(DiaryEntity diary);

    void deleteByDiary(DiaryEntity diary);
}
