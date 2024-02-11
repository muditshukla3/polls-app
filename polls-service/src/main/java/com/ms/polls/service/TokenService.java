package com.ms.polls.service;

import com.ms.polls.entity.Token;
import com.ms.polls.entity.TokenType;
import com.ms.polls.entity.User;
import com.ms.polls.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public Token saveToken(String tokenString, User user){
        Token token = Token.builder()
                .token(tokenString)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .user(user)
                .build();
        return tokenRepository.save(token);
    }

    public Token saveToken(Token token){
        return tokenRepository.save(token);
    }
    public void revokeTokens(Long id){
        List<Token> allValidTokensByUser = tokenRepository.findAllValidTokensByUser(id);
        if(allValidTokensByUser.isEmpty()){
            return;
        }
        allValidTokensByUser.forEach(token -> token.setRevoked(true));
        tokenRepository.saveAll(allValidTokensByUser);
    }

    public Token getToken(String token){
        return tokenRepository.findByToken(token).orElse(null);
    }
}
