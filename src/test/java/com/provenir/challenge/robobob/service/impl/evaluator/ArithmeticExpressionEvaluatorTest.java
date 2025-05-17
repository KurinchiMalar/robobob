package com.provenir.challenge.robobob.service.impl.evaluator;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ArithmeticExpressionEvaluatorTest {

    private ArithmeticExpressionEvaluator evaluator;

    @BeforeEach
    public void setUp(){
        evaluator = new ArithmeticExpressionEvaluator();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "5+7",
            "9-5",
            "3*7",
            "4/2",
            "1 +6 ",
            "(4+5)*6",
            "10/(7+3)",
            "8.6 + 2.3",
            "10.1-2.2"
    })
    @DisplayName("Should return true for valid expressions")
    public void testIsValidExpression_ValidExpressions_ReturnsTrue(String expression){
        assertTrue(evaluator.isValidExpression(expression));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "  ",
            "1+abc",
            "1++2",
            "x+y",
            "1+2=3",
            "max(x)",
            "1,890+5,890",
            "2!£"
    })
    @DisplayName("Should return false for invalid expressions")
    public void testIsValidExpression_InValidExpressions_ReturnsFalse(String expression){
        assertFalse(evaluator.isValidExpression(expression));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for invalid expressions")
    public void testEvaluate_InvalidExpression_ThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> evaluator.evaluate("3+x"));
    }

    @Test
    @DisplayName("Should throw ArithmeticException for divison by zero")
    public void testEvaluate_DivisionByZero_ThrowsException(){
        assertThrows(ArithmeticException.class, () -> evaluator.evaluate("7/0"));
    }

    @ParameterizedTest
    @CsvSource({
            "5+7, 12.0",
            "9-5, 4.0",
            "3*7, 21.0",
            "4/2, 2.0",
            "1 +6, 7.0 ",
            "(4+5)*6, 54.0",
            "10/(7+3), 1.0",
            "8.6 + 2.3, 10.9",
            "10.1-2.2, 7.9"
    })
    @DisplayName("Should return correct answer for valid arithmetic expressions")
    public void testEvaluate_ValidExpressions_ReturnCorrectResult(String expression, double expected){
        assertEquals(expected,evaluator.evaluate(expression),0.0001);
    }

    @Test
    @DisplayName("Should evaluate expressions ignoring whitespace and give correct answer")
    public void testEvaluate_ExpressionsWithWhiteSpace_ReturnsCorrectAnswer(){
        assertEquals(8.0,evaluator.evaluate("  7+ 1    "),0.0001);
    }

    @Test
    @DisplayName("Should evaluate expressions with nested brackets and give correct answer")
    public void testEvaluate_ExpressionsWithNestedBrackets_ReturnsCorrectAnswer(){
        assertEquals(21.0,evaluator.evaluate("3 * ((2+3) +2)"),0.0001);
    }

}
