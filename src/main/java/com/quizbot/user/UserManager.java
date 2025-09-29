package com.quizbot.user;

import com.quizbot.sender.Sender;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<Long, Sender> sessions = new HashMap<>();
    private static UserManager instance;

    private UserManager() {}

    public static UserManager getInstance() {
        if (instance == null) instance = new UserManager();
        return instance;
    }

    public void setSender(Long chatId, Sender sender) {
        sessions.put(chatId, sender);
    }

    public Sender getSender(Long chatId) {
        return sessions.get(chatId);
    }

    public void removeSender(Long chatId) {
        sessions.remove(chatId);
    }
}

