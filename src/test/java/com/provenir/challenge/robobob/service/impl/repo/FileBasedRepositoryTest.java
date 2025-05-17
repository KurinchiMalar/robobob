package com.provenir.challenge.robobob.service.impl.repo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;


@ExtendWith(MockitoExtension.class)
public class FileBasedRepositoryTest {

    private FileBasedRepository repository;

    private Map<String,String> qatestBedMap;

    @BeforeEach
    public void setUp() throws IOException {

        qatestBedMap = new HashMap<>();
        qatestBedMap.put("what is your name", "My name is RoboBob!");
        qatestBedMap.put("who created you", "I was created by the Malar.");
        qatestBedMap.put("what can you do", "I can answer questions, solve arithmetic problems");
        qatestBedMap.put("hello","Hello! How can I help you today?");

        //create a repository object
        FileBasedRepository repo = new FileBasedRepository("test-questions.json");
        repository = Mockito.spy(repo);

        doNothing().when(repository).readFromFileAndLoadQuestions();
        repository.init();

        ReflectionTestUtils.setField(repository,"questionAnswersMap",qatestBedMap);

    }

    @Test
    @DisplayName("Should find answer for available question with exact match")
    public void testFindAnswerFor_AvailableQuestion_ReturnsAnswer(){

        Optional<String> answer = repository.findAnswerFor("What is your name");

        assertTrue(answer.isPresent());
        assertEquals("My name is RoboBob!", answer.get());
    }

    @Test
    @DisplayName("Should find answer for available question with different casing with exact match")
    public void testFindAnswerFor_AvailableQuestionDifferentCasing_ReturnsAnswer(){

        Optional<String> answer = repository.findAnswerFor("WHat IS yOur naMe");

        assertTrue(answer.isPresent());
        assertEquals("My name is RoboBob!", answer.get());
    }

    @Test
    @DisplayName("Should find answer for available question with additional whitespace with exact match")
    public void testFindAnswerFor_AvailableQuestionWithWhiteSpace_ReturnsAnswer(){

        Optional<String> answer = repository.findAnswerFor("    WHat IS yOur naMe    ");

        assertTrue(answer.isPresent());
        assertEquals("My name is RoboBob!", answer.get());
    }

    @Test
    @DisplayName("Should return empty optional for unknown question ")
    public void testFindAnswerFor_UnAvailableQuestion_ReturnsEmptyOptional(){

        Optional<String> answer = repository.findAnswerFor("I am an unknown question");

        assertFalse(answer.isPresent());
    }

    @Test
    @DisplayName("Should return unmodifiable map of all questions and answers")
    public void testGetAllQuestionsAndAnswers_ReturnsUnModifableMap(){

        Map<String,String> result = repository.getAllQuestionsAndAnswers();

        assertEquals(qatestBedMap.size(),result.size());
        assertThrows(UnsupportedOperationException.class, () -> result.put("testQuestion","testAns"));

    }

}
