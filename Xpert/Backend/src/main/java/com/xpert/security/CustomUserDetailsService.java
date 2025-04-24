package com.xpert.security;

import com.xpert.entity.Users;
import com.xpert.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;



import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        log.info("Authenticating user with email: {}", email);

        Users user = userRepository.findByEmail(email)
        		.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(
        	    user.getEmail(),
        	    user.getPasswordHash(),
        	    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        	);

    }
}
