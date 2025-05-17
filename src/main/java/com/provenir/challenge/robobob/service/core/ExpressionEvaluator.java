package com.provenir.challenge.robobob.service.core;

/**
 * Evaluates mathematical expressions
 */
public interface ExpressionEvaluator {

    /**
     * Checks if an expression is syntactically valid.
     * @param expression The arithmetic expression (eg: "2 + (3*4)")
     * @return boolean true if expression is valid, false otherwise.
     */
    boolean isValidExpression(String expression);

    /**
     * Evaluates a valid arithmetic expression and returns the result.
     *
     * @param expression A valid arithmetic expression.
     * @return double The computed result
     * @throws IllegalArgumentException if the expression is invalid.
     */
    double evaluate(String expression);
}
