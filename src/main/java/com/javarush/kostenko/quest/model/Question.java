package com.javarush.kostenko.quest.model;

import lombok.Data;

import java.util.Map;

@Data
public class Question {
    private int id;
    private String step;
    private String question;
    private Map<String, Option> options;

    @Data
    public static class Option {
        private String text;
        private String outcome;
        private Integer nextStep;
    }
}
