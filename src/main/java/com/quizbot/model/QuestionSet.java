package com.quizbot.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionSet {
    private List<Question> questions;

    public QuestionSet() {
        this.questions = new ArrayList<>();
    }

    public void loadFromFile(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Question[] qs = mapper.readValue(new File(path), Question[].class);
        Collections.addAll(this.questions, qs);
    }

    public Question getRandomQuestion() {
        if (questions.isEmpty()) return null;
        int index = (int) (Math.random() * questions.size());
        return questions.get(index);
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

