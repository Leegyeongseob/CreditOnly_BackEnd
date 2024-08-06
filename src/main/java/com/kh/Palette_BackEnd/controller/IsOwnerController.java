package com.kh.Palette_BackEnd.controller;

import com.kh.Palette_BackEnd.service.IsOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class IsOwnerController {
    @Autowired
    private IsOwnerService isOwnerService;

    @GetMapping("/isMemberOfCouple")
    public ResponseEntity<Boolean> isMemberOfCouple(
            @RequestParam("coupleName") String coupleName,
            @RequestParam("email") String email) {

        boolean isMember = isOwnerService.isEmailInCouple(coupleName, email);

        return ResponseEntity.ok(isMember);
    }
}