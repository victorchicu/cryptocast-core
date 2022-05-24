package com.coinbank.core.domain.services.impl;

import com.coinbank.core.domain.UserPrincipal;
import com.coinbank.core.domain.exceptions.UserNotFoundException;
import com.coinbank.core.domain.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);

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
