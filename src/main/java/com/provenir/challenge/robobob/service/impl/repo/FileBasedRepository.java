package com.provenir.challenge.robobob.service.impl.repo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.provenir.challenge.robobob.service.core.repo.QuestionRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository implementation that loads question and answers from a pre-configured JSON file
 * The purpose of this class is to provide ability to lookup for pre-configured questions and answers.
 * @author KurinchiMalar
 */
@Repository
public class FileBasedRepository implements QuestionRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileBasedRepository.class);

    private final String filename;
    private final ObjectMapper objectMapper;
    private Map<String,String> questionAnswersMap;

    /**
     * Constructs FileBasedRepository instance with the specified questions configuration file.
     *
     * @param filename Path to the JSON file containing the pre-configured question, answer pairs
     */
    public FileBasedRepository(@Value("${robobob.questions.file}") String filename) {
        this.filename = filename;
        this.objectMapper = new ObjectMapper();
        this.questionAnswersMap = new HashMap<>();
    }

    /**
     * Initializes the repository by loading questions from the configuration file.
     */
    @PostConstruct
    public void init(){
        try{
            readFromFileAndLoadQuestions();
            logger.info("Loaded {} question-answer pairs from {}",questionAnswersMap.size(),filename);

        } catch (IOException e) {
            logger.error("Failed to load questions from {}",filename,e);
            questionAnswersMap = new HashMap<>();
        }
    }

    /**
     * Finds an answer for the given question.
     * @param question The question to lookup.
     * @return Optional with the answer if found, empty otherwise.
     */
    @Override
    public Optional<String> findAnswerFor(String question) {
        String formattedQuestion = question.toLowerCase().trim();
        return Optional.ofNullable(questionAnswersMap.get(formattedQuestion));
    }

    /**
     * Gets all the question-answer pairs loaded in the repository.
     *
     * @return UnmodifiableMap of all question-answer pairs.
     */
    @Override
    public Map<String, String> getAllQuestionsAndAnswers() {
        return Collections.unmodifiableMap(questionAnswersMap);
    }

    /**
     * Reads questions from JSON file and loads them into memory. (internal map)
     *
     * @throws IOException if there is an error reading the file.
     */
    protected void readFromFileAndLoadQuestions() throws IOException {

        try(InputStream inputStream = new ClassPathResource(filename).getInputStream()){

            // TypeReference to retain the actual data type.
            Map<String,String> rawQuestionsMapFromFile = objectMapper.readValue(inputStream,
                    new TypeReference<Map<String, String>>() {});

            // Formatting added for consistency.
            questionAnswersMap = formatMap(rawQuestionsMapFromFile);
        }
    }

    /**
     * Utility method that formats the keys of the question-answer map to lowercase and trims whitespace.
     *
     * @param inputMap The input question-answer map without any formatting.
     * @return Formatted map keys converted to lowercase and trimmed whitespace.
     */
    private Map<String,String> formatMap(Map<String,String> inputMap){
        return inputMap.entrySet().stream()
                .collect(Collectors.toMap(
                        e->e.getKey().toLowerCase().trim(),
                        Map.Entry::getValue
                ));
    }
}
