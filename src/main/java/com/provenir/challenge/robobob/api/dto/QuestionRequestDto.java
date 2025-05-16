package com.provenir.challenge.robobob.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionRequestDto {

    @NotBlank(message = "Question cannot be empty")
    @Size(min = 3, max = 500, message = "Question should be between 3 and 500 characters")
    private String question;
}
