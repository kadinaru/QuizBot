package com.quizbot.session;

import com.quizbot.model.Question;
import com.quizbot.model.QuestionSet;

public class LearnSession implements StateSession {

    private QuestionSet questionSet;
    private Question current;
    private State state = State.INIT;
    private boolean lastAnswerCorrect = true;

    public LearnSession(QuestionSet set) {
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
        if (current == null) return false;

        lastAnswerCorrect = current.isCorrect(answer);
        state = State.ACTION; // остаёмся в действии
        return lastAnswerCorrect;
    }

    // Возвращает сообщение о предыдущем ответе
    public String getFeedback() {
        if (current != null) {
            return lastAnswerCorrect ?
                    "Правильно!" :
                    "Неправильно! Правильный ответ: " + String.join(", ", current.getCorrectAnswers());
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
