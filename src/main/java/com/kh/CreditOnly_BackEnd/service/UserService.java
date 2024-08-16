package com.kh.CreditOnly_BackEnd.service;

import com.kh.CreditOnly_BackEnd.entity.UserEntity;
import com.kh.CreditOnly_BackEnd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles().toArray(new String[0]))
                .accountExpired(!user.isActive())
                .accountLocked(!user.isActive())
                .credentialsExpired(!user.isActive())
                .disabled(!user.isActive())
                .build();
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public UserEntity createUser(UserEntity user) {
        // 여기에 사용자 생성 로직 추가 (비밀번호 암호화 등)
        return userRepository.save(user);
    }

    public UserEntity updateUser(UserEntity user) {
        // 여기에 사용자 업데이트 로직 추가
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    // 필요에 따라 추가 메서드 구현
}
