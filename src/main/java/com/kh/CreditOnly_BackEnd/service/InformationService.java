package com.kh.CreditOnly_BackEnd.service;

import com.kh.CreditOnly_BackEnd.constant.Authority;
import com.kh.CreditOnly_BackEnd.dto.reqdto.InformationReqDto;
import com.kh.CreditOnly_BackEnd.dto.resdto.InformationResDto;
import com.kh.CreditOnly_BackEnd.entity.InformationEntity;
import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import com.kh.CreditOnly_BackEnd.repository.InformationRepository;
import com.kh.CreditOnly_BackEnd.repository.MemberRepository;
import com.kh.CreditOnly_BackEnd.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InformationService {

    private final InformationRepository informationRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public InformationResDto createInformation(InformationReqDto informationReqDto) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!member.getAuthority().equals(Authority.ROLE_ADMIN)) {
            throw new RuntimeException("정보 작성 권한이 없습니다.");
        }

        InformationEntity informationEntity = InformationEntity.builder()
                .title(informationReqDto.getTitle())
                .content(informationReqDto.getContent())
                .publishedDate(LocalDateTime.now()) // 현재 시간을 publishedDate로 설정
                .imageUrl(informationReqDto.getImageUrl())
                .category(informationReqDto.getCategory())
                .build();

        InformationEntity savedInformation = informationRepository.save(informationEntity);
        return mapToResDto(savedInformation);
    }

    @Transactional
    public InformationResDto updateInformation(Long id, InformationReqDto informationReqDto) {
        InformationEntity informationEntity = informationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("정보를 찾을 수 없습니다."));

        Long memberId = SecurityUtil.getCurrentMemberId();
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!member.getAuthority().equals(Authority.ROLE_ADMIN)) {
            throw new RuntimeException("정보 수정 권한이 없습니다.");
        }

        // 정보 업데이트
        informationEntity.setTitle(informationReqDto.getTitle());
        informationEntity.setContent(informationReqDto.getContent());
        // publishedDate를 현재 시간으로 갱신
        informationEntity.setPublishedDate(LocalDateTime.now());
        informationEntity.setImageUrl(informationReqDto.getImageUrl());
        informationEntity.setCategory(informationReqDto.getCategory());

        InformationEntity updatedInformation = informationRepository.save(informationEntity);
        return mapToResDto(updatedInformation);
    }

    @Transactional
    public void deleteInformation(Long id) {
        InformationEntity informationEntity = informationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("정보를 찾을 수 없습니다."));

        Long memberId = SecurityUtil.getCurrentMemberId();
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!member.getAuthority().equals(Authority.ROLE_ADMIN)) {
            throw new RuntimeException("정보 삭제 권한이 없습니다.");
        }

        informationRepository.deleteById(id);
    }

    @Transactional
    public InformationResDto getInformationById(Long id) {
        InformationEntity informationEntity = informationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("정보를 찾을 수 없습니다."));
        return mapToResDto(informationEntity);
    }

    @Transactional
    public List<InformationResDto> getAllInformation() {
        List<InformationEntity> list = informationRepository.findAll();
        return list.stream()
                .map(this::mapToResDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<InformationResDto> getInformationByCategory(String category) {
        List<InformationEntity> list = informationRepository.findByCategory(category);
        return list.stream()
                .map(this::mapToResDto)
                .collect(Collectors.toList());
    }

    private InformationResDto mapToResDto(InformationEntity informationEntity) {
        return InformationResDto.builder()
                .id(informationEntity.getId())
                .title(informationEntity.getTitle())
                .content(informationEntity.getContent())
                .publishedDate(informationEntity.getPublishedDate())
                .imageUrl(informationEntity.getImageUrl())
                .category(informationEntity.getCategory())
                .build();
    }

//    // 데이터베이스에 초기 데이터 삽입
//    @PostConstruct
//    public void initDatabase() {
//        // 신용조회 정보모음 데이터 삽입 (10개)
//        for (int i = 1; i <= 10; i++) {
//            informationRepository.save(InformationEntity.builder()
//                    .title("신용점수 확인 방법 " + i)
//                    .content("신용점수를 쉽게 확인하는 방법에 대해 알아봅니다. (버전 " + i + ")")
//                    .publishedDate(LocalDateTime.now())
//                    .imageUrl("/images/credit-score-check-" + i + ".jpg")
//                    .category("신용조회 정보모음")
//                    .build());
//        }
//
//        // 신용조회 어플추천 데이터 삽입 (10개)
//        for (int i = 1; i <= 10; i++) {
//            informationRepository.save(InformationEntity.builder()
//                    .title("신용관리 앱 추천 " + i)
//                    .content("신용관리를 위한 최고의 앱을 소개합니다. (추천 " + i + ")")
//                    .publishedDate(LocalDateTime.now())
//                    .imageUrl("/images/app-recommendation-" + i + ".jpg")
//                    .category("신용조회 어플추천")
//                    .build());
//        }
//
//        // 신용카드와 신용정보 데이터 삽입 (10개)
//        for (int i = 1; i <= 10; i++) {
//            informationRepository.save(InformationEntity.builder()
//                    .title("신용카드 사용 팁 " + i)
//                    .content("신용카드를 현명하게 사용하는 방법에 대해 알아봅니다. (팁 " + i + ")")
//                    .publishedDate(LocalDateTime.now())
//                    .imageUrl("/images/credit-card-tips-" + i + ".jpg")
//                    .category("신용카드와 신용정보")
//                    .build());
//        }
//    }

}
