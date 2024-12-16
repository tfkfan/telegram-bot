package com.tfkfan.bot.service;

import com.tfkfan.bot.service.dto.SearchItemDTO;
import io.quarkus.panache.common.Page;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tfkfan.bot.domain.SearchItem}.
 */
public interface SearchItemService {
    /**
     * Save a searchItem.
     *
     * @param searchItemDTO the entity to save.
     * @return the persisted entity.
     */
    SearchItemDTO persistOrUpdate(SearchItemDTO searchItemDTO);

    /**
     * Delete the "href" searchItemDTO.
     *
     * @param href the id of the entity.
     */
    void delete(String href);

    /**
     * Get all the searchItems.
     * @param page the pagination information.
     * @return the list of entities.
     */
    Paged<SearchItemDTO> findAll(Page page);

    /**
     * Get the "href" searchItemDTO.
     *
     * @param href the id of the entity.
     * @return the entity.
     */
    Optional<SearchItemDTO> findOne(String href);
}
