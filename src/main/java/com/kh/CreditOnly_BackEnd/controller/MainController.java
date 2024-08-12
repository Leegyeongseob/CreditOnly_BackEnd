package com.kh.CreditOnly_BackEnd.controller;


import com.kh.CreditOnly_BackEnd.constant.Sex;
import com.kh.CreditOnly_BackEnd.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final MainService mainService;

    //본인 성별 가져오는 비동기 함수
    @GetMapping("/mySexSearch")
    public ResponseEntity<Sex>mySexSearch(@RequestParam String email){
        return ResponseEntity.ok(mainService.mySexSearch(email));
    }
    @GetMapping("/dataSearch")
    public ResponseEntity<List<Map<String, Object>>> dataSearch(@RequestParam String email,@RequestParam String data){
        return ResponseEntity.ok(mainService.dataSearch(email,data));
    }
}
