package com.javarush.kostenko.quest.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.kostenko.quest.model.Question;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class QuestionRepositoryTest {

    @Mock
    private ObjectMapper objectMapper;

    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() throws IOException {
        List<Question> mockQuestions = getMockQuestions();

        lenient().when(objectMapper.readValue(any(InputStream.class),
                        any(new TypeReference<List<Question>>() {}.getClass())))
                .thenReturn(mockQuestions);

        // Initializing questionRepository
        questionRepository = new QuestionRepository("questions.json");
    }

    private @NotNull List<Question> getMockQuestions() {
        List<Question> mockQuestions = new ArrayList<>();
        Question question1 = new Question();
        question1.setId(1);
        question1.setStep("Step 1: Mystical Glow");
        question1.setQuestion("You find a mysterious glow at your workplace that turns out to be a portal. " +
                "What will you do?");
        mockQuestions.add(question1);

        Question question2 = new Question();
        question2.setId(2);
        question2.setStep("Step 2: Enchanted Forest");
        question2.setQuestion("After passing through the portal, you find yourself in an enchanted forest with " +
                "bioluminescent plants. What will you do?");
        mockQuestions.add(question2);

        // Add remaining mock questions to match the content in the json
        for (int i = 3; i <= 8; i++) {
            Question question = new Question();
            question.setId(i);
            question.setStep("Step " + i);
            question.setQuestion("Mock Question " + i);
            mockQuestions.add(question);
        }

        return mockQuestions;
    }

    @Test
    void testQuestionRepositoryInitialization() {
        assertNotNull(questionRepository.getQuestions());
        assertEquals(8, questionRepository.getQuestions().size()); // expect 8 questions from mock data
    }

    @Test
    void testGetQuestionById() {
        Question question1 = questionRepository.getQuestionById(1);
        assertNotNull(question1);
        assertEquals(1, question1.getId());
        assertEquals("Step 1: Mystical Glow", question1.getStep());

        Question question2 = questionRepository.getQuestionById(2);
        assertNotNull(question2);
        assertEquals(2, question2.getId());
        assertEquals("Step 2: Enchanted Forest", question2.getStep());

        Question questionNull = questionRepository.getQuestionById(999);
        assertNull(questionNull);
    }

    @Test
    void testFileNotFound() {
        IOException exception = assertThrows(IOException.class, () ->
                new QuestionRepository("non_existing_file.json")
        );
        assertEquals("File not found: non_existing_file.json", exception.getMessage());
    }
}
