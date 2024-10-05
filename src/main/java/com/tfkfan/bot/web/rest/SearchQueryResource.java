package com.tfkfan.bot.web.rest;

import static jakarta.ws.rs.core.UriBuilder.fromPath;

import com.tfkfan.bot.service.Paged;
import com.tfkfan.bot.service.SearchQueryService;
import com.tfkfan.bot.service.dto.SearchQueryDTO;
import com.tfkfan.bot.web.rest.errors.BadRequestAlertException;
import com.tfkfan.bot.web.rest.vm.PageRequestVM;
import com.tfkfan.bot.web.rest.vm.SortRequestVM;
import com.tfkfan.bot.web.util.HeaderUtil;
import com.tfkfan.bot.web.util.PaginationUtil;
import com.tfkfan.bot.web.util.ResponseUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.Optional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST controller for managing {@link com.tfkfan.bot.domain.SearchQuery}.
 */
@Path("/api/search-queries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class SearchQueryResource {

    private final Logger log = LoggerFactory.getLogger(SearchQueryResource.class);

    private static final String ENTITY_NAME = "searchQuery";

    @ConfigProperty(name = "application.name")
    String applicationName;

    @Inject
    SearchQueryService searchQueryService;

    /**
     * {@code POST  /search-queries} : Create a new searchQuery.
     *
     * @param searchQueryDTO the searchQueryDTO to create.
     * @return the {@link Response} with status {@code 201 (Created)} and with body the new searchQueryDTO, or with status {@code 400 (Bad Request)} if the searchQuery has already an ID.
     */
    @POST
    public Response createSearchQuery(@Valid SearchQueryDTO searchQueryDTO, @Context UriInfo uriInfo) {
        log.debug("REST request to save SearchQuery : {}", searchQueryDTO);
        if (searchQueryDTO.id != null) {
            throw new BadRequestAlertException("A new searchQuery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (searchQueryDTO.minPrice != null && searchQueryDTO.maxPrice != null && searchQueryDTO.minPrice > searchQueryDTO.maxPrice) {
            throw new BadRequestAlertException("Min value cannot be greater than max one", ENTITY_NAME, "minmaxinvalid");
        }
        var result = searchQueryService.persistOrUpdate(searchQueryDTO);
        var response = Response.created(fromPath(uriInfo.getPath()).path(result.id.toString()).build()).entity(result);
        HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code PUT  /search-queries} : Updates an existing searchQuery.
     *
     * @param searchQueryDTO the searchQueryDTO to update.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the updated searchQueryDTO,
     * or with status {@code 400 (Bad Request)} if the searchQueryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the searchQueryDTO couldn't be updated.
     */
    @PUT
    @Path("/{id}")
    public Response updateSearchQuery(@Valid SearchQueryDTO searchQueryDTO, @PathParam("id") Long id) {
        log.debug("REST request to update SearchQuery : {}", searchQueryDTO);
        if (searchQueryDTO.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        var result = searchQueryService.persistOrUpdate(searchQueryDTO);
        var response = Response.ok().entity(result);
        HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, searchQueryDTO.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code DELETE  /search-queries/:id} : delete the "id" searchQuery.
     *
     * @param id the id of the searchQueryDTO to delete.
     * @return the {@link Response} with status {@code 204 (NO_CONTENT)}.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteSearchQuery(@PathParam("id") Long id) {
        log.debug("REST request to delete SearchQuery : {}", id);
        searchQueryService.delete(id);
        var response = Response.noContent();
        HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code GET  /search-queries} : get all the searchQueries.
     *
     * @param pageRequest the pagination information.
     * @return the {@link Response} with status {@code 200 (OK)} and the list of searchQueries in body.
     */
    @GET
    public Response getAllSearchQueries(
        @BeanParam PageRequestVM pageRequest,
        @BeanParam SortRequestVM sortRequest,
        @Context UriInfo uriInfo
    ) {
        log.debug("REST request to get a page of SearchQueries");
        var page = pageRequest.toPage();
        var sort = sortRequest.toSort();
        Paged<SearchQueryDTO> result = searchQueryService.findAll(page);
        var response = Response.ok().entity(result.content);
        response = PaginationUtil.withPaginationInfo(response, uriInfo, result);
        return response.build();
    }

    /**
     * {@code GET  /search-queries/:id} : get the "id" searchQuery.
     *
     * @param id the id of the searchQueryDTO to retrieve.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the searchQueryDTO, or with status {@code 404 (Not Found)}.
     */
    @GET
    @Path("/{id}")
    public Response getSearchQuery(@PathParam("id") Long id) {
        log.debug("REST request to get SearchQuery : {}", id);
        Optional<SearchQueryDTO> searchQueryDTO = searchQueryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(searchQueryDTO);
    }
}
