package com.provenir.challenge.robobob.service.strategy;

public interface QAStrategy {

    boolean isAnswerable(String question);

    String answer(String question);
}
