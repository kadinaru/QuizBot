package com.quizbot;

import com.quizbot.command.Command;
import com.quizbot.command.ExamCommand;
import com.quizbot.command.LearnCommand;
import com.quizbot.command.StudyCommand;
import com.quizbot.model.QuestionSet;
import com.quizbot.sender.Sender;
import com.quizbot.user.UserManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QuizBot extends TelegramLongPollingBot {

    private QuestionSet questionSet = new QuestionSet();
    private Map<String, Command> commands = new HashMap<>();

    public QuizBot() throws IOException {
        // Загружаем вопросы из файла
        questionSet.loadFromFile("src/main/resources/questions.json");

        // Регистрируем команды
        commands.put("/exam", new ExamCommand(questionSet, 5));
        commands.put("/learn", new LearnCommand(questionSet));
        commands.put("/study", new StudyCommand(questionSet));
    }

    @Override
    public String getBotUsername() {
        return "QuizBot";
    }

    @Override
    public String getBotToken() {
        return "8395970816:AAHGBo_fGUWfgv0YfxcaP2DHbLdom4JJ9Tc";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));

        UserManager userManager = UserManager.getInstance();

        if (text.startsWith("/")) {
            // Обрабатываем команды
            Command cmd = commands.get(text);
            if (cmd != null) {
                Sender sender = cmd.execute(chatId);
                userManager.setSender(chatId, sender);
                message = sender.createSendMessage();
            } else if (text.equals("/cancel")) {
                userManager.removeSender(chatId);
                message.setText("Сессия отменена. Введите команду: /exam, /learn или /study");
            } else {
                message.setText("Неизвестная команда! Используйте: /exam, /learn, /study, /cancel");
            }
        } else {
            // Обрабатываем ответы пользователя в текущей сессии
            Sender sender = userManager.getSender(chatId);
            if (sender != null) {
                sender.onMessageReceived(text);
                message = sender.createSendMessage();
            } else {
                message.setText("Введите команду для начала: /exam, /learn или /study");
            }
        }

        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


