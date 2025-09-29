package com.quizbot.sender;

import com.quizbot.model.Question;
import com.quizbot.session.LearnSession;
import com.quizbot.session.StateSession;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class LearnSender extends Sender {

    public LearnSender(long chatId, LearnSession session) {
        super(chatId);
        this.stateSession = session;
    }

    @Override
    public void onMessageReceived(String message) {
        if (stateSession.getState() == StateSession.State.ACTION) {
            stateSession.check(message);
        }
    }

    @Override
    public SendMessage createSendMessage() {
        SendMessage msg = new SendMessage();
        msg.setChatId(String.valueOf(chatId));

        LearnSession session = (LearnSession) stateSession;

        // Выводим обратную связь по предыдущему ответу
        String feedback = session.getFeedback();

        // Задаём следующий вопрос
        Question q = session.action();
        if (q != null) {
            String text = feedback + (feedback.isEmpty() ? "" : "\n\n") +
                    "Вопрос: " + q.getQuestion();
            msg.setText(text);
        } else {
            msg.setText(feedback + "\nСессия завершена!");
        }

        return msg;
    }
}
