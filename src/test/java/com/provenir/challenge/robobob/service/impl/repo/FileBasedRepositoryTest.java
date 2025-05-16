package com.provenir.challenge.robobob.service.impl.repo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileBasedRepositoryTest {

    private FileBasedRepository repository;

    @Mock
    private ClassPathResource mockResource;

    @Mock
    private ObjectMapper mockObjectMapper;

    private Map<String,String> qatestBedMap;

    @BeforeEach
    public void setUp() throws IOException {

        qatestBedMap = new HashMap<>();
        qatestBedMap.put("what is your name", "My name is RoboBob!");
        qatestBedMap.put("who created you", "I was created by the Malar.");
        qatestBedMap.put("what can you do", "I can answer questions, solve arithmetic problems");
        qatestBedMap.put("hello","Hello! How can I help you today?");

        //create a repository object
        repository = new FileBasedRepository("test-questions.json");
        ReflectionTestUtils.setField(repository,"objectMapper",mockObjectMapper);

        String jsonContent = "{\"what is your name\":\"My name is RoboBob!\",\"who created you\":\"I was created by the Malar.\"}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(jsonContent.getBytes());

        // mock to return the prepared qatestBedMap with question and answers.
        when(mockResource.getInputStream()).thenReturn(inputStream);
        when(mockObjectMapper.readValue(any(ByteArrayInputStream.class),any(TypeReference.class))).thenReturn(qatestBedMap);

        ReflectionTestUtils.setField(repository,"questionAnswersMap",qatestBedMap);

    }

    @Test
    @DisplayName("Should find answer for available question with exact match")
    public void testFindAnswerFor_AvailableQuestion_ReturnsAnswer(){

        Optional<String> answer = repository.findAnswerFor("What is your name");
    }

}
