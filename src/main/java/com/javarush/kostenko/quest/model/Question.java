package com.javarush.kostenko.quest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private int id;
    private String step;
    private String question;
    private Map<String, Option> options;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Option {
        private String text;
        private String outcome;
        private Integer nextStep;
    }
}
