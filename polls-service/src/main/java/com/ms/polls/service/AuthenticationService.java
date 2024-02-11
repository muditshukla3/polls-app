package com.ms.polls.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.polls.entity.Token;
import com.ms.polls.entity.User;
import com.ms.polls.request.LoginRequest;
import com.ms.polls.response.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final TokenService tokenService;


    public AuthResponse authenticate(LoginRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));
        User user = userService.findByUsername(request.getUsername());

        String tokenString = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        //revoke existing token
        tokenService.revokeTokens(user.getId());
        Token savedToken = tokenService.saveToken(refreshToken,user);
        if(savedToken.getId()!=null){
            return new AuthResponse(tokenString,savedToken.getToken());
        }else{
            return new AuthResponse("", "");
        }
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader("Authorization");
        final String refreshToken;
        final String username;
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            return;
        }

        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if(username!=null){
            UserDetails user = userService.findByUsername(username);
            Token token = tokenService.getToken(refreshToken);
            if(jwtService.isTokenValid(refreshToken, user) && (token!=null && !token.isRevoked())){
                String accessToken = jwtService.generateToken(user);
                AuthResponse authResponse =
                        new AuthResponse(accessToken, refreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
