package com.preppilot.authentication.service;

import com.preppilot.authentication.dto.RegisterRequest;
import com.preppilot.authentication.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

}