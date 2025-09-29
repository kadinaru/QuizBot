module com.example.quizbot {
    requires javafx.controls;
    requires javafx.fxml;
    requires telegrambots.meta;
    requires telegrambots;
    requires com.fasterxml.jackson.databind;
    requires java.base;


    opens com.quizbot.model to com.fasterxml.jackson.databind;
    exports com.quizbot;
}
