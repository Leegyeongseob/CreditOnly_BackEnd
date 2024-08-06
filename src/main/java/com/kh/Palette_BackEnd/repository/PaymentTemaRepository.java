package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.PaymentEntity;
import com.kh.Palette_BackEnd.entity.PaymentTemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentTemaRepository extends JpaRepository<PaymentTemaEntity, Long> {

    List<PaymentTemaEntity> findByCouple(CoupleEntity couple);
//    Optional<PaymentTemaEntity> findByOrderName(String orderName);
}
