package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.GuestBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuestBookRepository extends JpaRepository<GuestBookEntity, Long> {
    List<GuestBookEntity> findByCouple(CoupleEntity couple);
    List<GuestBookEntity> findAll(); // 모든 방명록을 조회하는 메서드
}
