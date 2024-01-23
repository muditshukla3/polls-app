package com.ms.polls.controller;

import com.ms.polls.entity.User;
import com.ms.polls.repository.UserRepository;
import com.ms.polls.request.LoginRequest;
import com.ms.polls.response.AuthResponse;
import com.ms.polls.service.JwtService;
import com.ms.polls.service.TokenService;
import com.ms.polls.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody LoginRequest request){

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()
                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByUsernameOrEmail(request.getUsernameOrEmail()
                                                         , request.getUsernameOrEmail())
                .orElseThrow(() -> new UsernameNotFoundException("user not found with username "+request.getUsernameOrEmail()));
        String username = user.getUsername();
        String token = jwtService.generateToken(username);
        String refreshToken = jwtService.generateRefreshToken(username);
        tokenService.revokeAllTokens(user);
        tokenService.saveRefreshToken(user, refreshToken);
        return ResponseEntity.ok(
                AuthResponse.builder()
                        .token(token)
                        .refreshToken(refreshToken)
                        .build()
        );
    }
}
