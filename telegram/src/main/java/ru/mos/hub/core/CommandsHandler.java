package ru.mos.hub.core;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class CommandsHandler extends TelegramLongPollingBot {

    public CommandsHandler(String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getText());
    }

    @Override
    public String getBotUsername() {
        return "fgsdfg";
    }
}
