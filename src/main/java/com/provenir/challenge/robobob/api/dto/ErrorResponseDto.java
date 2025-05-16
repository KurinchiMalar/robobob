package com.provenir.challenge.robobob.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ErrorResponseDto {
    private String code;
    private String message;
    private Map<String,String> details;
    private long timestamp;

    public ErrorResponseDto() {
        this.timestamp = System.currentTimeMillis();
    }

    public ErrorResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public ErrorResponseDto(String code, String message, Map<String, String> details) {
        this.code = code;
        this.message = message;
        this.details = details;
        this.timestamp = System.currentTimeMillis();
    }

}
