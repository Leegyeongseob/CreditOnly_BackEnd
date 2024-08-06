package com.kh.Palette_BackEnd.service;

import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IsOwnerService {
    @Autowired
private CoupleRepository coupleRepository;

    public boolean isEmailInCouple(String coupleName, String email) {
        // Find the couple by coupleName
        Optional<CoupleEntity> coupleOpt = coupleRepository.findByCoupleName(coupleName);
        if (coupleOpt.isPresent()) {
            CoupleEntity couple = coupleOpt.get();
            // Check if email matches either firstEmail or secondEmail in the couple
            return email.equals(couple.getFirstEmail()) || email.equals(couple.getSecondEmail());
        }
        return false; // If couple with given coupleName doesn't exist
    }
}