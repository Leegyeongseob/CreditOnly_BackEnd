package com.kh.Palette_BackEnd.service;

import com.kh.Palette_BackEnd.dto.reqdto.GalleryReqDto;
import com.kh.Palette_BackEnd.dto.reqdto.PaymentReqDto;
import com.kh.Palette_BackEnd.dto.reqdto.PaymentTemaReqDto;
import com.kh.Palette_BackEnd.dto.resdto.PaymentResDto;
import com.kh.Palette_BackEnd.entity.*;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import com.kh.Palette_BackEnd.repository.DiaryRepository;
import com.kh.Palette_BackEnd.repository.PaymentRepository;
import com.kh.Palette_BackEnd.repository.PaymentTemaRepository;
import com.kh.Palette_BackEnd.resdto.DiaryResDto;
import com.kh.Palette_BackEnd.resdto.GalleryResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PaymentTemaService {

    @Autowired
    private CoupleRepository coupleRepository;

    @Autowired
    private PaymentTemaRepository paymentTemaRepository;

    @PersistenceContext
    private EntityManager em;

    public String saveTemaPayment(PaymentTemaReqDto paymentTemaReqDto) {
        try {
            Optional<CoupleEntity> coupleOpt = coupleRepository.findByFirstEmailOrSecondEmail(paymentTemaReqDto.getCustomerEmail(), paymentTemaReqDto.getCustomerEmail());
            CoupleEntity couple = coupleOpt.orElseThrow(() -> new RuntimeException("Couple not found"));

            PaymentTemaEntity payment = new PaymentTemaEntity();
            payment.setPaymentId(paymentTemaReqDto.getPaymentId());
            payment.setOrderName(paymentTemaReqDto.getOrderName());
            payment.setTotalAmount(paymentTemaReqDto.getTotalAmount());
            payment.setCustomerName(paymentTemaReqDto.getCustomerName());
            payment.setCustomerPhone(paymentTemaReqDto.getCustomerPhone());
            payment.setCustomerEmail(paymentTemaReqDto.getCustomerEmail());
            payment.setStatus(paymentTemaReqDto.getStatus());
            payment.setCouple(couple); // 결제 엔티티에 커플 엔티티 설정

            paymentTemaRepository.saveAndFlush(payment);
            em.clear();

            return "구매 값이 추가 되었습니다.";
        } catch (Exception e) {
            log.error("Failed to save payment: {}", e.getMessage());
            throw new RuntimeException("Failed to save payment", e);
        }
    }

    // 테마 조회
    @Transactional
    public List<PaymentResDto> getTema(String email) {
        CoupleEntity couple = coupleRepository.findByFirstEmailOrSecondEmail(email, email)
                .orElseThrow(() -> new RuntimeException("Couple not found"));

        List<PaymentTemaEntity> temaEntities = paymentTemaRepository.findByCouple(couple);

        if (!temaEntities.isEmpty()) {
            List<String> temaList = temaEntities.stream()
                    .map(PaymentTemaEntity::getOrderName)
                    .collect(Collectors.toList());

            return temaEntities.stream()
                    .map(tema -> PaymentResDto.builder()
                            .customerEmail(tema.getCustomerEmail())
                            .orderName(temaList)  // 여기에 여러 개의 주문명을 포함
                            .build())
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}
