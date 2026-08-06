package com.preppilot.authentication.controller;

import com.preppilot.authentication.dto.RegisterRequest;
import com.preppilot.authentication.dto.RegisterResponse;
import com.preppilot.authentication.entity.User;
import com.preppilot.authentication.mapper.UserMapper;
import com.preppilot.authentication.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/test")
public class TestController {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public TestController(UserRepository userRepository,UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @GetMapping("/count")
    public Long countUsers() {
        return userRepository.count();
    }

//    @PostMapping("/create")
//    public RegisterResponse test(
//            @RequestBody RegisterRequest request) {
//
//        User user = mapper.toEntity(request);
//
//        user.setId(1L);
//
//        return mapper.toRegisterResponse(user);
//    }
}