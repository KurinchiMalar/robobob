package com.provenir.challenge.robobob.service.strategy.impl;

import com.provenir.challenge.robobob.service.strategy.QAStrategy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Integer.MAX_VALUE) // least priority
public class FallbackQAStrategy implements QAStrategy {

    private static final String[] DEFAULT_ANSWERS = {
            "I am not sure how to answer that.",
            "Sorry! I don't think I am able to answer that.",
            "Sorry! I don't have that information.",
            "I am afraid, I am not programmed for that question.",
            "Sorry! This question is beyond my current scope."
    };


    @Override
    public boolean isAnswerable(String question) {
        // as this is a fallback strategy, we will be able to provide a fallback answer.
        return true;
    }

    @Override
    public String answer(String question) {
        // For the same question, we need to get the same answer (Consistency required).
        // Hence hash based on question to get the index.
        int responseIndex = Math.abs(question.hashCode()) % DEFAULT_ANSWERS.length;
        return DEFAULT_ANSWERS[responseIndex];
    }
}
