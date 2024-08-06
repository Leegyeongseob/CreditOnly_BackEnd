package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.BoardEntity;
import com.kh.Palette_BackEnd.entity.GuestBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    Page<BoardEntity> findByCouple_FirstEmailAndCouple_SecondEmailOrderByRegDateDesc(String firstEmail, String secondEmail, Pageable pageable);
    List<BoardEntity> findByCouple(CoupleEntity couple);
    Optional<BoardEntity> findById(Long id);
}
