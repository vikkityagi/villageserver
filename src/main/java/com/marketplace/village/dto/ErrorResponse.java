package com.marketplace.village.dto;



import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorResponse {

    private String message;
    private LocalDateTime time;

    public ErrorResponse(String message, LocalDateTime time) {
        this.message = message;
        this.time = time;
    }

    // getters
}
