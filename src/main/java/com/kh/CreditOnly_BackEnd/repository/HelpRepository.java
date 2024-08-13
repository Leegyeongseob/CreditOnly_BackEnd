package com.kh.CreditOnly_BackEnd.repository;

import com.kh.CreditOnly_BackEnd.entity.HelpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HelpRepository extends JpaRepository<HelpEntity, Long> {
    Optional<List<HelpEntity>> findByEmail(String email);

}
