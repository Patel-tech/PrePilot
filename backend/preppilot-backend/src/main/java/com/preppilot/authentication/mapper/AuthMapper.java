package com.preppilot.authentication.mapper;

import com.preppilot.authentication.dto.RegisterResponse;
import com.preppilot.authentication.entity.User;

public class AuthMapper {

    private AuthMapper(){}

    public static RegisterResponse toRegisterResponse(User user){

        RegisterResponse response=new RegisterResponse();

        response.setId(user.getId());

        response.setFullName(
                user.getFirstName()+" "+user.getLastName());

        response.setEmail(user.getEmail());

        return response;

    }

}