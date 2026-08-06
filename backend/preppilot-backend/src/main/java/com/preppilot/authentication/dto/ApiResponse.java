package com.preppilot.authentication.dto;



import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
 @Getter
public class ApiResponse<T> {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(int status, String message, T data) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.data = data;
    }


}
