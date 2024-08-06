package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.CoupleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CoupleRepository extends JpaRepository<CoupleEntity,Long> {
    boolean existsByCoupleName(String coupleName);
    Optional<CoupleEntity> findByCoupleName(String coupleName);
    boolean existsBySecondEmail(String coupleName);
    Optional<CoupleEntity> findByFirstEmail(String Email);
    Optional<CoupleEntity> findBySecondEmail(String Email);
    Optional<CoupleEntity> findByFirstEmailOrSecondEmail(String firstEmail, String secondEmail);
    Optional<List<CoupleEntity>> findByCoupleNameContaining(String coupleName);

//    boolean existByDatingDay(String coupleName);
}
