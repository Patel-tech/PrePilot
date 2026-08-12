package com.preppilot.authentication.security.refresh;

import com.preppilot.authentication.entity.RefreshToken;
import com.preppilot.authentication.entity.User;

public interface RefreshTokenService {

    RefreshToken createToken(User user);

    RefreshToken verifyToken(String token);

    void revokeToken(String token);
}
