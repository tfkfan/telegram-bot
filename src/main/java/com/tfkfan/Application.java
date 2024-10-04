package com.tfkfan;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class Application extends RouteBuilder {
    public static final String GREETING = "direct:send-greeting";

    @ConfigProperty(name = "telegram.token")
    private String token;

    @Override
    public void configure() {
        // @formatter:off
        from("telegram:bots?authorizationToken=" + token)
                .log("${body}")
                .choice()
                .when(simple("${body} == '/start'")).to(GREETING)
                .endChoice();

        from(GREETING)
                .setHeader("firstname", simple("${body.from.firstName}"))
                .choice()
                .when(simple("${headers.is_new_user} == false"))
                .log("Existing user found, sending greeting")
                .setBody(simple("Welcome once again, ${headers.firstname}!"))
                .to("telegram:bots?authorizationToken=" + token)
                .otherwise()
                .log("New user is created, sending greeting")
                .setBody(simple("You are new here, ${headers.firstname}, nice to meet you!"))
                .to("telegram:bots?authorizationToken=" + token)
                .end();
        // @formatter:on
    }
}