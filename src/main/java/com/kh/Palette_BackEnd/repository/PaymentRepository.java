package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    Optional<PaymentEntity> findByCouple(CoupleEntity couple);
}
