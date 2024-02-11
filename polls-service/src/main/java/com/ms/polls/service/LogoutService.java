package com.ms.polls.service;

import com.ms.polls.entity.Token;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutService implements LogoutHandler {

    private final TokenService tokenService;
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        log.info("Logout handler invoked...");
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            return;
        }
        jwtToken = authHeader.substring(7);
        Token token = tokenService.getToken(jwtToken);
        if(token!=null){
            token.setRevoked(true);
            tokenService.saveToken(token);
        }
    }
}
