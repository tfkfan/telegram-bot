package com.tfkfan.bot;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@QuarkusMain
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        log.info("Running telegram-bot");
        Quarkus.run(args);
    }
}
