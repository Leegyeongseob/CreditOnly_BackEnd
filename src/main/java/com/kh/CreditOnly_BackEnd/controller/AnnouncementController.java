package com.kh.CreditOnly_BackEnd.controller;

import com.kh.CreditOnly_BackEnd.dto.reqdto.AnnouncementReqDto;
import com.kh.CreditOnly_BackEnd.dto.reqdto.HelpReqDto;
import com.kh.CreditOnly_BackEnd.dto.resdto.AnnouncementResDto;
import com.kh.CreditOnly_BackEnd.dto.resdto.HelpResDto;
import com.kh.CreditOnly_BackEnd.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/announcement")
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

    @PostMapping("/send")
    public ResponseEntity<String> createBoard(@RequestBody AnnouncementReqDto announcementReqDto) {
        announcementService.createBoard(announcementReqDto);
        return ResponseEntity.ok("게시글이 성공적으로 등록되었습니다.");
    }

    @GetMapping("/getAll")
    public List<AnnouncementResDto> getBoardsByClassTitle(@RequestParam String classTitle) {
        return announcementService.getBoardsByClassTitle(classTitle);
    }
}
