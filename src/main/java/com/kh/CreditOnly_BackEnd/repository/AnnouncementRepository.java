package com.kh.CreditOnly_BackEnd.repository;

import com.kh.CreditOnly_BackEnd.entity.AnnouncementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, Long> {
    List<AnnouncementEntity> findByClassTitle(String classTitle);

    Optional<List<AnnouncementEntity>> findByTitleOrContentsContains(String data, String data1);
}
