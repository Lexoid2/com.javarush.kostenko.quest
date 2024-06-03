package com.javarush.kostenko.quest.repository;

import com.javarush.kostenko.quest.model.Question;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class QuestionRepository {
    @Getter
    private List<Question> questions;

    public QuestionRepository(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        questions = mapper.readValue(Files.readAllBytes(Paths.get(filePath)), new TypeReference<List<Question>>() {});
    }

    public Question getQuestionById(int id) {
        return questions.stream().filter(q -> q.getId() == id).findFirst().orElse(null);
    }
}
