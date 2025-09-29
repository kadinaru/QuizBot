package com.quizbot.command;

import com.quizbot.model.QuestionSet;
import com.quizbot.sender.StudySender;
import com.quizbot.session.StudySession;

public class StudyCommand extends Command {
    private QuestionSet set;

    public StudyCommand(QuestionSet set) {
        this.set = set;
    }

    @Override
    public StudySender execute(long chatId) {
        StudySession session = new StudySession(set);
        return new StudySender(chatId, session);
    }
}

