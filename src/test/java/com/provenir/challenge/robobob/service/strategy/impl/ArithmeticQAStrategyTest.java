package com.provenir.challenge.robobob.service.strategy.impl;


import com.provenir.challenge.robobob.service.impl.evaluator.ArithmeticExpressionEvaluator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArithmeticQAStrategyTest {

    @Mock
    private ArithmeticExpressionEvaluator evaluator;

    private ArithmeticQAStrategy arithmeticQAStrategy;

    @BeforeEach
    public void setUp(){
        arithmeticQAStrategy = new ArithmeticQAStrategy(evaluator);
    }

    @ParameterizedTest
    @CsvSource({
            "What is 1+4?, 1+4",
            "Calculate 6-3, 6-3",
            "Compute 5*4, 5*4",
            "Evaluate 1.9+2.3, 1.9+2.3"
    })
    @DisplayName("Should be able to extract exact expressions from given questions.")
    public void testExtractArithmeticExpr_ArithmeticQuestions_ReturnsCorrectExpression(String question, String expectedExpression){
        String actualExpression = arithmeticQAStrategy.extractArithmeticExpr(question);
        assertEquals(expectedExpression,actualExpression);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Who are you?",
            "What is your name?",
            "1+2=3",
            "Does 1+1 =2?"
    })
    @DisplayName("Should return null for non arithmetic questions")
    public void testExtractArithmeticExpr_NonArithmeticQuestions_ReturnsNull(String question){
        String expression = arithmeticQAStrategy.extractArithmeticExpr(question);
        assertNull(expression);
    }

    @Test
    @DisplayName("Should return true when question contains valid expression")
    public void testIsAnswerable_ValidExpression_ReturnsTrue(){
        String question = "What is 4+9?";
        when(evaluator.isValidExpression("4+9")).thenReturn(true);

        boolean isAnswerable = arithmeticQAStrategy.isAnswerable(question);
        assertTrue(isAnswerable);
        verify(evaluator).isValidExpression("4+9");
    }

    @Test
    @DisplayName("Should return false when question does not match arithmetic pattern")
    public void testIsAnswerable_NonArithmeticQuestion_ReturnsFalse(){
        String question = "What is your name";

        boolean isAnswerable = arithmeticQAStrategy.isAnswerable(question);
        assertFalse(isAnswerable);
        verify(evaluator,never()).isValidExpression(anyString());
    }

    @Test
    @DisplayName("Should return correct answer for valid arithmetic addition question")
    public void testAnswer_ValidArithmeticAdditionQuestion_ReturnsCorrectAnswer(){
        String question = "What is 4+2?";
        when(evaluator.evaluate("4+2")).thenReturn(6.0);

        String answer = arithmeticQAStrategy.answer(question);

        assertEquals(" Answer is : 6",answer);
        verify(evaluator).evaluate("4+2");

    }

    @Test
    @DisplayName("Should return correct answer for valid arithmetic division question")
    public void testAnswer_ValidArithmeticDivisionQuestion_ReturnsCorrectAnswer(){
        String question = "What is 10/4?";
        when(evaluator.evaluate("10/4")).thenReturn(2.5);

        String answer = arithmeticQAStrategy.answer(question);

        assertEquals(" Answer is : 2.5",answer);
        verify(evaluator).evaluate("10/4");

    }

    @Test
    @DisplayName("Should return error message for division by zero in question")
    public void testAnswer_DivisonByZeroQuestion_ReturnsErrorMessage(){
        String question = "What is 5/0?";
        when(evaluator.evaluate("5/0")).thenThrow(new ArithmeticException("Division by zero"));

        String answer = arithmeticQAStrategy.answer(question);

        assertEquals("Sorry! Invalid Arithmetic Expression: Division by zero",answer);
        verify(evaluator).evaluate("5/0");

    }

    @Test
    @DisplayName("Should ensure decimal results are formatted legibly after decimal point")
    public void testAnswer_DecimalResultWithTrailingZeroes_RemovesTrailingZeroes(){

        String question1 = "Calculate 10/2";
        String question2 = "Evaluate 10/4";
        when(evaluator.evaluate("10/2")).thenReturn(5.0);
        when(evaluator.evaluate("10/4")).thenReturn(2.50);
        String answer1 = arithmeticQAStrategy.answer(question1);
        String answer2 = arithmeticQAStrategy.answer(question2);

        assertEquals(" Answer is : 5",answer1);
        assertEquals(" Answer is : 2.5",answer2);
    }

    @Test
    @DisplayName("Should return error message when extraction of expression fails")
    public void testAnswer_ExpressionExtractionFails_ReturnsErrorMessage(){

        String question = "I am an invalid arithmetic question";

        String answer = arithmeticQAStrategy.answer(question);

        assertEquals("Sorry! I am not able to parse the given expression.",answer);
        verify(evaluator,never()).evaluate(anyString());
    }


}
