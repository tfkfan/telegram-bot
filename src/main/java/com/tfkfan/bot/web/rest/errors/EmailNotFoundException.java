package com.tfkfan.bot.web.rest.errors;

public class EmailNotFoundException extends BadRequestAlertException {

    public EmailNotFoundException() {
        super("Email address not registered", "userManagement", "emailnotfound");
    }
}
