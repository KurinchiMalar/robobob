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

@Component
@Order(1) // Highest priority
public class PredefinedQAStrategy implements QAStrategy {

    private static final Logger logger = LoggerFactory.getLogger(PredefinedQAStrategy.class);
    private final QuestionRepository questionRepository;

    @Autowired
    public PredefinedQAStrategy(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public boolean isAnswerable(String question) {
        boolean isAnswerable = findAnswer(question).isPresent();
        logger.debug("isAnswerable '{}': {}",question,isAnswerable);
        return isAnswerable;
    }

    @Override
    public String answer(String question) {
        return findAnswer(question)
                .orElse("I do not have answer");
    }

    private Optional<String> findAnswer(String question){
        return questionRepository.findAnswerFor(StringTransformationUtils.convertToLowerCaseAndRemoveAllWhiteSpacesAndQuestionMark(question));
    }


}
