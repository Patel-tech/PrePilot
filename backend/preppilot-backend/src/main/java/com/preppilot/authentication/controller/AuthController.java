package com.preppilot.authentication.controller;

import com.preppilot.authentication.dto.RegisterRequest;
import com.preppilot.authentication.dto.RegisterResponse;
import com.preppilot.authentication.service.AuthService;
import com.preppilot.common.response.ApiResponse;
import com.preppilot.common.response.ResponseBuilder;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){

        this.authService=authService;

    }

    @PostMapping("/register")
    public ApiResponse<RegisterResponse> register(

            @Valid
            @RequestBody
            RegisterRequest request){

        RegisterResponse response=
                authService.register(request);

        return ResponseBuilder.success(
                "User Registered Successfully",
                response);

    }

}