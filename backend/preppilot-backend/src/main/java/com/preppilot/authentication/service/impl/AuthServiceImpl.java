package com.preppilot.authentication.service.impl;

import com.preppilot.authentication.dto.LoginRequest;
import com.preppilot.authentication.dto.LoginResponse;
import com.preppilot.authentication.dto.RegisterRequest;
import com.preppilot.authentication.dto.RegisterResponse;
import com.preppilot.authentication.entity.RefreshToken;
import com.preppilot.authentication.entity.Role;
import com.preppilot.authentication.entity.User;
import com.preppilot.authentication.exception.UserAlreadyExistsException;
import com.preppilot.authentication.jwt.TokenProvider;
import com.preppilot.authentication.mapper.UserMapper;
import com.preppilot.authentication.repository.RoleRepository;
import com.preppilot.authentication.repository.UserRepository;
import com.preppilot.authentication.security.refresh.RefreshTokenService;
import com.preppilot.authentication.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    private static final String DEFAULT_ROLE = "ROLE_USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    private final TokenProvider tokenProvider;

    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            UserMapper userMapper,
            AuthenticationManager authenticationManager,
            TokenProvider tokenProvider,
            RefreshTokenService refreshTokenService,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {

        String email = request.getEmail()
                .trim()
                .toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(
                    "User already exists with email: " + email
            );
        }

        User user = userMapper.toEntity(request);

        user.setEmail(email);

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

    // for Login
    @Override
    public LoginResponse login(
            LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(

                        new UsernamePasswordAuthenticationToken(

                                request.getEmail(),

                                request.getPassword()
                        )
                );

        String token =
                tokenProvider.generateToken(authentication);

        User user =
                userRepository
                        .findByEmail(
                                request.getEmail()
                        )
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "User not found"
                                )
                        );

        RefreshToken refreshToken =
                refreshTokenService
                        .createToken(user);

        return new LoginResponse(
                token,
                refreshToken.getToken(),
                "Bearer",
                user.getEmail()
        );
    }

    @Override
    public LoginResponse refreshToken(
            String token) {

        RefreshToken refreshToken =
                refreshTokenService.verifyToken(token);

        User user =
                refreshToken.getUser();

        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                        .password(user.getPassword())
                        .authorities(
                                user.getRoles()
                                        .stream()
                                        .map(Role::getName)
                                        .toArray(String[]::new)
                        )
                        .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        String accessToken =
                tokenProvider.generateToken(
                        authentication
                );

        return new LoginResponse(
                accessToken,
                token,
                "Bearer",
                user.getEmail()
        );
    }

    @Override
    public void logout(String refreshToken) {

        refreshTokenService.revokeToken(
                refreshToken
        );
    }

}