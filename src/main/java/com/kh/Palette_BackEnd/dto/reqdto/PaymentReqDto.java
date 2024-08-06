package com.kh.Palette_BackEnd.dto.reqdto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentReqDto {
    private String paymentId;
    private String orderName;
    private int totalAmount;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String status;
}