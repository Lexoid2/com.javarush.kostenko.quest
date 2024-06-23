package com.javarush.kostenko.quest.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.kostenko.quest.model.Question;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
class QuestionRepositoryTest {

    @Mock
    private ObjectMapper objectMapper;

    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() throws IOException {
        log.debug("Setting up mocks for QuestionRepositoryTest");
        List<Question> mockQuestions = getMockQuestions();

        lenient().when(objectMapper.readValue(any(InputStream.class),
                        any(new TypeReference<List<Question>>() {}.getClass())))
                .thenReturn(mockQuestions);

        // Initializing questionRepository
        questionRepository = new QuestionRepository("questions.json");
        log.debug("QuestionRepository initialized with mock data");
    }

    private @NotNull List<Question> getMockQuestions() {
        log.debug("Creating mock questions for tests");
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

        log.debug("Mock questions created: {}", mockQuestions);
        return mockQuestions;
    }

    @Test
    void testQuestionRepositoryInitialization() {
        log.debug("Testing QuestionRepository initialization");
        assertNotNull(questionRepository.getQuestions());
        assertEquals(8, questionRepository.getQuestions().size()); // expect 8 questions from mock data
        log.debug("QuestionRepository initialization test passed");
    }

    @Test
    void testGetQuestionById() {
        log.debug("Testing getQuestionById with ID 1");
        Question question1 = questionRepository.getQuestionById(1);
        assertNotNull(question1);
        assertEquals(1, question1.getId());
        assertEquals("Step 1: Mystical Glow", question1.getStep());

        log.debug("Testing getQuestionById with ID 2");
        Question question2 = questionRepository.getQuestionById(2);
        assertNotNull(question2);
        assertEquals(2, question2.getId());
        assertEquals("Step 2: Enchanted Forest", question2.getStep());

        log.debug("Testing getQuestionById with non-existing ID 999");
        Question questionNull = questionRepository.getQuestionById(999);
        assertNull(questionNull);

        log.debug("getQuestionById tests completed successfully");
    }

    @Test
    void testFileNotFound() {
        log.debug("Testing initialization with non-existing file");
        IOException exception = assertThrows(IOException.class, () ->
                new QuestionRepository("non_existing_file.json")
        );
        assertEquals("File not found: non_existing_file.json", exception.getMessage());
        log.debug("FileNotFound test passed");
    }
}
