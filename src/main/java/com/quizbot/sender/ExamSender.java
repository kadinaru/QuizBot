package com.quizbot.sender;

import com.quizbot.model.Question;
import com.quizbot.session.ExamSession;
import com.quizbot.session.StateSession;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class ExamSender extends Sender {

    public ExamSender(long chatId, ExamSession session) {
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

        if (stateSession.getState() == StateSession.State.END) {
            msg.setText(stateSession.end());
        }
        else if (stateSession.getState() == StateSession.State.INIT
                || stateSession.getState() == StateSession.State.ACTION) {

            ExamSession exam = (ExamSession) stateSession;
            Question q = exam.action();

            if (q != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Вопрос: ").append(q.getQuestion()).append("\n");

                // Выводим варианты с номерами
                int i = 1;
                for (String option : exam.getCurrentOptions()) {
                    sb.append(i++).append(". ").append(option).append("\n");
                }

                msg.setText(sb.toString());
            } else {
                msg.setText(stateSession.end());
            }
        } else {
            msg.setText("Ошибка состояния сессии!");
        }

        return msg;
    }
}
