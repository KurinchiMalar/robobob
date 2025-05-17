package com.provenir.challenge.robobob.service.impl;

import com.provenir.challenge.robobob.service.core.QuestionService;
import com.provenir.challenge.robobob.service.strategy.QAStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Default implementation of QuestionService
 * which uses Chain of Responsibility pattern to choose between available strategies to answer questions.
 * Implements caching of question and corresponding answers to improve performance of application.
 * @author KurinchiMalar
 */
@Service
public class DefaultQuestionService implements QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultQuestionService.class);
    private final List<QAStrategy> qaStrategies;

    /**
     * Construct DefaultQuestionService instance with all the available QA strategies.
     * @param qaStrategies
     */
    @Autowired
    public DefaultQuestionService(List<QAStrategy> qaStrategies) {
        this.qaStrategies = qaStrategies;
        logger.info("Initialized with {} strategies",qaStrategies.size());
    }

    /**
     * Implements Chain of Responsibility and delegates to appropriate QAStrategy that can provide answer.
     * Results are cached to improve performance.
     *
     * @param question The question to answer
     * @return String The answer to given question.
     * @throws  IllegalArgumentException if the question is null or empty
     */
    @Override
    @Cacheable(value = "questionCache", key ="#question", unless = "#result == null")
    public String answerQuestion(String question) {

        logger.info("Cache miss for question : {} ",question);

        if(question == null || question.trim().isEmpty()){
            throw new IllegalArgumentException("Question cannot be empty");
        }

        // Iterate to figure out the strategy that can answer
        // Chain of Responsibility
        for(QAStrategy qaStrategy: qaStrategies){
            if(qaStrategy.isAnswerable(question)){
                logger.debug("Using strategy: {}",qaStrategy.getClass().getSimpleName());
                return qaStrategy.answer(question);
            }
        }

        logger.warn("No strategy match for question: {}",question);
        return "Sorry! I am unable to answer that question";
    }
}
