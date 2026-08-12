package com.preppilot.authentication.security.refresh;

import com.preppilot.authentication.entity.RefreshToken;
import com.preppilot.authentication.entity.User;
import com.preppilot.authentication.repository.RefreshTokenRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenServiceImpl
        implements RefreshTokenService {

    private static final long REFRESH_TOKEN_DAYS = 7;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenServiceImpl(
            RefreshTokenRepository refreshTokenRepository) {

        this.refreshTokenRepository =
                refreshTokenRepository;
    }

    @Override
    public RefreshToken createToken(User user) {

        RefreshToken refreshToken =
                new RefreshToken();

        refreshToken.setToken(
                UUID.randomUUID().toString()
        );

        refreshToken.setUser(user);

        refreshToken.setExpiryDate(
                LocalDateTime.now()
                        .plusDays(REFRESH_TOKEN_DAYS)
        );

        refreshToken.setRevoked(false);

        return refreshTokenRepository
                .save(refreshToken);
    }

    @Override
    public RefreshToken verifyToken(
            String token) {

        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByToken(token)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Refresh token not found"
                                )
                        );

        if (refreshToken.isRevoked()) {
            throw new IllegalArgumentException(
                    "Refresh token has been revoked"
            );
        }

        if (refreshToken.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            throw new IllegalArgumentException(
                    "Refresh token has expired"
            );
        }

        return refreshToken;
    }

    @Override
    public void revokeToken(
            String token) {

        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByToken(token)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Refresh token not found"
                                )
                        );

        refreshToken.setRevoked(true);

        refreshTokenRepository.save(refreshToken);
    }
}