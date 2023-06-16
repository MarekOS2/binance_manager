package com.marek.binance_manager.controller.web;

import com.marek.binance_manager.controller.api.AuthController;
import com.marek.binance_manager.dto.RegisterDto;
import com.marek.binance_manager.service.UserAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class WebAuthController {
    private final UserAuthService userAuthService;
    @GetMapping("/register")
    public String getRegisterForm(Model model) {

        return "register";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @ModelAttribute RegisterDto registerDto) {
        if (userAuthService.existsByUsername(registerDto.username())) {
            return new ResponseEntity<>("username already exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
}
