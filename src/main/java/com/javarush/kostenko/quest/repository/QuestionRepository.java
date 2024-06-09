package com.javarush.kostenko.quest.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.kostenko.quest.model.Question;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Getter
public class QuestionRepository {
    private static final Logger logger = LoggerFactory.getLogger(QuestionRepository.class);
    private final List<Question> questions;

    public QuestionRepository(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                logger.error("File not found: {}", filePath);
                throw new IOException("File not found: " + filePath);
            }
            questions = mapper.readValue(inputStream, new TypeReference<List<Question>>() {});
            logger.info("Loaded {} questions from file: {}", questions.size(), filePath);
        } catch (IOException e) {
            logger.error("Failed to load questions from file: {}", filePath, e);
            throw e;
        }
    }

    public Question getQuestionById(int id) {
        logger.info("Fetching question with id: {}", id);
        Question question = questions.stream().filter(q -> q.getId() == id).findFirst().orElse(null);
        if (question == null) {
            logger.warn("No question found for id: {}", id);
        }
        return question;
    }
}
