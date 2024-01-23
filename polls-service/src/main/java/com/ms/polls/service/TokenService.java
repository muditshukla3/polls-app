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

    public void saveRefreshToken(User user, String refreshToken) {
        Token token = Token
                .builder()
                .expired(false)
                .token(refreshToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .user(user)
                .build();
        tokenRepository.save(token);

    }

    public void revokeAllTokens(User user) {
        List<Token> allValidTokenByUser = tokenRepository
                .findAllValidTokensByUser(user.getId());
        if(!allValidTokenByUser.isEmpty()){
            allValidTokenByUser.forEach(
                    token -> {
                        token.setExpired(true);
                        token.setRevoked(true);
                    }
            );
            tokenRepository.saveAll(allValidTokenByUser);
        }
    }
}
