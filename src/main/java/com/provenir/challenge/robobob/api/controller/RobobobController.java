package com.provenir.challenge.robobob.api.controller;

import com.provenir.challenge.robobob.api.dto.AnswerResponseDto;
import com.provenir.challenge.robobob.api.dto.QuestionRequestDto;
import com.provenir.challenge.robobob.service.core.QuestionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for Robobob application which can answer questions.
 * Provides API endpoint for submitting questions and responding with answers.
 * @author KurinchiMalar
 */
@RestController
@RequestMapping("/api/v1")
public class RobobobController {

    private static final Logger logger = LoggerFactory.getLogger(RobobobController.class);
    private final QuestionService questionService;

    /**
     * Constructs a RobobobController instance with the specified QuestionService.
     * @param questionService Service for actually processing the questions and generate answers.
     */
    @Autowired
    public RobobobController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * POST end point for submitting questions to Robobob.
     * @param request DTO containing the question to be answered.
     * @return ResponseEntity containing the answer and timestamp.
     */
    @PostMapping("/ask")
    public ResponseEntity<AnswerResponseDto> askQuestion(@Valid @RequestBody QuestionRequestDto request){
        logger.info("Question : {}", request.getQuestion());
        String answer = questionService.answerQuestion(request.getQuestion());
        logger.debug("Answer : {}",answer);
        return ResponseEntity.ok(new AnswerResponseDto(answer));
    }
}
