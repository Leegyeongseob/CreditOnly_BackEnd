package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.MemberEntity;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;


@SpringBootTest
@Slf4j
@TestPropertySource(locations = "classpath:application_test.properties")
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;
    public MemberEntity createMemberInfo(){
        return MemberEntity.builder().email("can34879@naver.com")
                .pwd("1q2w3e4r!@")
                .name("이경섭")
                .nickName("멘도롱또돗")
                .registrationNumber("9403101")
                .build();
    }
    @Test
    @DisplayName("회원가입테스트를 위한 데이터 입력")
    public void InsertMemberInfo(){
        MemberEntity user = createMemberInfo();
        memberRepository.saveAndFlush(user);
        em.clear();
    }
    @Test
    @DisplayName("이메일 중복 테스트")
    public void emailIsExist(){
        MemberEntity user = createMemberInfo();
        boolean isTrue = memberRepository.existsByEmail(user.getEmail());
        log.info(String.valueOf(isTrue));
    }
}