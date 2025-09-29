package com.quizbot.command;

import com.quizbot.model.QuestionSet;
import com.quizbot.sender.LearnSender;
import com.quizbot.session.LearnSession;

public class LearnCommand extends Command {
    private QuestionSet set;

    public LearnCommand(QuestionSet set) {
        this.set = set;
    }

    @Override
    public LearnSender execute(long chatId) {
        LearnSession session = new LearnSession(set);
        return new LearnSender(chatId, session);
    }
}
