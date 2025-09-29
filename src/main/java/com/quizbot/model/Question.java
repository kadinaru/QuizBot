package com.quizbot.model;

import java.util.List;

public class Question {
    private String question;
    private List<String> correctAnswers;
    private List<String> wrongAnswers;

    public Question() {}

    public Question(String question, List<String> correctAnswers, List<String> wrongAnswers) {
        this.question = question;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public List<String> getWrongAnswers() {
        return wrongAnswers;
    }

    public boolean isCorrect(String answer) {
        return correctAnswers.contains(answer);
    }
}
