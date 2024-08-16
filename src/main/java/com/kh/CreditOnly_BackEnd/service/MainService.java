package com.kh.CreditOnly_BackEnd.service;


import com.kh.CreditOnly_BackEnd.constant.Sex;
import com.kh.CreditOnly_BackEnd.entity.AnnouncementEntity;
import com.kh.CreditOnly_BackEnd.entity.HelpEntity;
import com.kh.CreditOnly_BackEnd.entity.InformationEntity;
import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import com.kh.CreditOnly_BackEnd.repository.AnnouncementRepository;
import com.kh.CreditOnly_BackEnd.repository.HelpRepository;
import com.kh.CreditOnly_BackEnd.repository.InformationRepository;
import com.kh.CreditOnly_BackEnd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MainService {
    private final MemberRepository memberRepository;
    private final HelpRepository helpRepository;
    private final AnnouncementRepository announcementRepository;
    private final InformationRepository informationRepository;
    @PersistenceContext
    EntityManager em;

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
    // 검색창에서 전체 검색
    public List<Map<String, Object>> dataSearch(String email, String data) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            // 1. 이메일로 필터링된 결과를 가져옵니다.
            Optional<List<HelpEntity>> helpEntitiesOpt = helpRepository.findByEmail(email);
            if (helpEntitiesOpt.isPresent()) {
                List<HelpEntity> helpEntities = helpEntitiesOpt.get();
                // 2. 가져온 결과에서 데이터로 추가 필터링합니다.
                for (HelpEntity helpEntity : helpEntities) {
                    if (helpEntity.getTitle().contains(data) || helpEntity.getContents().contains(data)) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", helpEntity.getId());
                        map.put("page", "help");
                        map.put("title", helpEntity.getTitle());
                        map.put("contents", helpEntity.getContents());
                        resultList.add(map);
                    }
                }
            }

            // 공지사항에 관련된 데이터를 가져옵니다.
            Optional<List<AnnouncementEntity>> announcementEntitiesOpt = announcementRepository.findByTitleOrContentsContains(data, data);
            if (announcementEntitiesOpt.isPresent()) {
                List<AnnouncementEntity> announcementEntities = announcementEntitiesOpt.get();
                for (AnnouncementEntity announcementEntity : announcementEntities) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", announcementEntity.getId());
                    map.put("page", "announcement");
                    map.put("classTitle", announcementEntity.getClassTitle());
                    map.put("title", announcementEntity.getTitle());
                    map.put("contents", announcementEntity.getContents());
                    resultList.add(map);
                }
            }
            //신용정보 관련된 데이터를 가져옵니다.
            Optional<List<InformationEntity>> informationEntitiesOpt = informationRepository.findByTitleLikeOrContentLike(data, data);
            if(informationEntitiesOpt.isPresent()){
                List<InformationEntity> informationEntities = informationEntitiesOpt.get();
                for(InformationEntity informationEntity : informationEntities){
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", informationEntity.getId());
                    map.put("page", "information");
                    map.put("category", informationEntity.getCategory());
                    map.put("title", informationEntity.getTitle());
                    map.put("contents", informationEntity.getContent());
                    resultList.add(map);
                }
            }
        } catch (DataAccessException e) {
            // 데이터베이스 관련 예외 처리
            throw new RuntimeException("데이터 검색 중 문제가 발생했습니다.", e);
        } catch (Exception e) {
            // 그 외의 일반적인 예외 처리
            throw new RuntimeException("예기치 못한 오류가 발생했습니다.", e);
        }
        return resultList;
    }

}
