package com.javarush.kostenko.quest.service;

import com.javarush.kostenko.quest.model.Question;
import com.javarush.kostenko.quest.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(String filePath) throws IOException {
        try {
            this.questionRepository = new QuestionRepository(filePath);
            log.info("QuestionRepository initialized with file: {}", filePath);
        } catch (IOException e) {
            log.error("Failed to initialize QuestionRepository with file: {}", filePath, e);
            throw e;
        }
    }

    public Question getQuestionById(int id) {
        log.info("Fetching question with id: {}", id);
        Question question = questionRepository.getQuestionById(id);
        if (question == null) {
            log.warn("No question found for id: {}", id);
        }
        return question;
    }
}
