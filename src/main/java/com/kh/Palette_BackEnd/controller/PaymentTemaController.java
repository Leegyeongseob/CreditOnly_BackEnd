package com.kh.Palette_BackEnd.controller;


import com.kh.Palette_BackEnd.dto.reqdto.PaymentTemaReqDto;
import com.kh.Palette_BackEnd.dto.resdto.PaymentResDto;
import com.kh.Palette_BackEnd.service.PaymentTemaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/paymenttema")
@RequiredArgsConstructor
public class PaymentTemaController {
    @Autowired
    private PaymentTemaService paymentTemaService;

    // 테마 구매
    @PostMapping("/tema")
    public ResponseEntity<String> temaPayment(@RequestBody PaymentTemaReqDto paymentTemaReqDto) {
        String total_price = paymentTemaService.saveTemaPayment(paymentTemaReqDto);
        return ResponseEntity.ok(total_price);
    }

    // 테마 조회
    @GetMapping("/temaload")
    public ResponseEntity<List<PaymentResDto>> getTema(@RequestParam String email) {
        List<PaymentResDto> temalist = paymentTemaService.getTema(email);
        return ResponseEntity.ok(temalist);
    }
}
