package com.tfkfan.bot.telegram;

import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public abstract class BaseCommandsHandler extends TelegramLongPollingBot {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public BaseCommandsHandler(String botToken) {
        super(botToken);
    }

    public <T extends Serializable, M extends BotApiMethod<T>> T execute(M message) {
        try {
            return super.execute(message);
        } catch (Exception e) {
            log.error("Error processing non-command update", e);
            return null;
        }
    }
}
