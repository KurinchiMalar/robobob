package com.provenir.challenge.robobob.service.strategy;

/**
 * Strategy interface for answering questions.
 * @author KurinchiMalar
 */
public interface QAStrategy {

    /**
     * Determines if strategy can answer the given question.
     * @param question The question to check if answerable.
     * @return boolean true if the strategy can answer the question, false otherwise.
     */
    boolean isAnswerable(String question);

    /**
     * Provides answer to given question.
     * @param question The question to be answered.
     * @return String The answer to the question.
     */
    String answer(String question);
}
