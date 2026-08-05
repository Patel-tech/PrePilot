package com.preppilot.authentication.service.impl;

import com.preppilot.authentication.dto.RegisterRequest;
import com.preppilot.authentication.dto.RegisterResponse;
import com.preppilot.authentication.entity.Role;
import com.preppilot.authentication.entity.User;
import com.preppilot.authentication.mapper.AuthMapper;
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

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder){

        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
        this.passwordEncoder=passwordEncoder;

    }

    @Override
    public RegisterResponse register(RegisterRequest request){

        if(userRepository.existsByEmail(request.getEmail())){

            throw new ResourceAlreadyExistsException(
                    "Email already registered.");

        }

        Role role=roleRepository.findByName("ROLE_USER")
                .orElseThrow(()->
                        new ResourceNotFoundException(
                                "Default role not found"));

        User user=new User();

        user.setFirstName(request.getFirstName());

        user.setLastName(request.getLastName());

        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(request.getPassword()));

        user.setEnabled(true);

        user.getRoles().add(role);

        User savedUser=userRepository.save(user);

        return AuthMapper.toRegisterResponse(savedUser);

    }

}