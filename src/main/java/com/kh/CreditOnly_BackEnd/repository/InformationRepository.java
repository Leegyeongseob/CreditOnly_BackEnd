package com.kh.CreditOnly_BackEnd.repository;

import com.kh.CreditOnly_BackEnd.entity.InformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// InformationEntity를 위한 레포지토리 인터페이스
@Repository
public interface InformationRepository extends JpaRepository<InformationEntity, Long> {
    // 카테고리별로 정보를 조회하는 메서드 (페이지네이션 제거)
    List<InformationEntity> findByCategory(String category);

    // 전체 정보를 조회하는 메서드 (페이지네이션 제거)
    List<InformationEntity> findAll();

    Optional<List<InformationEntity>> findByTitleLikeOrContentLike(String data, String data1);
}
