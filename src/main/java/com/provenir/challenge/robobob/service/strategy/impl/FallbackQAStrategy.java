package com.provenir.challenge.robobob.service.strategy.impl;

import com.provenir.challenge.robobob.service.strategy.QAStrategy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * QAStrategy implementation that provides default responses when no other strategy can answer.
 * This strategy has the least priority and
 * will always be able to answer questions with default answers when no other strategy is able to answer.
 *
 * @author KurinchiMalar
 */
@Component
@Order(Integer.MAX_VALUE) // least priority
public class FallbackQAStrategy implements QAStrategy {

    /**
     * Array of Default answers to be used when no other strategy is able to provide answer.
     * Responses for a particular question will be selection based on the question's hashcode for consistency.
     */
    private static final String[] DEFAULT_ANSWERS = {
            "I am not sure how to answer that.",
            "Sorry! I don't think I am able to answer that.",
            "Sorry! I don't have that information.",
            "I am afraid, I am not programmed for that question.",
            "Sorry! This question is beyond my current scope."
    };

    /**
     * FallBackStrategy will always be able to answer given question.
     * @param question The question.(ignored)
     * @return boolean Returns true always.
     */
    @Override
    public boolean isAnswerable(String question) {
        return true;
    }

    /**
     * Provides a consistent fallback response based on question's hash code.
     *
     * @param question The question to be answered.
     * @return String The response from DefaultAnswers array selected based on the question's hashcode.
     */
    @Override
    public String answer(String question) {

        // For the same question, we need to get the same answer everytime.
        int responseIndex = Math.abs(question.hashCode()) % DEFAULT_ANSWERS.length;
        return DEFAULT_ANSWERS[responseIndex];
    }
}
