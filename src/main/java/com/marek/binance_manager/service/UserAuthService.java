package com.marek.binance_manager.service;

import com.marek.binance_manager.dto.LoginDto;
import com.marek.binance_manager.dto.RegisterDto;
import com.marek.binance_manager.entity.Authority;
import com.marek.binance_manager.entity.User;
import com.marek.binance_manager.repository.AuthorityRepository;
import com.marek.binance_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> register(RegisterDto registerDto) {
        User user = new User();
        user.setUsername(registerDto.username());
        user.setEmail(registerDto.email());
        user.setPassword(registerDto.password());

        Authority authorities = authorityRepository.getAuthorityByName("USER").get();
        user.setAuthorities(Collections.singleton(authorities));

        userRepository.save(user);

        return ResponseEntity.ok("user successfully registered");
    }

    public ResponseEntity<String> login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.username(), loginDto.password()));
        return new ResponseEntity<>("User successfully logged in", HttpStatus.OK);
    }

    public boolean existsByUsername(String username) {
        if (userRepository.existsByUsername(username))
            return true;
        else return false;
    }
}
