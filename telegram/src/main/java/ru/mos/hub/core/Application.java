package ru.mos.hub.core;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Application {

    public static final String AUTHORIZATION_TOKEN = "7002247280:AAF_y-ANikPO0GcbSWTieD2iHaoBLUZ7m24";

    private Application() {
        // noop
    }

    public static void main(String[] args) throws Exception {
        TelegramBotsApi botsApplication = new TelegramBotsApi(DefaultBotSession.class);
        try {
            botsApplication.registerBot(new CommandsHandler(AUTHORIZATION_TOKEN));
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
