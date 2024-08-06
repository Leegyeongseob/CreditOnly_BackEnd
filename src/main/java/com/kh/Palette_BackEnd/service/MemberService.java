package com.kh.Palette_BackEnd.service;


import com.kh.Palette_BackEnd.dto.reqdto.MemberUpdateReqDto;
import com.kh.Palette_BackEnd.dto.resdto.MemberResDto;
import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.MemberEntity;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import com.kh.Palette_BackEnd.repository.MemberRepository;
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
    private final CoupleRepository coupleRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // PasswordEncoder 주입

    @PersistenceContext
    EntityManager em;
    public MemberResDto memberAxios(String email){
        Optional<MemberEntity> memberEntity = memberRepository.findByEmail(email);
        MemberEntity member = memberEntity.get();
        return MemberResDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .nickName(member.getNickName())
                .coupleName(member.getCouple().getCoupleName())
                .build();
    }
    //회원 정보 수정 (커플 테이블도 수정해야함.)
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
                member.setNickName(memberUpdateReqDto.getNickName());
                member.getCouple().setCoupleName(memberUpdateReqDto.getCoupleName());
                Optional<CoupleEntity> coupleEntity1 = coupleRepository.findByFirstEmail(memberUpdateReqDto.getEmail());
                Optional<CoupleEntity> coupleEntity2 = coupleRepository.findBySecondEmail(memberUpdateReqDto.getEmail());
                if(coupleEntity1.isPresent()){
                    coupleEntity1.get().setFirstEmail(memberUpdateReqDto.getUpdateEmail());
                    coupleEntity1.get().setCoupleName(memberUpdateReqDto.getCoupleName());
                    coupleRepository.saveAndFlush(coupleEntity1.get());
                    em.clear();
                } else if (coupleEntity2.isPresent()) {
                    coupleEntity2.get().setSecondEmail(memberUpdateReqDto.getUpdateEmail());
                    coupleEntity2.get().setCoupleName(memberUpdateReqDto.getCoupleName());
                    coupleRepository.saveAndFlush(coupleEntity2.get());
                    em.clear();
                }
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

            // 커플 정보 조회
            Optional<CoupleEntity> coupleEntity1 = coupleRepository.findByFirstEmail(email);
            Optional<CoupleEntity> coupleEntity2 = coupleRepository.findBySecondEmail(email);

            // 커플 정보 처리
            if (coupleEntity1.isPresent()) {
                CoupleEntity couple = coupleEntity1.get();
                if (couple.getSecondEmail() == null || couple.getSecondEmail().isEmpty()) {
                    coupleRepository.delete(couple);
                } else {
                    couple.setFirstEmail(null);
                    coupleRepository.saveAndFlush(couple);
                    em.clear();
                }
            } else if (coupleEntity2.isPresent()) {
                CoupleEntity couple = coupleEntity2.get();
                if (couple.getFirstEmail() == null || couple.getFirstEmail().isEmpty()) {
                    coupleRepository.delete(couple);
                } else {
                    couple.setSecondEmail(null);
                    coupleRepository.saveAndFlush(couple);
                    em.clear();
                }
            }

            return "회원 정보 및 관련 커플 정보가 삭제되었습니다.";
        } catch (Exception e) {
            return "회원 정보 삭제 중 오류가 발생했습니다.: " + e.getMessage();
        }
    }
    // 커플이름 search
    public String renderCoupleNameSearch(String email){
        Optional<CoupleEntity> coupleEntityOpt = coupleRepository.findByFirstEmailOrSecondEmail(email,email);
        if(coupleEntityOpt.isPresent()){
            log.info(coupleEntityOpt.get().getCoupleName());
            return coupleEntityOpt.get().getCoupleName();
        }
        else {
            // 커플을 찾지 못한 경우 예외를 던집니다.
            throw new EntityNotFoundException("해당 이메일로 커플을 찾을 수 없습니다: " + email);
        }
    }
    // 커플 이름으로 솔로인지 커플인지 확인
    public boolean isCoupleTrue(String coupleName) {
        Optional<CoupleEntity> coupleEntity = coupleRepository.findByCoupleName(coupleName);

        if (coupleEntity.isPresent()) {
            CoupleEntity entity = coupleEntity.get();
            String firstEmail = entity.getFirstEmail();
            String secondEmail = entity.getSecondEmail();
            return firstEmail != null && !firstEmail.isEmpty() && secondEmail != null && !secondEmail.isEmpty();
        } else {
            throw new EntityNotFoundException("해당 커플 이름으로 정보를 찾을 수 없습니다: " + coupleName);
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
    // 커플 프로필 url을 가져오는 Axios
    public List<String> coupleProfileUrl( String coupleName, String email) {
        List<String> list = new ArrayList<>();

        // 커플 이름으로 커플 검색
        Optional<CoupleEntity> coupleEntityOpt = coupleRepository.findByCoupleName(coupleName);
        if (coupleEntityOpt.isPresent()) {
            CoupleEntity coupleEntity = coupleEntityOpt.get();
            String firstEmail = coupleEntity.getFirstEmail();
            String secondEmail = coupleEntity.getSecondEmail();

            // firstEmail만 값이 존재하는 경우 처리
            if (firstEmail != null &&secondEmail ==null && firstEmail.equals(email)) {
                Optional<MemberEntity> memberFirstEntityOpt = memberRepository.findByEmail(firstEmail);
                memberFirstEntityOpt.ifPresent(memberEntity -> list.add(memberEntity.getProfileImgUrl()));
            }

            // secondEmail만 값이 존재하는 경우 처리
            if (secondEmail != null && firstEmail == null && secondEmail.equals(email)) {
                Optional<MemberEntity> memberSecondEntityOpt = memberRepository.findByEmail(secondEmail);
                memberSecondEntityOpt.ifPresent(memberEntity -> list.add(memberEntity.getProfileImgUrl()));
            }

            // 값이 둘다 존재하고 firstEmail이 email과 같은 경우
            if (firstEmail != null && secondEmail != null && firstEmail.equals(email)){
                Optional<MemberEntity> memberFirstEntityOpt = memberRepository.findByEmail(firstEmail);
                Optional<MemberEntity> memberSecondEntityOpt = memberRepository.findByEmail(secondEmail);
                memberFirstEntityOpt.ifPresent(memberEntity -> list.add(memberEntity.getProfileImgUrl()));
                memberSecondEntityOpt.ifPresent(memberEntity -> list.add(memberEntity.getProfileImgUrl()));
            }
            // 값이 둘다 존재하고 secondEmail이 email과 같은 경우
            if (firstEmail != null && secondEmail != null && secondEmail.equals(email)){
                Optional<MemberEntity> memberFirstEntityOpt = memberRepository.findByEmail(firstEmail);
                Optional<MemberEntity> memberSecondEntityOpt = memberRepository.findByEmail(secondEmail);
                memberSecondEntityOpt.ifPresent(memberEntity -> list.add(memberEntity.getProfileImgUrl()));
                memberFirstEntityOpt.ifPresent(memberEntity -> list.add(memberEntity.getProfileImgUrl()));

            }
            // 주어진 이메일이 커플의 firstEmail 또는 secondEmail과 일치하지 않는 경우
            if (!email.equals(firstEmail) && !email.equals(secondEmail)) {
                // firstEmail에 해당하는 멤버의 프로필 이미지 URL 가져오기
                Optional<MemberEntity> memberEntity1 = memberRepository.findByEmail(firstEmail);
                memberEntity1.ifPresent(memberEntity -> list.add(memberEntity.getProfileImgUrl()));

                // secondEmail에 해당하는 멤버의 프로필 이미지 URL 가져오기
                Optional<MemberEntity> memberEntity2 = memberRepository.findByEmail(secondEmail);
                memberEntity2.ifPresent(memberEntity -> list.add(memberEntity.getProfileImgUrl()));
            }
            return list;
        }
        // 커플 정보가 없을 경우 빈 리스트 반환
        return list;
    }

    // 앨범 결제시 이메일로 이름 가져오기
    public MemberResDto albumCustomer(String email) {
        Optional<MemberEntity> memberEntityOpt = memberRepository.findByEmail(email);
        if (memberEntityOpt.isPresent()) {
            MemberEntity memberEntity = memberEntityOpt.get();
            return MemberResDto.fromMemberEntity(memberEntity);
        } else {
            throw new IllegalArgumentException("해당 이메일로 사용자를 찾을 수 없습니다: " + email);
        }
    }
    public String firstEmailGet(String coupleName){
        Optional<CoupleEntity> coupleEntityOpt = coupleRepository.findByCoupleName(coupleName);
        // 커플 테이블에 존재하면
        if(coupleEntityOpt.isPresent()){
            // 첫번째 이메일에 있으면
            if(coupleEntityOpt.get().getFirstEmail() !=null) {

                return coupleEntityOpt.get().getFirstEmail();
            }
            // 두번째 이메일에 있으면
            else{
                return coupleEntityOpt.get().getSecondEmail();
            }
        }
        // 존재하지 않으면
        else{
            return "Db에 존재하지 않습니다.";
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
