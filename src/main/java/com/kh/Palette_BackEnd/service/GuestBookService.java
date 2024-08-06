package com.kh.Palette_BackEnd.service;

import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.GuestBookEntity;
import com.kh.Palette_BackEnd.entity.MemberEntity;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import com.kh.Palette_BackEnd.repository.GuestBookRepository;
import com.kh.Palette_BackEnd.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class GuestBookService {

    @Autowired
    private GuestBookRepository guestBookRepository;

    @Autowired
    private CoupleRepository coupleRepository;

    @Autowired
    private MemberRepository memberRepository;

    // 모든 방명록 조회
    public List<GuestBookEntity> getAllGuestBooks() {
        return guestBookRepository.findAll();
    }


    // 특정 커플의 방명록 항목들을 가져오는 메서드
    public List<GuestBookEntity> getGuestBookEntries(String coupleName) {
        CoupleEntity couple = coupleRepository.findByCoupleName(coupleName)
                .orElseThrow(() -> new RuntimeException("Couple not found"));
        return guestBookRepository.findByCouple(couple);
    }

    // 방명록 항목을 추가하는 메서드
    public GuestBookEntity addGuestBookEntry(String coupleName, String memberEmail,String contents) {
        CoupleEntity couple = coupleRepository.findByCoupleName(coupleName)
                .orElseThrow(() -> new RuntimeException("Couple not found"));
        MemberEntity member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        GuestBookEntity entry = GuestBookEntity.builder()
                .couple(couple)
                .member(member)
                .contents(contents)
                .build();

        return guestBookRepository.save(entry);
    }

    // 방명록 항목을 수정하는 메서드
    public void updateGuestBookEntry(Long entryId,String contents , String requesterEmail) {
        GuestBookEntity entry = guestBookRepository.findById(entryId)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        CoupleEntity couple = entry.getCouple();
        if (!entry.getMember().getEmail().equals(requesterEmail) &&
                !couple.getFirstEmail().equals(requesterEmail) &&
                !couple.getSecondEmail().equals(requesterEmail)) {
            throw new RuntimeException("Only the author or couple members can update this entry");
        }


        entry.setContents(contents);
        guestBookRepository.save(entry);
    }

    // 방명록 항목을 삭제하는 메서드
    public void deleteGuestBookEntry(Long entryId, String requesterEmail) {
        GuestBookEntity entry = guestBookRepository.findById(entryId)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        CoupleEntity couple = entry.getCouple();
        if (!entry.getMember().getEmail().equals(requesterEmail) &&
                !couple.getFirstEmail().equals(requesterEmail) &&
                !couple.getSecondEmail().equals(requesterEmail)) {
            throw new RuntimeException("Only the author or couple members can delete this entry");
        }

        guestBookRepository.delete(entry);
    }
    // 이메일로 이미지 url 가져오는 메서드
    public String getProfileImgUrlByEmail(String email) {
        Optional<MemberEntity> member = memberRepository.findByEmail(email);
        return member.map(MemberEntity::getProfileImgUrl).orElse(null);
    }
    public String searchImgUrl(String email){
        Optional<CoupleEntity> coupleEntityOpt = coupleRepository.findByFirstEmailOrSecondEmail(email,email);
        if(coupleEntityOpt.isPresent()){
            CoupleEntity coupleEntity = coupleEntityOpt.get();
            String firstEmail = coupleEntity.getFirstEmail();
            String secondEmail = coupleEntity.getSecondEmail();
            if(firstEmail.equals(email)){
                return "true"; // firstEmail과 email이 같으면 true 반환
            } else if (secondEmail.equals(email)) {
                return "false"; // secondEmail과 email이 같으면 false 반환
            } else {
                // 예외 처리: firstEmail과 secondEmail 둘 다 email과 다른 경우 (이 경우는 발생하지 않아야 함)
                throw new IllegalStateException("커플 테이블에 이메일이 잘못 저장되어 있습니다.");
            }
        } else {
            // 커플을 찾지 못한 경우 예외를 던집니다.
            throw new EntityNotFoundException("해당 이메일로 커플을 찾을 수 없습니다: " + email);
        }
    }
}
