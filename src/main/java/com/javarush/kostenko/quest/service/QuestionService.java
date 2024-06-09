package com.javarush.kostenko.quest.service;

import com.javarush.kostenko.quest.model.Question;
import com.javarush.kostenko.quest.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class QuestionService {
    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);
    private final QuestionRepository questionRepository;

    public QuestionService(String filePath) throws IOException {
        try {
            this.questionRepository = new QuestionRepository(filePath);
            logger.info("QuestionRepository initialized with file: {}", filePath);
        } catch (IOException e) {
            logger.error("Failed to initialize QuestionRepository with file: {}", filePath, e);
            throw e;
        }
    }

    public Question getQuestionById(int id) {
        logger.info("Fetching question with id: {}", id);
        Question question = questionRepository.getQuestionById(id);
        if (question == null) {
            logger.warn("No question found for id: {}", id);
        }
        return question;
    }
}
