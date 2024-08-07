package com.kh.CreditOnly_BackEnd.service;


import com.kh.CreditOnly_BackEnd.dto.reqdto.MemberUpdateReqDto;
import com.kh.CreditOnly_BackEnd.dto.resdto.MemberResDto;
import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import com.kh.CreditOnly_BackEnd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // PasswordEncoder 주입

    @PersistenceContext
    EntityManager em;
    // 사용자 정보 가져오기
    public MemberResDto memberAxios(String email){
        Optional<MemberEntity> memberEntity = memberRepository.findByEmail(email);
        MemberEntity member = memberEntity.get();
        return MemberResDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
    //회원 정보 수정
    public String memberModify(MemberUpdateReqDto memberUpdateReqDto) {
        try {
            //수정전 이메일로 정보 조회
            Optional<MemberEntity> memberEntity = memberRepository.findByEmail(memberUpdateReqDto.getEmail());
            if(memberEntity.isPresent())
            {
                MemberEntity member = memberEntity.get();
                member.setEmail(memberUpdateReqDto.getUpdateEmail());
                member.setPwd(passwordEncoder.encode(memberUpdateReqDto.getPwd()));
                member.setName(memberUpdateReqDto.getName());
                memberRepository.saveAndFlush(member);
                em.clear();
            }
            return "Success";
        } catch (DataAccessException e) {
            // 데이터 접근 예외 처리 (예: 데이터베이스 접근 오류)
            return "회원 정보 수정 실패: 데이터베이스 접근 중 오류가 발생했습니다.";
        } catch (Exception e) {
            // 그 외의 예외 처리
            return "회원 정보 수정 중 오류가 발생했습니다.";
        }
    }
    // 회원정보삭제 (커플테이블도 둘 다 없을 때 삭제해야 함.)
    public String memberDelete(String email) {
        try {
            // 회원 정보 조회
            MemberEntity memberEntity = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
            // 회원 정보 삭제
            memberRepository.delete(memberEntity);

            return "회원 정보 및 관련 커플 정보가 삭제되었습니다.";
        } catch (Exception e) {
            return "회원 정보 삭제 중 오류가 발생했습니다.: " + e.getMessage();
        }
    }
    //프로필url 저장 Axios
    public boolean profileUrlSave(String email,String url){
        Optional<MemberEntity> memberEntityOpt = memberRepository.findByEmail(email);
        if(memberEntityOpt.isPresent()){
            MemberEntity member = memberEntityOpt.get();
            member.setProfileImgUrl(url);
            memberRepository.saveAndFlush(member);
            return true;
        }
        else{
            return false;
        }
    }
    // 이메일로 imgurl 가져오기
    public String searchProfileUrl(String email){
        Optional<MemberEntity> memberEntityOpt = memberRepository.findByEmail(email);
        if(memberEntityOpt.isPresent()){
            MemberEntity member = memberEntityOpt.get();
            if(member.getProfileImgUrl() != null){
                return member.getProfileImgUrl();
            }
            else{
                return "notExist";
            }
        }
        else{
            return "notExist";
        }
    }
}
