package com.tfkfan.bot.web.rest;

import com.tfkfan.bot.service.AvitoParserService;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/test")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class TestResource {

    private final Logger log = LoggerFactory.getLogger(TestResource.class);

    @Inject
    AvitoParserService avitoParserService;

    @GET
    @PermitAll
    public Response test() {
        avitoParserService.run();
        return Response.ok("OK").build();
    }
}
