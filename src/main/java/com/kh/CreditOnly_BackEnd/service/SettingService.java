package com.kh.CreditOnly_BackEnd.service;

import com.kh.CreditOnly_BackEnd.dto.resdto.SettingResDto;
import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import com.kh.CreditOnly_BackEnd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SettingService {
    private final MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    //회원 정보 가져오는 비동기 함수
    public SettingResDto getUserInfo(String email) {
        Optional<MemberEntity> memberEntityOpt = memberRepository.findByEmail(email);

        if (memberEntityOpt.isPresent()) {
            MemberEntity memberEntity = memberEntityOpt.get();
            // MemberEntity를 SettingResDto로 변환
            return SettingResDto.fromMemberEntity(memberEntity);
        } else {
            throw new RuntimeException("Member not found");
        }
    }
}
