package com.kh.CreditOnly_BackEnd.repository;


import com.kh.CreditOnly_BackEnd.entity.ConsumerSurveyIndexEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsumerSurveyIndexRepository extends JpaRepository<ConsumerSurveyIndexEntity,Long> {

    Optional<ConsumerSurveyIndexEntity> findByWordContainingOrContentContaining(String message, String message1);

}
