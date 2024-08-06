package com.kh.Palette_BackEnd.dto.resdto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResDto {
    private String customerEmail;
    private int totalAmount;
    private List<String> orderName;
}
