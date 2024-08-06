package com.kh.Palette_BackEnd.service;


import com.kh.Palette_BackEnd.constant.Sex;
import com.kh.Palette_BackEnd.entity.BoardEntity;
import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.MemberEntity;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import com.kh.Palette_BackEnd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MainService {
    private final CoupleRepository coupleRepository;
    private final MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;
    // 커플이름으로 닉네임 불러오기
    public List<String> searchNickName(String email, String coupleName) {
        List<String> nickNames = new ArrayList<>();

        Optional<CoupleEntity> coupleEntityOpt = coupleRepository.findByCoupleName(coupleName);

        if (coupleEntityOpt.isPresent()) {
            CoupleEntity coupleEntity = coupleEntityOpt.get();
            String firstEmail = coupleEntity.getFirstEmail();
            String secondEmail = coupleEntity.getSecondEmail();

            // firstEmail만 값이 존재하는 경우 처리
            if (firstEmail != null &&secondEmail ==null && firstEmail.equals(email)) {
                Optional<MemberEntity> memberFirstEntityOpt = memberRepository.findByEmail(firstEmail);
                memberFirstEntityOpt.ifPresent(memberEntity -> nickNames.add(memberEntity.getNickName()));
            }

            // secondEmail만 값이 존재하는 경우 처리
            if (secondEmail != null && firstEmail == null && secondEmail.equals(email)) {
                Optional<MemberEntity> memberSecondEntityOpt = memberRepository.findByEmail(secondEmail);
                memberSecondEntityOpt.ifPresent(memberEntity -> nickNames.add(memberEntity.getNickName()));
            }

            // 값이 둘다 존재하고 firstEmail이 email과 같은 경우
            if (firstEmail != null && secondEmail != null && firstEmail.equals(email)){
                Optional<MemberEntity> memberFirstEntityOpt = memberRepository.findByEmail(firstEmail);
                Optional<MemberEntity> memberSecondEntityOpt = memberRepository.findByEmail(secondEmail);
                memberFirstEntityOpt.ifPresent(memberEntity -> nickNames.add(memberEntity.getNickName()));
                memberSecondEntityOpt.ifPresent(memberEntity -> nickNames.add(memberEntity.getNickName()));
            }
            // 값이 둘다 존재하고 secondEmail이 email과 같은 경우
            if (firstEmail != null && secondEmail != null && secondEmail.equals(email)){
                Optional<MemberEntity> memberFirstEntityOpt = memberRepository.findByEmail(firstEmail);
                Optional<MemberEntity> memberSecondEntityOpt = memberRepository.findByEmail(secondEmail);
                memberSecondEntityOpt.ifPresent(memberEntity -> nickNames.add(memberEntity.getNickName()));
                memberFirstEntityOpt.ifPresent(memberEntity -> nickNames.add(memberEntity.getNickName()));

            }
            // 일치하는 email이 존재 하지 않을 경우
            if (!firstEmail.equals(email)&&!secondEmail.equals(email)){
                Optional<MemberEntity> memberFirstEntityOpt = memberRepository.findByEmail(firstEmail);
                Optional<MemberEntity> memberSecondEntityOpt = memberRepository.findByEmail(secondEmail);
                memberFirstEntityOpt.ifPresent(memberEntity -> nickNames.add(memberEntity.getNickName()));
                memberSecondEntityOpt.ifPresent(memberEntity -> nickNames.add(memberEntity.getNickName()));
            }

        }
        return nickNames;
    }
    // 커플이름으로 D-day 찾기
    public String searchDday(String coupleName) {
        Optional<CoupleEntity> coupleEntityOpt = coupleRepository.findByCoupleName(coupleName);
        if (coupleEntityOpt.isPresent()) {
            CoupleEntity coupleEntity = coupleEntityOpt.get();
            String datingDay = coupleEntity.getDDay();
            if (datingDay != null) {
                return datingDay;
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException("해당 커플 이름으로 정보를 찾을 수 없습니다: " + coupleName);
        }
    }
    // D-day 저장
    public boolean saveDday(String coupleName,String datingDay){
        Optional<CoupleEntity> coupleEntityOpt = coupleRepository.findByCoupleName(coupleName);
        if(coupleEntityOpt.isPresent()){
            CoupleEntity coupleEntity = coupleEntityOpt.get();
            coupleEntity.setDDay(datingDay);
            coupleRepository.saveAndFlush(coupleEntity);
            em.clear();
            return true;
        }
        else{
            return false;
        }
    }
    //커플 검색에 맞는 리스트 값.
    public List<String> visitCoupleNameSearchList(String coupleName){
        List<String> resultList = new ArrayList<>();
        try {
            Optional<List<CoupleEntity>> optionalCoupleEntities = coupleRepository.findByCoupleNameContaining(coupleName);

            optionalCoupleEntities.ifPresent(entities -> {
                for (CoupleEntity entity : entities) {
                    resultList.add(entity.getCoupleName());
                }
            });

            if (optionalCoupleEntities.isEmpty() || resultList.isEmpty()) {
                throw new RuntimeException("검색된 커플 이름이 없습니다.");
            }
        } catch (Exception e) {
            // 필요에 따라 예외 처리를 수행합니다
            e.printStackTrace();
            // 애플리케이션의 오류 처리 전략에 따라 사용자 정의 예외를 throw하거나 처리합니다
        }

        return resultList;
    }
    //본인 성별 가져오는 비동기 함수
    public Sex mySexSearch(String email){
        Optional<MemberEntity> memberEntityOpt = memberRepository.findByEmail(email);
        if(memberEntityOpt.isPresent()){
            MemberEntity memberEntity = memberEntityOpt.get();
            return memberEntity.getSex();
        }
        else{
            throw new RuntimeException("Member not found");
        }
    }
}
