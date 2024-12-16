package com.tfkfan.bot.web.rest;

import com.tfkfan.bot.service.Paged;
import com.tfkfan.bot.service.SearchItemService;
import com.tfkfan.bot.service.dto.SearchItemDTO;
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
 * REST controller for managing {@link com.tfkfan.bot.domain.SearchItem}.
 */
@Path("/api/search-items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class SearchItemResource {

    private final Logger log = LoggerFactory.getLogger(SearchItemResource.class);

    @Inject
    SearchItemService searchItemService;

    /**
     * {@code GET  /search-items} : get all the searchItems.
     *
     * @param pageRequest the pagination information.
     * @return the {@link Response} with status {@code 200 (OK)} and the list of searchItems in body.
     */
    @GET
    public Response getAllSearchItems(
        @BeanParam PageRequestVM pageRequest,
        @BeanParam SortRequestVM sortRequest,
        @Context UriInfo uriInfo
    ) {
        log.debug("REST request to get a page of SearchItems");
        var page = pageRequest.toPage();
        var sort = sortRequest.toSort();
        Paged<SearchItemDTO> result = searchItemService.findAll(page);
        var response = Response.ok().entity(result.content);
        response = PaginationUtil.withPaginationInfo(response, uriInfo, result);
        return response.build();
    }

    /**
     * {@code GET  /search-items/:href} : get the "href" searchItem.
     *
     * @param href the href of the searchItemDTO to retrieve.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the searchItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GET
    @Path("/{href}")
    public Response getSearchItem(@PathParam("href") String href) {
        log.debug("REST request to get SearchItem : {}", href);
        Optional<SearchItemDTO> searchItemDTO = searchItemService.findOne(href);
        return ResponseUtil.wrapOrNotFound(searchItemDTO);
    }
}
