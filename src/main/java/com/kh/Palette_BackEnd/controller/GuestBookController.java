package com.kh.Palette_BackEnd.controller;

import com.kh.Palette_BackEnd.dto.reqdto.GuestBookReqDto;
import com.kh.Palette_BackEnd.dto.resdto.GuestBookResDto;
import com.kh.Palette_BackEnd.entity.GuestBookEntity;
import com.kh.Palette_BackEnd.service.GuestBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/guestbook")
public class GuestBookController {

    @Autowired
    private GuestBookService guestBookService;

    @GetMapping("/all")
    public ResponseEntity<List<GuestBookResDto>> getAllGuestBooks() {
        List<GuestBookEntity> guestBooks = guestBookService.getAllGuestBooks();
        List<GuestBookResDto> responseDtoList = guestBooks.stream()
                .map(GuestBookResDto::convertToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtoList);
    }

    @GetMapping("/{coupleName}")
    public ResponseEntity<List<GuestBookResDto>> getGuestBookEntries(@PathVariable String coupleName) {
        List<GuestBookEntity> entries = guestBookService.getGuestBookEntries(coupleName);
        List<GuestBookResDto> responseDtoList = entries.stream()
                .map(GuestBookResDto::convertToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtoList);
    }

    @PostMapping
    public ResponseEntity<GuestBookResDto> addGuestBookEntry(@RequestBody GuestBookReqDto guestBookReqDto) {
        GuestBookEntity newEntry = guestBookService.addGuestBookEntry(
                guestBookReqDto.getCoupleName(),
                guestBookReqDto.getMemberEmail(),
                guestBookReqDto.getContents()
        );
        GuestBookResDto responseDto = GuestBookResDto.convertToResponseDto(newEntry);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{entryId}")
    public ResponseEntity<Void> updateGuestBookEntry(
            @PathVariable Long entryId,
            @RequestBody GuestBookReqDto guestBookReqDto
    ) {
        guestBookService.updateGuestBookEntry(
                entryId,
                guestBookReqDto.getContents(),
                guestBookReqDto.getMemberEmail()
        );
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{entryId}")
    public ResponseEntity<Void> deleteGuestBookEntry(
            @PathVariable Long entryId,
            @RequestParam String memberEmail
    ) {
        guestBookService.deleteGuestBookEntry(entryId, memberEmail);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<String> getProfileImgUrl(@PathVariable String email) {
        String profileImgUrl = guestBookService.getProfileImgUrlByEmail(email);
        return ResponseEntity.ok(profileImgUrl);
    }
    @GetMapping("/searchImgUrl/{email}")
    public ResponseEntity<String> searchImgUrl(@PathVariable String email) {
        try {
            String imageUrl = guestBookService.searchImgUrl(email);
            return ResponseEntity.ok(imageUrl); // 이미지 URL을 성공적으로 조회한 경우 반환
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // 커플을 찾지 못한 경우 404 응답 반환
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); // 예외가 발생한 경우 500 응답 반환
        }
    }

}
