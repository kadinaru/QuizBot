package com.quizbot.command;

import com.quizbot.model.QuestionSet;
import com.quizbot.sender.ExamSender;
import com.quizbot.session.ExamSession;

public class ExamCommand extends Command {
    private QuestionSet set;
    private int totalQuestions;

    public ExamCommand(QuestionSet set, int totalQuestions) {
        this.set = set;
        this.totalQuestions = totalQuestions;
    }

    @Override
    public ExamSender execute(long chatId) {
        ExamSession session = new ExamSession(set, totalQuestions);
        return new ExamSender(chatId, session);
    }
}
