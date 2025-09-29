package com.quizbot.command;

import com.quizbot.sender.Sender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public abstract class Command {
    public abstract Sender execute(long chatId);
}
