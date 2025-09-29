package com.quizbot.sender;

import com.quizbot.model.Question;
import com.quizbot.session.StateSession;
import com.quizbot.session.StudySession;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class StudySender extends Sender {

    public StudySender(long chatId, StudySession session) {
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

        StudySession session = (StudySession) stateSession;

        // Показываем правильный ответ на предыдущий вопрос
        String feedback = session.getFeedback();

        // Задаём новый вопрос
        Question q = session.action();
        if (q != null) {
            String text = feedback.isEmpty() ? "" : feedback + "\n\n";
            text += "Вопрос: " + q.getQuestion();
            msg.setText(text);
        } else {
            msg.setText(feedback + "\nСессия завершена!");
        }

        return msg;
    }
}
