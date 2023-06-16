package com.marek.binance_manager.controller.api;

import com.marek.binance_manager.dto.LoginDto;
import com.marek.binance_manager.dto.RegisterDto;
import com.marek.binance_manager.entity.Authority;
import com.marek.binance_manager.entity.User;
import com.marek.binance_manager.repository.AuthorityRepository;
import com.marek.binance_manager.repository.UserRepository;
import com.marek.binance_manager.service.UserAuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

     private final UserAuthService userAuthService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userAuthService.existsByUsername(registerDto.username()))
            return new ResponseEntity<>("username already taken", HttpStatus.BAD_REQUEST);
        return userAuthService.register(registerDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        return userAuthService.login(loginDto);
    }
}
