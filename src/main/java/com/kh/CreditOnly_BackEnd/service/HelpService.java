package com.kh.CreditOnly_BackEnd.service;

import com.kh.CreditOnly_BackEnd.dto.reqdto.HelpReqDto;
import com.kh.CreditOnly_BackEnd.dto.resdto.HelpResDto;
import com.kh.CreditOnly_BackEnd.entity.HelpEntity;
import com.kh.CreditOnly_BackEnd.repository.HelpRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class HelpService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private HelpRepository helpRepository;

    public void createHelpRequest(HelpReqDto helpReqDto) {
        HelpEntity helpEntity = HelpEntity.builder()
                .email(helpReqDto.getEmail())
                .title(helpReqDto.getTitle())
                .contents(helpReqDto.getContents())
                .build();

        helpRepository.save(helpEntity);
    }
    // 이메일로 Help 요청을 조회하고, 결과를 HelpResDto로 변환
    public List<HelpResDto> getHelpRequestsByEmail(String email) {
        // 이메일로 Help 요청을 조회합니다.
        Optional<List<HelpEntity>> optionalHelpEntities = helpRepository.findByEmail(email);

        // Optional을 처리하여 리스트를 변환합니다.
        return optionalHelpEntities
                .orElseGet(List::of) // Optional이 비어 있을 경우 빈 리스트를 반환합니다.
                .stream()
                .map(entity -> new HelpResDto(
                        entity.getId(), // ID를 추가
                        entity.getTitle(),
                        entity.getContents(),
                        entity.getCreatedDate().format(DATE_TIME_FORMATTER))) // LocalDateTime을 String으로 변환
                .collect(Collectors.toList());
    }
}
