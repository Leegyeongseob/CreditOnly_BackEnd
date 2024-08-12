package com.kh.CreditOnly_BackEnd.controller;

import com.kh.CreditOnly_BackEnd.dto.resdto.SettingResDto;
import com.kh.CreditOnly_BackEnd.service.SettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/setting")
public class SettingController {
    private final SettingService settingService;

    // 회원 정보 가져오는 비동기 함수
    @GetMapping("/getInfo")
    public SettingResDto getUserInfo(@RequestParam String email) {
        log.info("Fetching user info for email: {}", email);
        return settingService.getUserInfo(email);
    }
}