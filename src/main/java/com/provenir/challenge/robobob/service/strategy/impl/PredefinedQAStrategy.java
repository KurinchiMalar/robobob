package com.provenir.challenge.robobob.service.strategy.impl;

import com.provenir.challenge.robobob.service.core.repo.QuestionRepository;
import com.provenir.challenge.robobob.service.strategy.QAStrategy;
import com.provenir.challenge.robobob.service.util.StringTransformationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * QAStrategy implementation that answers questions from a predefined set of question-answer pairs in repository.
 * This strategy has the highest priority and will attempt finding out the answer from the repository.
 *
 * @author KurinchiMalar
 */
@Component
@Order(1) // Highest priority
public class PredefinedQAStrategy implements QAStrategy {

    private static final Logger logger = LoggerFactory.getLogger(PredefinedQAStrategy.class);
    private final QuestionRepository questionRepository;

    /**
     * Constructs PredefinedQAStrategy with the specified question repository.
     *
     * @param questionRepository Repository containing pre-defined question-answer pairs
     */
    @Autowired
    public PredefinedQAStrategy(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * Validates if the question can be answered by looking it up in the predefined set.
     *
     * @param question The question to check if answerable.
     * @return boolean returns true if the question exists in repository, false otherwise
     */

    @Override
    public boolean isAnswerable(String question) {
        boolean isAnswerable = findAnswer(question).isPresent();
        logger.debug("isAnswerable '{}': {}",question,isAnswerable);
        return isAnswerable;
    }

    /**
     * Answers the questions by retrieving the predefined answer from repository.
     *
     * @param question The question to be answered.
     * @return String The predefined answer if found , a default message otherwise.
     */
    @Override
    public String answer(String question) {
        return findAnswer(question)
                .orElse("I do not have answer");
    }

    /**
     * Looksup the question in the repository after formatting it as per expectations of repository.
     *
     * @param question The question to lookup
     * @return Optional containing the answer if found, empty otherwise.
     */
    private Optional<String> findAnswer(String question){
        return questionRepository.findAnswerFor(StringTransformationUtils.convertToLowerCaseAndRemoveAllWhiteSpacesAndQuestionMark(question));
    }


}
