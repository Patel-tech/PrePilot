package com.preppilot.authentication.service.impl;

import com.preppilot.authentication.dto.RegisterRequest;
import com.preppilot.authentication.dto.RegisterResponse;
import com.preppilot.authentication.entity.Role;
import com.preppilot.authentication.entity.User;
import com.preppilot.authentication.mapper.AuthMapper;
import com.preppilot.authentication.mapper.UserMapper;
import com.preppilot.authentication.repository.RoleRepository;
import com.preppilot.authentication.repository.UserRepository;
import com.preppilot.authentication.service.AuthService;
import com.preppilot.common.exception.ResourceAlreadyExistsException;
import com.preppilot.common.exception.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    private static final String DEFAULT_ROLE = "ROLE_USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {

        // 1. Check whether email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw  new ResourceAlreadyExistsException("User already exists with email: "
                    + request.getEmail());
        }

        // 2. Convert DTO to Entity
        User user = userMapper.toEntity(request);

        // 3. Encode password
        String encodedPassword =
                passwordEncoder.encode(request.getPassword());

        user.setPassword(encodedPassword);

        // 4. Find default role
        Role userRole = roleRepository
                .findByName(DEFAULT_ROLE)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Default role not configured: "
                                        + DEFAULT_ROLE
                        )
                );

        // 5. Assign role
        user.addRole(userRole);

        // 6. Save user
        User savedUser = userRepository.save(user);

        // 7. Convert Entity to Response DTO
        return userMapper.toRegisterResponse(savedUser);
    }

}