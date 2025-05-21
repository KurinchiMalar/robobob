package com.provenir.challenge.robobob.api.exception;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.provenir.challenge.robobob.api.controller.RobobobController;
import com.provenir.challenge.robobob.api.dto.QuestionRequestDto;
import com.provenir.challenge.robobob.service.core.QuestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RobobobController.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private QuestionService questionService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Should be able to handle IllegalArgumentException and return appropriate error message")
    public void testHandleIllegalArgumentException() throws Exception{

        QuestionRequestDto requestDto = new QuestionRequestDto("What is 6+abc?");
        String errorMessage = "Invalid arithmetic expression";

        when(questionService.answerQuestion(anyString())).thenThrow(new IllegalArgumentException(errorMessage));

        mockMvc.perform(post("/api/v1/ask")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_INPUT"))
                .andExpect(jsonPath("$.message").value(errorMessage))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Should be able to handle MethodArgumentNotValidException and return appropriate error message")
    public void testHandleMethodArgumentNotValidException() throws Exception{

        QuestionRequestDto requestDto = new QuestionRequestDto("");

        mockMvc.perform(post("/api/v1/ask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value(containsString("Question cannot be empty")))
                .andExpect(jsonPath("$.message").value(containsString("Question should be between 3 and 500 characters")))
                .andExpect(jsonPath("$.timestamp").exists());

    }

    @Test
    @DisplayName("Should be able to handle Generic Exceptions and return internal server error")
    public void testHandleGenericException() throws Exception{

        QuestionRequestDto requestDto = new QuestionRequestDto("What is your name?");
        String errorMessage = "Unexpected error happened";

        when(questionService.answerQuestion(anyString())).thenThrow(new RuntimeException(errorMessage));

        mockMvc.perform(post("/api/v1/ask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.message").value(errorMessage))
                .andExpect(jsonPath("$.timestamp").exists());

    }
}
