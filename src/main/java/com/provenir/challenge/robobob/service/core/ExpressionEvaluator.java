package com.provenir.challenge.robobob.service.core;

public interface ExpressionEvaluator {

    boolean isValidExpression(String expression);

    double evaluate(String expression);
}
