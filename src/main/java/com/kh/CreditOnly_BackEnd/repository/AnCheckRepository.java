package com.kh.CreditOnly_BackEnd.repository;

import com.kh.CreditOnly_BackEnd.entity.AnCheckEntity;
import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnCheckRepository extends JpaRepository<AnCheckEntity, Long> {
    // 특정 이메일과 읽지 않은 상태의 알림만 가져오기
    List<AnCheckEntity> findByMember_EmailAndIsReadFalse(String email);

    // 특정 알림 ID와 이메일로 알림 검색
    AnCheckEntity findByIdAndMember_Email(Long id, String email);
    Optional<AnCheckEntity> findByAnnouncement_IdAndMember_Id(Long announcementId, Long memberId);
    List<AnCheckEntity> findByAnnouncement_Id(Long announcementId);

    List<AnCheckEntity> findAllByMember(MemberEntity memberEntity);
}
