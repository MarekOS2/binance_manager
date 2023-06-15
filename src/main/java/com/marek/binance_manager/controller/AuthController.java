package com.marek.binance_manager.controller;

import com.marek.binance_manager.dto.RegisterDto;
import com.marek.binance_manager.entity.Authority;
import com.marek.binance_manager.entity.User;
import com.marek.binance_manager.repository.AuthorityRepository;
import com.marek.binance_manager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    //TODO: add auth service and all logic in there from this class

     private final AuthenticationManager authenticationManager;
     private final UserRepository userRepository;
     private final AuthorityRepository authorityRepository;
     private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.username()))
            return new ResponseEntity<>("username already taken", HttpStatus.BAD_REQUEST);

        User user = new User();

        user.setUsername(registerDto.username());
        user.setPassword(passwordEncoder.encode(registerDto.password()));

        Authority authorities = authorityRepository.getAuthorityByName("USER").get();
        user.setAuthorities(Collections.singleton(authorities));

        userRepository.save(user);

        return new ResponseEntity<>("user successfully registered", HttpStatus.OK);
    }
}
