package com.marek.binance_manager.service;

import com.marek.binance_manager.entity.User;
import com.marek.binance_manager.repository.UserRepository;
import com.marek.binance_manager.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);
        UserDetails userDetails = user.map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        return userDetails;
    }
}
