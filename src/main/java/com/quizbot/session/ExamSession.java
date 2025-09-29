package com.quizbot.session;

import com.quizbot.model.Question;
import com.quizbot.model.QuestionSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExamSession implements StateSession {
    private QuestionSet questionSet;
    private Question current;
    private List<String> currentOptions; // варианты для текущего вопроса
    private int totalQuestions;
    private int answered = 0;
    private int correct = 0;
    private State state = State.INIT;

    public ExamSession(QuestionSet set, int totalQuestions) {
        this.questionSet = set;
        this.totalQuestions = totalQuestions;
    }

    @Override
    public Question action() {
        if (state == State.END) return null;

        current = questionSet.getRandomQuestion();

        // Формируем варианты: 3 правильных и 1 случайный неправильный
        currentOptions = new ArrayList<>();
        int correctCount = Math.min(3, current.getCorrectAnswers().size());
        currentOptions.addAll(current.getCorrectAnswers().subList(0, correctCount));

        if (!current.getWrongAnswers().isEmpty()) {
            int randIndex = (int) (Math.random() * current.getWrongAnswers().size());
            currentOptions.add(current.getWrongAnswers().get(randIndex));
        }

        Collections.shuffle(currentOptions); // перемешиваем варианты

        state = State.ACTION;
        return current;
    }

    @Override
    public boolean check(String answer) {
        if (current == null || currentOptions == null) return false;

        int index;
        try {
            index = Integer.parseInt(answer.trim()) - 1; // пользователь вводит 1..N
        } catch (NumberFormatException e) {
            return false;
        }

        if (index < 0 || index >= currentOptions.size()) return false;

        boolean result = current.getCorrectAnswers().contains(currentOptions.get(index));

        if (result) correct++;
        answered++;

        if (answered >= totalQuestions) state = State.END;
        else state = State.ACTION;

        return result;
    }

    @Override
    public String end() {
        return "Экзамен завершен: " + correct + "/" + totalQuestions;
    }

    @Override
    public State getState() {
        return state;
    }

    public List<String> getCurrentOptions() {
        return currentOptions;
    }
}
