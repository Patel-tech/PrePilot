package com.preppilot.authentication.mapper;

import com.preppilot.authentication.dto.RegisterRequest;
import com.preppilot.authentication.dto.RegisterResponse;
import com.preppilot.authentication.entity.User;

public interface UserMapper {

    User toEntity(RegisterRequest request);

    RegisterResponse toRegisterResponse(User user);

}