package com.preppilot.authentication.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String message;

    public RegisterResponse() {
    }

    public RegisterResponse(Long id,
                            String firstName,
                            String lastName,
                            String email,
                            String message) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.message = message;
    }


    public void setFullName(String s) {

    }
}