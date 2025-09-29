package com.quizbot.session;

import com.quizbot.model.Question;
import com.quizbot.model.QuestionSet;

public class StudySession implements StateSession {

    private QuestionSet questionSet;
    private Question current;
    private State state = State.INIT;

    public StudySession(QuestionSet set) {
        this.questionSet = set;
    }

    @Override
    public Question action() {
        current = questionSet.getRandomQuestion();
        state = State.ACTION;
        return current;
    }

    @Override
    public boolean check(String answer) {
        // В study мы не проверяем ответ, просто показываем правильный
        state = State.ACTION;
        return true;
    }

    // Возвращаем правильный ответ текущего вопроса
    public String getFeedback() {
        if (current != null) {
            return "Правильный ответ: " + String.join(", ", current.getCorrectAnswers());
        }
        return "";
    }

    @Override
    public String end() {
        return "Сессия завершена!";
    }

    @Override
    public State getState() {
        return state;
    }
}


