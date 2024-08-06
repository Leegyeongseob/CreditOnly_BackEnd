package com.kh.Palette_BackEnd.controller;


import com.kh.Palette_BackEnd.dto.reqdto.DiaryReqDto;
import com.kh.Palette_BackEnd.entity.DiaryEntity;
import com.kh.Palette_BackEnd.resdto.DiaryResDto;
import com.kh.Palette_BackEnd.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/save")
    public ResponseEntity<DiaryEntity> saveDiary(@RequestBody DiaryReqDto diaryReqDto) {
        DiaryEntity savedDiary = diaryService.saveDiary(diaryReqDto);
        return ResponseEntity.ok(savedDiary);
    }

    @GetMapping("/load")
    public ResponseEntity<List<DiaryResDto>> getDiariesByEmail(@RequestParam String email) {
        List<DiaryResDto> diaries = diaryService.getDiariesByEmail(email);
        return ResponseEntity.ok(diaries);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteDiary(@RequestParam String email, @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        diaryService.deleteDiaryByEmailAndDate(email, localDate);
        return ResponseEntity.ok().build();
    }
}
