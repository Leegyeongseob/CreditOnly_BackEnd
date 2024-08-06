package com.kh.Palette_BackEnd.service;


import com.kh.Palette_BackEnd.dto.reqdto.DiaryReqDto;
import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.DiaryCheckListEntity;
import com.kh.Palette_BackEnd.entity.DiaryEntity;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import com.kh.Palette_BackEnd.repository.DiaryCheckListRepository;
import com.kh.Palette_BackEnd.repository.DiaryRepository;
import com.kh.Palette_BackEnd.resdto.DiaryResDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor

public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final DiaryCheckListRepository diaryCheckListRepository;

    @Autowired
    private CoupleRepository coupleRepository;

    //다이어리 저장
    @Transactional
    public DiaryEntity saveDiary(DiaryReqDto diaryReqDto) {
        try {
            Optional<CoupleEntity> coupleOpt = coupleRepository.findByFirstEmailOrSecondEmail(diaryReqDto.getEmail(), diaryReqDto.getEmail());
            CoupleEntity couple = coupleOpt.orElseThrow(() -> new RuntimeException("Couple not found"));
            LocalDate anniversary = diaryReqDto.getAnniversary();

            Optional<DiaryEntity> existingDiaryOpt = diaryRepository.findByCoupleAndAnniversary(couple, anniversary);

            DiaryEntity diaryEntity;
            if (existingDiaryOpt.isPresent()) {
                diaryEntity = existingDiaryOpt.get();
                diaryEntity.setDateContents(diaryReqDto.getDateContents());
                diaryEntity.setContents(diaryReqDto.getContents());

                Optional<DiaryCheckListEntity> existingCheckListOpt = diaryCheckListRepository.findByDiary(diaryEntity);
                if (existingCheckListOpt.isPresent()) {
                    DiaryCheckListEntity diaryCheckListEntity = existingCheckListOpt.get();
                    diaryCheckListEntity.setEvents(diaryReqDto.getEvents());
                    diaryCheckListRepository.save(diaryCheckListEntity);
                } else {
                    DiaryCheckListEntity diaryCheckListEntity = new DiaryCheckListEntity();
                    diaryCheckListEntity.setEvents(diaryReqDto.getEvents());
                    diaryCheckListEntity.setDiary(diaryEntity);
                    diaryCheckListRepository.save(diaryCheckListEntity);
                }
            } else {
                diaryEntity = DiaryEntity.builder()
                        .email(diaryReqDto.getEmail())
                        .anniversary(anniversary)
                        .dateContents(diaryReqDto.getDateContents())
                        .contents(diaryReqDto.getContents())
                        .couple(couple)
                        .build();
                diaryEntity = diaryRepository.save(diaryEntity);

                DiaryCheckListEntity diaryCheckListEntity = new DiaryCheckListEntity();
                diaryCheckListEntity.setEvents(diaryReqDto.getEvents());
                diaryCheckListEntity.setDiary(diaryEntity);
                diaryCheckListRepository.save(diaryCheckListEntity);
            }

            return diaryEntity;
        } catch (Exception e) {
            log.error("Failed to save diary: {}", e.getMessage());
            throw new RuntimeException("Failed to save diary", e);
        }
    }

    //다이어리 조회 불러오기
    @Transactional
    public List<DiaryResDto> getDiariesByEmail(String email) {
        CoupleEntity couple = coupleRepository.findByFirstEmailOrSecondEmail(email, email)
                .orElseThrow(() -> new RuntimeException("Couple not found"));

        List<DiaryEntity> diaries = diaryRepository.findByCouple(couple);

        return diaries.stream().map(diary -> {
            List<DiaryCheckListEntity.Event> events = diaryCheckListRepository.findByDiary(diary)
                    .map(DiaryCheckListEntity::getEvents).orElse(null);

            return DiaryResDto.builder()
                    .email(diary.getEmail())
                    .anniversary(diary.getAnniversary())
                    .dateContents(diary.getDateContents())
                    .contents(diary.getContents())
                    .events(events)
                    .build();
        }).collect(Collectors.toList());
    }

    //다이어리 삭제
    @Transactional
    public void deleteDiaryByEmailAndDate(String email, LocalDate date) {
        CoupleEntity couple = coupleRepository.findByFirstEmailOrSecondEmail(email, email)
                .orElseThrow(() -> new RuntimeException("Couple not found"));

        DiaryEntity diary = diaryRepository.findByCoupleAndAnniversary(couple, date)
                .orElseThrow(() -> new RuntimeException("Diary not found"));

        diaryCheckListRepository.deleteByDiary(diary);
        diaryRepository.delete(diary);
    }
}