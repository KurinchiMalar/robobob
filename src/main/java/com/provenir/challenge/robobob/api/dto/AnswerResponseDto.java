package com.provenir.challenge.robobob.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AnswerResponseDto {

    private String answer;
    private long timeStamp;

    public AnswerResponseDto(){
        this.timeStamp = Instant.now().toEpochMilli();
    }
    public AnswerResponseDto(String answer) {
        this.answer = answer;
        this.timeStamp = Instant.now().toEpochMilli();
    }
}
