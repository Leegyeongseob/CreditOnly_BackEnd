package com.kh.Palette_BackEnd.controller;


import com.kh.Palette_BackEnd.constant.Sex;
import com.kh.Palette_BackEnd.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final MainService mainService;
    //커플이름으로 닉네임 찾기
    @GetMapping("/searchNickName")
    public ResponseEntity<List<String>> searchNickName(@RequestParam String email,@RequestParam String coupleName){
        return ResponseEntity.ok(mainService.searchNickName(email,coupleName));
    }
    //커플이름으로 Dday 잧기
    @GetMapping("/searchDday")
    public ResponseEntity<String> searchDday(@RequestParam String coupleName){
        return ResponseEntity.ok(mainService.searchDday(coupleName));

    }
    //Dday 저장
    @GetMapping("/saveDday")
    public ResponseEntity<Boolean> saveDday(@RequestParam String coupleName, @RequestParam String dDay){
        return ResponseEntity.ok(mainService.saveDday(coupleName,dDay));
    }
    //방문검색 커플이름들 search
    @GetMapping("/visitCoupleNameSearchList")
    //커플 검색에 맞는 리스트 값.
    public ResponseEntity<List<String>>visitCoupleNameSearchList(@RequestParam String coupleName){
        return ResponseEntity.ok(mainService.visitCoupleNameSearchList(coupleName));
    }
    //본인 성별 가져오는 비동기 함수
    @GetMapping("/mySexSearch")
    public ResponseEntity<Sex>mySexSearch(@RequestParam String email){
        return ResponseEntity.ok(mainService.mySexSearch(email));
    }
}
