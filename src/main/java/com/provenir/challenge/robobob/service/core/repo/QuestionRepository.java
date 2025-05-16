package com.provenir.challenge.robobob.service.core.repo;

import java.util.Map;
import java.util.Optional;

public interface QuestionRepository {

    Optional<String> findAnswerFor(String question);

    Map<String,String> getAllQuestionsAndAnswers();
}
