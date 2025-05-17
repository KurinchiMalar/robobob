package com.provenir.challenge.robobob.service.impl;


import com.provenir.challenge.robobob.service.strategy.QAStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultQuestionServiceTest {

    @Mock
    private QAStrategy strategy1;

    @Mock
    private QAStrategy strategy2;

    @Mock
    private QAStrategy fallbackStrategy;

    private DefaultQuestionService questionService;

    private  List<QAStrategy> qaStrategiesList;

    @BeforeEach
    public void setUp(){
        qaStrategiesList = Arrays.asList(strategy1,strategy2,fallbackStrategy);
        questionService = new DefaultQuestionService(qaStrategiesList);
    }

    @Test
    @DisplayName("Should throw IllegalArugmentException when question is null")
    public void testAnswerQuestion_NullQuestion_ThrowsException(){

        DefaultQuestionService questionServiceWithoutStrategy = new DefaultQuestionService(Collections.emptyList());

        assertThrows(IllegalArgumentException.class , () -> questionServiceWithoutStrategy.answerQuestion(null));
    }

    @Test
    @DisplayName("Should throw IllegalArugmentException when question is empty")
    public void testAnswerQuestion_EmptyQuestion_ThrowsException(){

        DefaultQuestionService questionServiceWithoutStrategy  = new DefaultQuestionService(Collections.emptyList());

        assertThrows(IllegalArgumentException.class , () -> questionServiceWithoutStrategy.answerQuestion(""));
    }

    @Test
    @DisplayName("Should use first strategy if valid answer can be obtained")
    public void testAnswerQuestion_FirstStrategyAnswerable_UsesFirstStrategy(){

        String question = "What is your name?";
        String expectedAnswer = "My name is RoboBob!";
        // stub to make strategy1 answerable.
        when(strategy1.isAnswerable(question)).thenReturn(true);
        when(strategy1.answer(question)).thenReturn(expectedAnswer);

        String answer = questionService.answerQuestion(question);

        assertEquals(expectedAnswer,answer);
        verify(strategy1).isAnswerable(question);
        verify(strategy1).answer(question);
        verify(strategy2,never()).isAnswerable(question);
        verify(strategy2,never()).answer(question);
        verify(fallbackStrategy,never()).isAnswerable(question);
        verify(fallbackStrategy,never()).answer(question);

    }

    @Test
    @DisplayName("Should use next strategy if valid answer cannot be obtained from first strategy")
    public void testAnswerQuestion_NextStrategyAnswerable_UsesSecondStrategy(){

        String question = "What is 2+3?";
        String expectedAnswer = "Answer is : 5";
        // stub to make strategy1 un-answerable and strategy2 answerable
        when(strategy1.isAnswerable(question)).thenReturn(false);
        when(strategy2.isAnswerable(question)).thenReturn(true);
        when(strategy2.answer(question)).thenReturn(expectedAnswer);

        String answer = questionService.answerQuestion(question);

        assertEquals(expectedAnswer,answer);
        verify(strategy1).isAnswerable(question);
        verify(strategy1,never()).answer(question);
        verify(strategy2).isAnswerable(question);
        verify(strategy2).answer(question);
        verify(fallbackStrategy,never()).isAnswerable(question);
        verify(fallbackStrategy,never()).answer(question);

    }

    @Test
    @DisplayName("Should use fallback strategy if no other strategy is answerable")
    public void testAnswerQuestion_NoStrategyAnswerable_UsesFallBacktrategy(){

        String question = "What is 2+3?";
        String expectedAnswer = "Fallback response";
        // stub to make strategy1 un-answerable and strategy2 un-answerable
        when(strategy1.isAnswerable(question)).thenReturn(false);
        when(strategy2.isAnswerable(question)).thenReturn(false);
        when(fallbackStrategy.isAnswerable(anyString())).thenReturn(true);
        when(fallbackStrategy.answer(anyString())).thenReturn("Fallback response");

        String answer = questionService.answerQuestion(question);

        assertEquals(expectedAnswer,answer);
        verify(strategy1).isAnswerable(question);
        verify(strategy1,never()).answer(question);
        verify(strategy2).isAnswerable(question);
        verify(strategy2,never()).answer(question);
        verify(fallbackStrategy).isAnswerable(question);
        verify(fallbackStrategy).answer(question);

    }

    @Test
    @DisplayName("Should return default message when no strategies are available to answer")
    public void testAnswerQuestion_NoStrategies_ReturnsDefaultMessage(){

        String expectedAnswer = "Sorry! I am unable to answer that question";
        String answer = questionService.answerQuestion("Random question");

        assertEquals(expectedAnswer,answer);

    }

}
