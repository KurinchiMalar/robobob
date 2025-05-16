package com.provenir.challenge.robobob.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.provenir.challenge.robobob.api.dto.QuestionRequestDto;
import com.provenir.challenge.robobob.service.core.QuestionService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RobobobController.class)
public class RobobobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private QuestionService questionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return successful response when a valid question is asked")
    public void testAskQuestion_ValidQuestion1_ReturnsAnswer() throws Exception{

        QuestionRequestDto requestDto = new QuestionRequestDto("What is your name?");
        String expectedAnswer = "My name is RoboBob!";

        when(questionService.answerQuestion(anyString())).thenReturn(expectedAnswer);

        mockMvc.perform(post("/api/v1/robobob/ask")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value(expectedAnswer))
                .andExpect(jsonPath("$.timeStamp").exists());
    }

    @Test
    @DisplayName("Should return successful response when a valid question is asked")
    public void testAskQuestion_ValidQuestion2_ReturnsAnswer() throws Exception{

        QuestionRequestDto requestDto = new QuestionRequestDto("how are you?");
        String expectedAnswer = "I'm fine, thank you for asking! How can I help you?";

        when(questionService.answerQuestion(anyString())).thenReturn(expectedAnswer);

        mockMvc.perform(post("/api/v1/robobob/ask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value(expectedAnswer))
                .andExpect(jsonPath("$.timeStamp").exists());
    }

    @Test
    @DisplayName("Should return bad request for empty question")
    public void testAskQuestion_EmptyQuestion_ReturnsBadRequest() throws Exception{

        QuestionRequestDto requestDto = new QuestionRequestDto("");

        mockMvc.perform(post("/api/v1/robobob/ask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.details.question").exists());
    }

    @Test
    @DisplayName("Should return bad request for short question")
    public void testAskQuestion_ShortQuestion_ReturnsBadRequest() throws Exception{

        QuestionRequestDto requestDto = new QuestionRequestDto("hi");

        mockMvc.perform(post("/api/v1/robobob/ask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.details.question").exists());
    }

    @Test
    @DisplayName("Should return bad request for long question")
    public void testAskQuestion_LongQuestion_ReturnsBadRequest() throws Exception{

        StringBuilder longQuestionSb = new StringBuilder();
        for(int i = 0; i < 501; i++) longQuestionSb.append("a");
        QuestionRequestDto requestDto = new QuestionRequestDto(longQuestionSb.toString());

        mockMvc.perform(post("/api/v1/robobob/ask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.details.question").exists());
    }


}
