package com.tfkfan.bot.service;

import com.tfkfan.bot.service.dto.SearchQueryDTO;
import io.quarkus.panache.common.Page;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tfkfan.bot.domain.SearchQuery}.
 */
public interface SearchQueryService {
    /**
     * Save a searchQuery.
     *
     * @param searchQueryDTO the entity to save.
     * @return the persisted entity.
     */
    SearchQueryDTO persistOrUpdate(SearchQueryDTO searchQueryDTO);

    /**
     * Delete the "id" searchQueryDTO.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get all the searchQueries.
     * @param page the pagination information.
     * @return the list of entities.
     */
    public Paged<SearchQueryDTO> findAll(Page page);

    /**
     * Get the "id" searchQueryDTO.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SearchQueryDTO> findOne(Long id);
}
