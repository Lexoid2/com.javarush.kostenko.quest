package com.javarush.kostenko.quest.model;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class QuestionTest {

    @Test
    public void testQuestionProperties() {
        Question question = new Question();
        question.setId(1);
        question.setStep("Step1");
        question.setQuestion("What is your favorite color?");

        Map<String, Question.Option> options = new HashMap<>();
        Question.Option option1 = new Question.Option();
        option1.setText("Red");
        option1.setOutcome("Outcome1");
        option1.setNextStep(2);
        options.put("Red", option1);

        question.setOptions(options);

        assertEquals(1, question.getId());
        assertEquals("Step1", question.getStep());
        assertEquals("What is your favorite color?", question.getQuestion());
        assertNotNull(question.getOptions());
        assertEquals(option1, question.getOptions().get("Red"));
    }

    @Test
    public void testOptionProperties() {
        Question.Option option = new Question.Option();
        option.setText("Blue");
        option.setOutcome("Outcome2");
        option.setNextStep(3);

        assertEquals("Blue", option.getText());
        assertEquals("Outcome2", option.getOutcome());
        assertEquals(3, option.getNextStep());
    }
}