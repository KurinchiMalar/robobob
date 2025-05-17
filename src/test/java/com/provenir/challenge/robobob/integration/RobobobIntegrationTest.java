package com.provenir.challenge.robobob.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.provenir.challenge.robobob.api.RobobobApplication;
import com.provenir.challenge.robobob.api.dto.AnswerResponseDto;
import com.provenir.challenge.robobob.api.dto.QuestionRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RobobobApplication.class)
@AutoConfigureMockMvc
public class RobobobIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String ASK_API_URI = "/api/v1/robobob/ask";
    @Test
    @DisplayName("Integration Test - Known question should return exact answer")
    public void testKnownQuestion() throws Exception{

        QuestionRequestDto requestDto = new QuestionRequestDto("What is your name?");
        String expectedAnswer = "My name is RoboBob!";

        MvcResult result = mockMvc.perform(post(ASK_API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn();

        AnswerResponseDto response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                AnswerResponseDto.class);

        assertNotNull(response);
        assertEquals(expectedAnswer,response.getAnswer());

    }
}
