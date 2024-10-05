package com.tfkfan.bot.config;

/**
 * Application constants.
 */
public final class Constants {

    public static final String COMMAND_INIT_CHAR = "/";

    public static final String START_COMMAND = COMMAND_INIT_CHAR + "start";
    public static final String STOP_COMMAND = COMMAND_INIT_CHAR + "stop";
    public static final String HELP_COMMAND = COMMAND_INIT_CHAR + "help";
    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";

    public static final String DEFAULT_LANGUAGE = "en";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final String LOCAL_DATE_FORMAT = "yyyy-MM-dd";

    private Constants() {}
}
