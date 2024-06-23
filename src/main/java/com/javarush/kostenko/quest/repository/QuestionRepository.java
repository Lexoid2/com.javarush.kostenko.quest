package com.javarush.kostenko.quest.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.kostenko.quest.model.Question;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Getter
@Slf4j
public class QuestionRepository {
    private final List<Question> questions;

    public QuestionRepository(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                log.error("File not found: {}", filePath);
                throw new IOException("File not found: " + filePath);
            }
            questions = mapper.readValue(inputStream, new TypeReference<List<Question>>() {});
            log.info("Loaded {} questions from file: {}", questions.size(), filePath);
        } catch (IOException e) {
            log.error("Failed to load questions from file: {}", filePath, e);
            throw e;
        }
    }

    public Question getQuestionById(int id) {
        log.info("Fetching question with id: {}", id);
        Question question = questions.stream().filter(q -> q.getId() == id).findFirst().orElse(null);
        if (question == null) {
            log.warn("No question found for id: {}", id);
        }
        return question;
    }
}
