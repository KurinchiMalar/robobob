package com.provenir.challenge.robobob.service.strategy.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FallbackQAStrategyTest {

    private FallbackQAStrategy fallbackQAStrategy;

    @BeforeEach
    public void setup(){
        fallbackQAStrategy = new FallbackQAStrategy();
    }

    @Test
    @DisplayName("Should always be answerable as it is fallback strategy")
    public void testIsAnswerable_ReturnsTrue(){
        String question = "Random Question";

        assertTrue(fallbackQAStrategy.isAnswerable(question));
    }

    @Test
    @DisplayName("Should give same answer when same question is asked multiple times")
    public void testAnswer_SameQuestion_ReturnsSameAnswer(){

        String question = "What can you do?";
        String sameQuestionWithDifferentCasing = "wHaT Can YoU Do?";

        String ans1 = fallbackQAStrategy.answer(question);
        String ans2 = fallbackQAStrategy.answer(question);
        String ans3 = fallbackQAStrategy.answer(sameQuestionWithDifferentCasing);

        assertNotNull(ans1);
        assertNotNull(ans2);
        assertNotNull(ans3);
        assertEquals(ans1,ans2);
        assertEquals(ans1,ans3);
    }

    @Test
    @DisplayName("Should give different answer when different questions are asked")
    public void testAnswer_DifferentQuestion_ReturnsDifferentAnswer(){

        String question1 = "How are you?";
        String question2 = "Who created you?";

        String ans1 = fallbackQAStrategy.answer(question1);
        String ans2 = fallbackQAStrategy.answer(question2);
        assertNotNull(ans1);
        assertNotNull(ans2);
        assertNotEquals(ans1,ans2);
    }

}
