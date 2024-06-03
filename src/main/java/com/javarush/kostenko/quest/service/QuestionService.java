package com.javarush.kostenko.quest.service;

import com.javarush.kostenko.quest.model.Question;
import com.javarush.kostenko.quest.repository.QuestionRepository;

import java.io.IOException;
import java.util.List;

public class QuestionService {
    private QuestionRepository questionRepository;

    public QuestionService(String filePath) throws IOException {
        this.questionRepository = new QuestionRepository(filePath);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.getQuestions();
    }

    public Question getQuestionById(int id) {
        return questionRepository.getQuestionById(id);
    }
}
