package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    Optional<MemberEntity> findByEmail(String email);
    Optional<String> findNickNameByEmail(String email);
    boolean existsByEmail(String email);

    Optional<MemberEntity> findEmailByNameAndRegistrationNumber(String name, String registrationNumber);

    Optional<MemberEntity> findPwdByEmailAndNameAndRegistrationNumber(String email, String name, String registrationNumber);
}
