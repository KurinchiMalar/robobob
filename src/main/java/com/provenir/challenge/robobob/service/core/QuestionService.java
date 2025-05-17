package com.provenir.challenge.robobob.service.core;

/**
 * This is the Core service for answering questions in this application.
 * Implementations of this interfacae handle different question types using Strategy pattern.
 */
public interface QuestionService {

    /**
     * Answers a given question by delegating to proper strategies.
     *
     * @param question The input question (must not be null or blank)
     * @return The answer to the question, or a fall back response if the answer is not known.
     * @throws IllegalArgumentException if the question is null or empty
     */
    String answerQuestion(String question);
}
