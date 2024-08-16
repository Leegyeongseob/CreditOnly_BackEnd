package com.kh.CreditOnly_BackEnd.controller;

import com.kh.CreditOnly_BackEnd.dto.reqdto.AnnouncementReqDto;
import com.kh.CreditOnly_BackEnd.dto.reqdto.HelpReqDto;
import com.kh.CreditOnly_BackEnd.dto.resdto.AnnouncementResDto;
import com.kh.CreditOnly_BackEnd.dto.resdto.HelpResDto;
import com.kh.CreditOnly_BackEnd.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // 특정 이메일에 해당하는 알림 가져오기
    @GetMapping("/notifications")
    public ResponseEntity<List<AnnouncementResDto>> getNotificationsByEmail(@RequestParam String email) {
        List<AnnouncementResDto> notifications = announcementService.getNotificationsByEmail(email);
        return ResponseEntity.ok(notifications);
    }

    // 특정 알림을 읽음 처리
    @PostMapping("/markAsRead")
    public ResponseEntity<String> markNotificationAsRead(@RequestParam Long id, @RequestParam String email) {
        try {
            announcementService.markAsRead(id, email);
            return ResponseEntity.ok("Notification marked as read");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // 공지사항 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAnnouncement(@PathVariable Long id) {
        try {
            announcementService.deleteAnnouncement(id);
            return ResponseEntity.ok("공지사항이 성공적으로 삭제되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
