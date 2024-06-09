package com.javarush.kostenko.quest.controller;

import com.javarush.kostenko.quest.service.QuestionService;
import com.javarush.kostenko.quest.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/start-adventure")
public class StartAdventureServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(StartAdventureServlet.class);
    private QuestionService questionService;

    @Override
    public void init() throws ServletException {
        try {
            String questionsFilePath = "questions.json";
            questionService = new QuestionService(questionsFilePath);
            logger.info("QuestionService initialized with file: {}", questionsFilePath);
        } catch (IOException e) {
            logger.error("Failed to load questions", e);
            throw new ServletException("Failed to load questions", e);
        }
    }

    @Override
    protected void doPost(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Handling POST request");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String name = request.getParameter("name");

        if (name != null && !name.isEmpty()) {
            session.setAttribute("playerName", name);
            if (session.getAttribute("gameCount") == null) {
                session.setAttribute("gameCount", 0);
            }
            logger.info("Player name set to: {}", name);
        } else {
            name = (String) session.getAttribute("playerName");
            if (name == null || name.isEmpty()) {
                logger.warn("No player name found, redirecting to prologue.jsp");
                response.sendRedirect("prologue.jsp");
                return;
            }
        }

        String option = request.getParameter("option");
        String stepIdParam = request.getParameter("stepId");

        if (stepIdParam == null || stepIdParam.isEmpty()) {
            logger.error("Missing or empty parameter: stepId");
            throw new ServletException("Missing or empty parameter: stepId");
        }

        int stepId;
        try {
            stepId = Integer.parseInt(stepIdParam);
        } catch (NumberFormatException e) {
            logger.error("Invalid stepId parameter", e);
            throw new ServletException("Invalid stepId parameter", e);
        }

        Question question = questionService.getQuestionById(stepId);
        if (question == null) {
            logger.error("No question found for stepId: {}", stepId);
            throw new ServletException("No question found for stepId: " + stepId);
        }

        if (option == null) {
            request.setAttribute("errorMessage", "Please select an option.");
            forwardToQuestion(request, response, name, question);
            return;
        }

        Question.Option selectedOption = question.getOptions().get(option);
        if (selectedOption == null) {
            request.setAttribute("errorMessage", "Invalid option selected.");
            forwardToQuestion(request, response, name, question);
            return;
        }

        request.setAttribute("outcome", selectedOption.getOutcome());
        request.setAttribute("name", name);
        request.setAttribute("nextStep", String.valueOf(selectedOption.getNextStep()));
        logger.info("Option selected: {}, Outcome: {}", option, selectedOption.getOutcome());

        if (selectedOption.getNextStep() == null || selectedOption.getNextStep() == -1) {
            logger.info("Game over, forwarding to game-over.jsp");
            request.getRequestDispatcher("/game-over.jsp").forward(request, response);
        } else if (selectedOption.getNextStep() == 0) {
            logger.info("Game completed, forwarding to congratulations.jsp");
            request.getRequestDispatcher("/congratulations.jsp").forward(request, response);
        } else {
            logger.info("Proceeding to next step, forwarding to outcome.jsp");
            request.getRequestDispatcher("/outcome.jsp").forward(request, response);
        }
    }

    private void forwardToQuestion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                   String name, @NotNull Question question) throws ServletException, IOException {
        logger.info("Forwarding to question.jsp for stepId: {}", question.getId());
        request.setAttribute("name", name);
        request.setAttribute("question", question);
        request.setAttribute("stepId", question.getId());
        request.getRequestDispatcher("/question.jsp").forward(request, response);
    }
}
