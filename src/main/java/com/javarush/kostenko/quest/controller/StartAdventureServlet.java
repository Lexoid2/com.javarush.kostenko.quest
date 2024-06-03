package com.javarush.kostenko.quest.controller;

import com.javarush.kostenko.quest.service.QuestionService;
import com.javarush.kostenko.quest.model.Question;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/start-adventure")
public class StartAdventureServlet extends HttpServlet {
    private QuestionService questionService;

    @Override
    public void init() throws ServletException {
        try {
            String questionsFilePath = "questions.json";
            questionService = new QuestionService(questionsFilePath);
        } catch (IOException e) {
            throw new ServletException("Failed to load questions", e);
        }
    }

    @Override
    protected void doPost(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String option = request.getParameter("option");
        String stepIdParam = request.getParameter("stepId");

        if (stepIdParam == null || stepIdParam.isEmpty()) {
            throw new ServletException("Missing or empty parameter: stepId");
        }

        int stepId;
        try {
            stepId = Integer.parseInt(stepIdParam);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid stepId parameter", e);
        }

        Question question = questionService.getQuestionById(stepId);
        if (question == null) {
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

        if (selectedOption.getNextStep() == null || selectedOption.getNextStep() == -1) {
            request.getRequestDispatcher("/game-over.jsp").forward(request, response);
        } else if (selectedOption.getNextStep() == 0) {
            request.getRequestDispatcher("/congratulations.jsp").forward(request, response);
        } else {
            Question nextQuestion = questionService.getQuestionById(selectedOption.getNextStep());
            if (nextQuestion == null) {
                throw new ServletException("No question found for stepId: " + selectedOption.getNextStep());
            }
            forwardToQuestion(request, response, name, nextQuestion);
        }
    }

    private void forwardToQuestion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                   String name, Question question) throws ServletException, IOException {
        request.setAttribute("name", name);
        request.setAttribute("question", question);
        request.setAttribute("stepId", question.getId());
        request.getRequestDispatcher("/question.jsp").forward(request, response);
    }
}
