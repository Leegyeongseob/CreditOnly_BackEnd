package com.kh.CreditOnly_BackEnd.repository;

import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    Optional<MemberEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    List<MemberEntity> findEmailByNameAndRegistrationNumber(String name, String registrationNumber);
    Optional<MemberEntity> findPwdByEmailAndNameAndRegistrationNumber(String email, String name, String registrationNumber);
}
