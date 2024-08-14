package com.kh.CreditOnly_BackEnd.service;

import com.kh.CreditOnly_BackEnd.dto.reqdto.AnnouncementReqDto;
import com.kh.CreditOnly_BackEnd.dto.resdto.AnnouncementResDto;
import com.kh.CreditOnly_BackEnd.entity.AnCheckEntity;
import com.kh.CreditOnly_BackEnd.entity.AnnouncementEntity;
import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import com.kh.CreditOnly_BackEnd.repository.AnCheckRepository;
import com.kh.CreditOnly_BackEnd.repository.AnnouncementRepository;
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

    // 특정 이메일에 해당하는 알림 불러오기
    public List<AnnouncementResDto> getNotificationsByEmail(String email) {
        List<AnCheckEntity> anChecks = anCheckRepository.findByMember_EmailAndIsReadFalse(email);
        return anChecks.stream()
                .map(anCheck -> {
                    AnnouncementEntity announcement = anCheck.getAnnouncement();
                    return new AnnouncementResDto(
                            announcement.getId(),
                            announcement.getTitle(),
                            announcement.getClassTitle(),
                            announcement.getContents(),
                            announcement.getCreatedDate().format(DATE_TIME_FORMATTER)
                    );
                })
                .collect(Collectors.toList());
    }

    public void markAsRead(Long id, String email) {
        // 이메일로 MemberEntity 찾기
        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // MemberEntity와 AnnouncementEntity의 ID로 AnCheckEntity 찾기
        AnCheckEntity anCheck = anCheckRepository.findByAnnouncement_IdAndMember_Id(id, member.getId())
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        // 읽음 처리
        anCheck.setRead(true);
        anCheckRepository.save(anCheck);
    }
}