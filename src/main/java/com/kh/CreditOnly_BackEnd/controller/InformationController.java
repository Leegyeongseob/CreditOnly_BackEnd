package com.kh.CreditOnly_BackEnd.controller;

import com.kh.CreditOnly_BackEnd.dto.reqdto.InformationReqDto;
import com.kh.CreditOnly_BackEnd.dto.resdto.InformationResDto;
import com.kh.CreditOnly_BackEnd.service.InformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/informations")
@RequiredArgsConstructor
public class InformationController {

    private final InformationService informationService;

    // 정보 생성 (관리자만 가능)
    @PostMapping
    public ResponseEntity<InformationResDto> createInformation(
            @RequestBody InformationReqDto informationReqDto) {
        InformationResDto createdInformation = informationService.createInformation(informationReqDto);
        return new ResponseEntity<>(createdInformation, HttpStatus.CREATED);
    }

    // 정보 수정 (관리자만 가능)
    @PutMapping("/{id}")
    public ResponseEntity<InformationResDto> updateInformation(
            @PathVariable Long id,
            @RequestBody InformationReqDto informationReqDto) {
        InformationResDto updatedInformation = informationService.updateInformation(id, informationReqDto);
        return new ResponseEntity<>(updatedInformation, HttpStatus.OK);
    }

    // 정보 삭제 (관리자만 가능)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInformation(@PathVariable Long id) {
        informationService.deleteInformation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 정보 ID로 조회
    @GetMapping("/{id}")
    public ResponseEntity<InformationResDto> getInformationById(@PathVariable Long id) {
        InformationResDto information = informationService.getInformationById(id);
        return new ResponseEntity<>(information, HttpStatus.OK);
    }

    // 모든 정보 조회 (페이지 네이션 제거)
    @GetMapping
    public ResponseEntity<List<InformationResDto>> getAllInformation() {
        List<InformationResDto> informationList = informationService.getAllInformation();
        return new ResponseEntity<>(informationList, HttpStatus.OK);
    }

    // 카테고리별 정보 조회 (페이지 네이션 제거)
    @GetMapping("/category/{category}")
    public ResponseEntity<List<InformationResDto>> getInformationByCategory(
            @PathVariable String category) {
        List<InformationResDto> informationList = informationService.getInformationByCategory(category);
        return new ResponseEntity<>(informationList, HttpStatus.OK);
    }
}
