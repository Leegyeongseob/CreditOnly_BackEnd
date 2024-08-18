package com.kh.CreditOnly_BackEnd.service;


import com.kh.CreditOnly_BackEnd.constant.Authority;
import com.kh.CreditOnly_BackEnd.dto.reqdto.MemberUpdateReqDto;
import com.kh.CreditOnly_BackEnd.dto.resdto.MemberResDto;
import com.kh.CreditOnly_BackEnd.entity.AnCheckEntity;
import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import com.kh.CreditOnly_BackEnd.repository.AnCheckRepository;
import com.kh.CreditOnly_BackEnd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // PasswordEncoder 주입
    private final AnCheckRepository anCheckRepository;
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
    public String memberDelete(String email) {
        try {
            // 회원 정보 조회
            MemberEntity memberEntity = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
            // 회원과 관련된 모든 AnCheckEntity 삭제
            List<AnCheckEntity> anCheckEntities = anCheckRepository.findAllByMember(memberEntity);
            anCheckRepository.deleteAll(anCheckEntities);
            //회원 정보 삭제
            memberRepository.delete(memberEntity);
            return "회원 정보가 삭제되었습니다.";
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
    // 토큰으로 이메일 불러오기
    public String getEmail(String number) {
        MemberEntity member = memberRepository.findById(Long.parseLong(number)).orElseThrow(()-> new RuntimeException("해당 회원이 존재하지 않습니다."));
        return member.getEmail();
    }

    // 관리자 이메일 목록 가져오기
    public List<String> getAdminEmails() {
        // Authority.ROLE_ADMIN을 기준으로 이메일 목록을 가져옵니다.
        return memberRepository.findByAuthority(Authority.ROLE_ADMIN).stream()
                .map(MemberEntity::getEmail)
                .collect(Collectors.toList());
    }

    //chatting과 관련된 service 부분

    public MemberEntity getUserByUsername(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public MemberEntity createMember(MemberEntity member) {
        // 여기에 사용자 생성 로직 추가 (비밀번호 암호화 등)
        return memberRepository.save(member);
    }

    public MemberEntity updateMember(MemberEntity member) {
        // 여기에 사용자 업데이트 로직 추가
        return memberRepository.save(member);
    }

    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    // 필요에 따라 추가 메서드 구현
}
