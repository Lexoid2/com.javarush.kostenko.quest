package com.javarush.kostenko.quest.controller;

import com.javarush.kostenko.quest.model.Question;
import com.javarush.kostenko.quest.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class StartAdventureServletTest {

    private static final Logger logger = LoggerFactory.getLogger(StartAdventureServletTest.class);

    private QuestionService questionService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher requestDispatcher;
    private StartAdventureServlet startAdventureServlet;

    @BeforeEach
    void setUp() throws Exception {
        // Initializing mocks
        questionService = mock(QuestionService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        requestDispatcher = mock(RequestDispatcher.class);

        startAdventureServlet = new StartAdventureServlet();

        // Using reflection to set the private field questionService
        Field questionServiceField = StartAdventureServlet.class.getDeclaredField("questionService");
        questionServiceField.setAccessible(true);
        questionServiceField.set(startAdventureServlet, questionService);

        Mockito.lenient().when(request.getSession()).thenReturn(session);
        Mockito.lenient().when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    void testDoPost_ValidScenario() throws ServletException, IOException {
        Mockito.lenient().when(request.getParameter("name")).thenReturn("TestPlayer");
        Mockito.lenient().when(request.getParameter("stepId")).thenReturn("1");
        Mockito.lenient().when(request.getParameter("option")).thenReturn("a");

        Question question = new Question();
        question.setId(1);
        Question.Option option = new Question.Option();
        option.setNextStep(2);
        option.setOutcome("Outcome");
        Map<String, Question.Option> options = new HashMap<>();
        options.put("a", option);
        question.setOptions(options);
        Mockito.lenient().when(questionService.getQuestionById(1)).thenReturn(question);

        logger.debug("Executing testDoPost_ValidScenario with name: TestPlayer, stepId: 1, option: a");

        startAdventureServlet.doPost(request, response);

        verify(session).setAttribute("playerName", "TestPlayer");
        verify(request).setAttribute("outcome", "Outcome");
        verify(request).setAttribute("name", "TestPlayer");
        verify(request).setAttribute("nextStep", "2");
        verify(requestDispatcher).forward(request, response);

        logger.debug("Completed testDoPost_ValidScenario successfully");
    }

    @Test
    void testDoPost_NoOptionSelected() throws ServletException, IOException {
        Mockito.lenient().when(request.getParameter("name")).thenReturn("TestPlayer");
        Mockito.lenient().when(request.getParameter("stepId")).thenReturn("1");

        Question question = new Question();
        question.setId(1);
        question.setOptions(new HashMap<>());
        Mockito.lenient().when(questionService.getQuestionById(1)).thenReturn(question);

        logger.debug("Executing testDoPost_NoOptionSelected with name: TestPlayer, stepId: 1");

        startAdventureServlet.doPost(request, response);

        verify(request).setAttribute("errorMessage", "Please select an option.");
        verify(requestDispatcher).forward(request, response);

        logger.debug("Completed testDoPost_NoOptionSelected successfully");
    }

    @Test
    void testDoPost_MissingName() throws ServletException, IOException {
        Mockito.lenient().when(request.getParameter("name")).thenReturn(null);
        Mockito.lenient().when(session.getAttribute("playerName")).thenReturn(null);

        logger.debug("Executing testDoPost_MissingName with no name parameter");

        startAdventureServlet.doPost(request, response);

        verify(response).sendRedirect("prologue.jsp");

        logger.debug("Completed testDoPost_MissingName successfully");
    }
}
