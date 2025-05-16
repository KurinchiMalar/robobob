package com.provenir.challenge.robobob.service.strategy.impl;


import com.provenir.challenge.robobob.service.core.repo.QuestionRepository;
import com.provenir.challenge.robobob.service.util.StringTransformationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PredefinedQAStrategyTest {

    @Mock
    private QuestionRepository questionRepository;

    private PredefinedQAStrategy predefinedQAStrategy;


    @BeforeEach
    public void setup(){
        predefinedQAStrategy = new PredefinedQAStrategy(questionRepository);
    }

    @Test
    @DisplayName("Should ensure question is answerable when repository has answer")
    public void testIsAnswerable_RepoHasAnswer_ReturnsTrue(){

        String question = "What is your name?";
        String formattedQuestion = StringTransformationUtils.convertToLowerCaseAndRemoveAllWhiteSpaces(question);
        when(questionRepository.findAnswerFor(formattedQuestion)).thenReturn(Optional.of("My name is RoboBob!"));

        boolean isAnswerable = predefinedQAStrategy.isAnswerable(question);

        assertTrue(isAnswerable);
        verify(questionRepository).findAnswerFor(formattedQuestion);
    }

    @Test
    @DisplayName("Should ensure question is not answerable when repository has no answer")
    public void testIsAnswerable_RepoHasNoAnswer_ReturnsFalse(){

        String question = "Random Question?";
        String formattedQuestion = StringTransformationUtils.convertToLowerCaseAndRemoveAllWhiteSpaces(question);
        when(questionRepository.findAnswerFor(formattedQuestion)).thenReturn(Optional.empty());

        boolean isAnswerable = predefinedQAStrategy.isAnswerable(question);

        assertFalse(isAnswerable);
        verify(questionRepository).findAnswerFor(formattedQuestion);
    }

    @Test
    @DisplayName("Should return correct answer when repository has the question")
    public void testAnswer_QuestionPresentInRepo_ReturnsAnswer(){

        String question = "What is your name?";
        String expectedAnswer = "My name is RoboBob!";
        String formattedQuestion = StringTransformationUtils.convertToLowerCaseAndRemoveAllWhiteSpaces(question);
        when(questionRepository.findAnswerFor(formattedQuestion)).thenReturn(Optional.of(expectedAnswer));

        String answer = predefinedQAStrategy.answer(formattedQuestion);

        assertEquals(expectedAnswer,answer);
        verify(questionRepository).findAnswerFor(formattedQuestion);
    }

    @Test
    @DisplayName("Should return correct answer when repository has the question with casing after being formatted")
    public void testAnswer_QuestionWithDifferentCasing_ReturnsAnswer(){

        String question = "WhAt is Your nAme?";
        String expectedAnswer = "My name is RoboBob!";
        String formattedQuestion = StringTransformationUtils.convertToLowerCaseAndRemoveAllWhiteSpaces(question);
        when(questionRepository.findAnswerFor(formattedQuestion)).thenReturn(Optional.of(expectedAnswer));

        String answer = predefinedQAStrategy.answer(formattedQuestion);

        assertEquals(expectedAnswer,answer);
        verify(questionRepository).findAnswerFor(formattedQuestion);
    }

    @Test
    @DisplayName("Should return a default answer when repository doesn't have the question")
    public void testAnswer_QuestionNotPresentInRepo_ReturnsDefaultAnswer(){

        String question = "Unknown?";
        String formattedQuestion = StringTransformationUtils.convertToLowerCaseAndRemoveAllWhiteSpaces(question);
        when(questionRepository.findAnswerFor(formattedQuestion)).thenReturn(Optional.empty());

        String answer = predefinedQAStrategy.answer(formattedQuestion);

        assertEquals("I do not have answer",answer);
        verify(questionRepository).findAnswerFor(formattedQuestion);
    }



}
