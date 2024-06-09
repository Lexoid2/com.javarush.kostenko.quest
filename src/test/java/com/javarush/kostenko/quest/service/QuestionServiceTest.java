package com.javarush.kostenko.quest.service;

import com.javarush.kostenko.quest.model.Question;
import com.javarush.kostenko.quest.repository.QuestionRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceTest.class);

    @Mock
    private QuestionRepository questionRepository;

    private QuestionService questionService;

    @BeforeEach
    void setUp() throws IOException, NoSuchFieldException, IllegalAccessException {
        logger.debug("Setting up mocks for QuestionServiceTest");
        List<Question> mockQuestions = getMockQuestions();

        // Mocking the QuestionRepository to return our mock questions
        lenient().when(questionRepository.getQuestionById(anyInt()))
                .thenAnswer(invocation -> {
                    int id = invocation.getArgument(0);
                    return mockQuestions.stream()
                            .filter(question -> question.getId() == id)
                            .findFirst()
                            .orElse(null);
                });

        // We create a real QuestionService instance and replace it with questionRepository
        questionService = new QuestionService("questions.json");
        setInternalState(questionService, questionRepository);
        logger.debug("QuestionService initialized with mock data");
    }

    // Setting internal state using Reflection API
    private void setInternalState(@NotNull Object targetObject, Object fieldValue)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = targetObject.getClass().getDeclaredField("questionRepository");
        field.setAccessible(true);
        field.set(targetObject, fieldValue);
    }

    private @NotNull List<Question> getMockQuestions() {
        logger.debug("Creating mock questions for tests");
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

        // Adding the remaining mock questions to match the content in json
        for (int i = 3; i <= 8; i++) {
            Question question = new Question();
            question.setId(i);
            question.setStep("Step " + i);
            question.setQuestion("Mock Question " + i);
            mockQuestions.add(question);
        }

        logger.debug("Mock questions created: {}", mockQuestions);
        return mockQuestions;
    }

    @Test
    void testGetQuestionByIdExists() {
        logger.debug("Testing getQuestionById with ID 1");
        Question question1 = questionService.getQuestionById(1);
        assertNotNull(question1);
        assertEquals(1, question1.getId());
        assertEquals("Step 1: Mystical Glow", question1.getStep());

        logger.debug("Testing getQuestionById with ID 2");
        Question question2 = questionService.getQuestionById(2);
        assertNotNull(question2);
        assertEquals(2, question2.getId());
        assertEquals("Step 2: Enchanted Forest", question2.getStep());

        verify(questionRepository, times(1)).getQuestionById(1);
        verify(questionRepository, times(1)).getQuestionById(2);

        logger.debug("Completed getQuestionById tests successfully");
    }

    @Test
    void testGetQuestionByIdNotExists() {
        logger.debug("Testing getQuestionById with non-existing ID 999");
        Question questionNull = questionService.getQuestionById(999);
        assertNull(questionNull);

        verify(questionRepository, times(1)).getQuestionById(999);

        logger.debug("Completed testGetQuestionByIdNotExists successfully");
    }

    @Test
    void testQuestionServiceInitialization() throws IOException {
        logger.debug("Testing QuestionService initialization");
        QuestionService service = new QuestionService("questions.json");
        assertNotNull(service);
        logger.debug("QuestionService initialization test passed");
    }

    @Test
    void testQuestionServiceInitializationIOException() {
        logger.debug("Testing QuestionService initialization with non-existing file");
        IOException exception = assertThrows(IOException.class, () ->
                new QuestionService("nonexistent_file.json")
        );
        assertEquals("File not found: nonexistent_file.json", exception.getMessage());
        logger.debug("FileNotFound test passed");
    }
}
