package com.javarush.kostenko.quest.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {

    private Question question;
    private Question.Option option1;
    private Question.Option option2;

    @BeforeEach
    void setUp() {
        question = new Question();
        option1 = new Question.Option();
        option2 = new Question.Option();

        question.setId(1);
        question.setStep("Step 1");
        question.setQuestion("This is a question");
        Map<String, Question.Option> options = new HashMap<>();
        options.put("a", option1);
        options.put("b", option2);
        question.setOptions(options);
    }

    @Test
    void testGetters() {
        assertEquals(1, question.getId());
        assertEquals("Step 1", question.getStep());
        assertEquals("This is a question", question.getQuestion());
        Map<String, Question.Option> expectedOptions = new HashMap<>();
        expectedOptions.put("a", option1);
        expectedOptions.put("b", option2);
        assertEquals(expectedOptions, question.getOptions());
    }

    @Test
    void testSetters() {
        question.setId(2);
        question.setStep("Step 2");
        question.setQuestion("Another question");
        Map<String, Question.Option> newOptions = new HashMap<>();
        newOptions.put("c", option1);
        question.setOptions(newOptions);

        assertEquals(2, question.getId());
        assertEquals("Step 2", question.getStep());
        assertEquals("Another question", question.getQuestion());
        assertEquals(newOptions, question.getOptions());
    }

    @Test
    void testOptionGetters() {
        option1.setText("Option 1");
        option1.setOutcome("Outcome 1");
        option1.setNextStep(2);

        assertEquals("Option 1", option1.getText());
        assertEquals("Outcome 1", option1.getOutcome());
        assertEquals(2, option1.getNextStep());
    }

    @Test
    void testOptionSetters() {
        option1.setText("Option 1");
        option1.setOutcome("Outcome 1");
        option1.setNextStep(2);

        option1.setText("New Option 1");
        option1.setOutcome("New Outcome 1");
        option1.setNextStep(3);

        assertEquals("New Option 1", option1.getText());
        assertEquals("New Outcome 1", option1.getOutcome());
        assertEquals(3, option1.getNextStep());
    }

    @Test
    void testToString() {
        Map<String, Question.Option> options = new HashMap<>();
        options.put("a", option1);
        options.put("b", option2);
        question.setOptions(options);

        String expected = "Question(id=1, step=Step 1, question=This is a question, " +
                "options={a=Question.Option(text=null, outcome=null, nextStep=null), " +
                "b=Question.Option(text=null, outcome=null, nextStep=null)})";
        assertEquals(expected, question.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        Question anotherQuestion = new Question();
        anotherQuestion.setId(1);
        anotherQuestion.setStep("Step 1");
        anotherQuestion.setQuestion("This is a question");
        Map<String, Question.Option> options = new HashMap<>();
        options.put("a", option1);
        options.put("b", option2);
        anotherQuestion.setOptions(options);

        assertEquals(question, anotherQuestion);
        assertEquals(question.hashCode(), anotherQuestion.hashCode());
    }
}
