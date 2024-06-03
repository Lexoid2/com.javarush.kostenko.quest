package com.javarush.kostenko.quest.service;

import com.javarush.kostenko.quest.model.Question;
import com.javarush.kostenko.quest.repository.QuestionRepository;

import java.io.IOException;

public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(String filePath) throws IOException {
        this.questionRepository = new QuestionRepository(filePath);
    }

    public Question getQuestionById(int id) {
        return questionRepository.getQuestionById(id);
    }
}
