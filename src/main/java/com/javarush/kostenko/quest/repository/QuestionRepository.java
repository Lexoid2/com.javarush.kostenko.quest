package com.javarush.kostenko.quest.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.kostenko.quest.model.Question;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Getter
public class QuestionRepository {
    private final List<Question> questions;

    public QuestionRepository(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + filePath);
            }
            questions = mapper.readValue(inputStream, new TypeReference<List<Question>>() {});
        }
    }

    public Question getQuestionById(int id) {
        return questions.stream().filter(q -> q.getId() == id).findFirst().orElse(null);
    }
}
