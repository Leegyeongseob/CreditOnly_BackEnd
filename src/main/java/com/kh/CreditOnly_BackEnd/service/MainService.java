package com.kh.CreditOnly_BackEnd.service;


import com.kh.CreditOnly_BackEnd.constant.Sex;
import com.kh.CreditOnly_BackEnd.entity.HelpEntity;
import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import com.kh.CreditOnly_BackEnd.repository.HelpRepository;
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
    //
    public List<Map<String, Object>> dataSearch(String email,String data){
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            // 1. 이메일로 필터링된 결과를 가져옵니다.
            List<HelpEntity> helpEntities = helpRepository.findByEmail(email);

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
