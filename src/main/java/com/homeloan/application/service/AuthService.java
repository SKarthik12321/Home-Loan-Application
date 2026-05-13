package com.homeloan.application.service;

import com.homeloan.application.dto.auth.AuthResponse;
import com.homeloan.application.dto.auth.LoginRequest;
import com.homeloan.application.dto.auth.RegisterRequest;
import com.homeloan.application.entity.UserAccount;
import com.homeloan.application.exception.BusinessRuleException;
import com.homeloan.application.repository.UserAccountRepository;
import com.homeloan.application.security.ApplicationUserDetailsService;
import com.homeloan.application.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Registration and login orchestration (JWT issuance).
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ApplicationUserDetailsService userDetailsService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userAccountRepository.existsByUsernameIgnoreCase(request.username())) {
            throw new BusinessRuleException("Username already taken");
        }
        UserAccount account = UserAccount.builder()
                .username(request.username().trim())
                .passwordHash(passwordEncoder.encode(request.password()))
                .role("USER")
                .build();
        userAccountRepository.save(account);
        UserDetails userDetails = userDetailsService.loadUserByUsername(account.getUsername());
        String token = jwtService.generateToken(userDetails);
        return toResponse(token, account.getUsername());
    }

    public AuthResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        return toResponse(token, userDetails.getUsername());
    }

    private AuthResponse toResponse(String token, String username) {
        return new AuthResponse("Bearer", token, jwtService.getExpirationSeconds(), username);
    }
}
