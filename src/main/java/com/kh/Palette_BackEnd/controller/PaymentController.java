package com.kh.Palette_BackEnd.controller;


import com.kh.Palette_BackEnd.dto.reqdto.GalleryReqDto;
import com.kh.Palette_BackEnd.dto.reqdto.PaymentReqDto;
import com.kh.Palette_BackEnd.dto.resdto.PaymentResDto;
import com.kh.Palette_BackEnd.entity.GalleryEntity;
import com.kh.Palette_BackEnd.entity.PaymentEntity;
import com.kh.Palette_BackEnd.resdto.DiaryResDto;
import com.kh.Palette_BackEnd.resdto.GalleryResDto;
import com.kh.Palette_BackEnd.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    // 페이지 구매
    @PostMapping("/complete")
    public ResponseEntity<String> completePayment(@RequestBody PaymentReqDto paymentReqDto) {
        String total_price = paymentService.savePayment(paymentReqDto);
        return ResponseEntity.ok(total_price);
    }

    // 페이지 구매 값 불러오기
    @GetMapping("/amount")
    public ResponseEntity<Integer> getAmount(@RequestParam String email) {
        int totalAmount = paymentService.getAmount(email); // 수정: 기본형 int를 사용하여 null 문제 방지
        return ResponseEntity.ok(totalAmount);
    }
}
