package com.kh.CreditOnly_BackEnd.service;

import com.kh.CreditOnly_BackEnd.dto.reqdto.AnnouncementReqDto;
import com.kh.CreditOnly_BackEnd.dto.reqdto.HelpReqDto;
import com.kh.CreditOnly_BackEnd.dto.resdto.AnnouncementResDto;
import com.kh.CreditOnly_BackEnd.dto.resdto.HelpResDto;
import com.kh.CreditOnly_BackEnd.entity.AnCheckEntity;
import com.kh.CreditOnly_BackEnd.entity.AnnouncementEntity;
import com.kh.CreditOnly_BackEnd.entity.HelpEntity;
import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import com.kh.CreditOnly_BackEnd.repository.AnCheckRepository;
import com.kh.CreditOnly_BackEnd.repository.AnnouncementRepository;
import com.kh.CreditOnly_BackEnd.repository.HelpRepository;
import com.kh.CreditOnly_BackEnd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AnnouncementService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private AnCheckRepository anCheckRepository;
    @Autowired
    private MemberRepository memberRepository;

    public void createBoard(AnnouncementReqDto announcementReqDto) {
        AnnouncementEntity announcementEntity = AnnouncementEntity.builder()
                .email(announcementReqDto.getEmail())
                .classTitle(announcementReqDto.getClassTitle())
                .title(announcementReqDto.getTitle())
                .contents(announcementReqDto.getContents())
                .build();

        createAnnouncement(announcementEntity);
    }

    // 게시글 작성 서비스 로직
    public void createAnnouncement(AnnouncementEntity announcement) {
        // 게시글 저장
        announcementRepository.save(announcement);

        // 모든 사용자 조회
        List<MemberEntity> allUsers = memberRepository.findAll();
        // 각 사용자에 대해 AnCheckEntity 생성
        for (MemberEntity member : allUsers) {
            AnCheckEntity anCheck = AnCheckEntity.builder()
                    .announcement(announcement)
                    .member(member)
                    .isRead(false)
                    .build();
            anCheckRepository.save(anCheck);
        }
    }

    //게시글 불러오기
    public List<AnnouncementResDto> getBoardsByClassTitle(String classTitle) {
        return announcementRepository.findByClassTitle(classTitle).stream()
                .map(entity -> new AnnouncementResDto(
                        entity.getId(),
                        entity.getTitle(),
                        entity.getClassTitle(),
                        entity.getContents(),
                        entity.getCreatedDate().format(DATE_TIME_FORMATTER)))
                .collect(Collectors.toList());
    }
}
