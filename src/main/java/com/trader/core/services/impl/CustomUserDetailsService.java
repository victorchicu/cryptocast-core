package com.trader.core.services.impl;

import com.trader.core.domain.UserPrincipal;
import com.trader.core.exceptions.UserNotFoundException;
import com.trader.core.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userRepository;

    public CustomUserDetailsService(UserService userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDetails loadUserById(String id) {
        return userRepository.findById(id)
                .map(UserPrincipal::create)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username)
                .map(UserPrincipal::create)
                .orElseThrow(UserNotFoundException::new);
    }
}
