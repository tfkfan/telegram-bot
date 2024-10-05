package com.tfkfan.bot.web.rest;

import com.tfkfan.bot.service.Paged;
import com.tfkfan.bot.service.SubscriberService;
import com.tfkfan.bot.service.dto.SubscriberDTO;
import com.tfkfan.bot.web.rest.vm.PageRequestVM;
import com.tfkfan.bot.web.rest.vm.SortRequestVM;
import com.tfkfan.bot.web.util.PaginationUtil;
import com.tfkfan.bot.web.util.ResponseUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST controller for managing {@link com.tfkfan.bot.domain.Subscriber}.
 */
@Path("/api/subscribers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class SubscriberResource {

    private final Logger log = LoggerFactory.getLogger(SubscriberResource.class);

    @Inject
    SubscriberService subscriberService;

    /**
     * {@code GET  /subscribers} : get all the subscribers.
     *
     * @param pageRequest the pagination information.
     * @return the {@link Response} with status {@code 200 (OK)} and the list of subscribers in body.
     */
    @GET
    public Response getAllSubscribers(
        @BeanParam PageRequestVM pageRequest,
        @BeanParam SortRequestVM sortRequest,
        @Context UriInfo uriInfo
    ) {
        log.debug("REST request to get a page of Subscribers");
        var page = pageRequest.toPage();
        var sort = sortRequest.toSort();
        Paged<SubscriberDTO> result = subscriberService.findAll(page);
        var response = Response.ok().entity(result.content);
        response = PaginationUtil.withPaginationInfo(response, uriInfo, result);
        return response.build();
    }

    /**
     * {@code GET  /subscribers/:id} : get the "id" subscriber.
     *
     * @param id the id of the subscriberDTO to retrieve.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the subscriberDTO, or with status {@code 404 (Not Found)}.
     */
    @GET
    @Path("/{id}")
    public Response getSubscriber(@PathParam("id") Long id) {
        log.debug("REST request to get Subscriber : {}", id);
        Optional<SubscriberDTO> subscriberDTO = subscriberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subscriberDTO);
    }
}
