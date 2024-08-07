package com.kh.CreditOnly_BackEnd.service;


import com.kh.CreditOnly_BackEnd.constant.Sex;
import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import com.kh.CreditOnly_BackEnd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MainService {
    private final MemberRepository memberRepository;

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
}
