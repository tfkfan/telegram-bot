package com.tfkfan.bot.service.impl;

import com.tfkfan.bot.domain.SearchQuery;
import com.tfkfan.bot.service.Paged;
import com.tfkfan.bot.service.SearchQueryService;
import com.tfkfan.bot.service.dto.SearchQueryDTO;
import com.tfkfan.bot.service.mapper.SearchQueryMapper;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@Transactional
public class SearchQueryServiceImpl implements SearchQueryService {

    private final Logger log = LoggerFactory.getLogger(SearchQueryServiceImpl.class);

    @Inject
    SearchQueryMapper searchQueryMapper;

    @Override
    @Transactional
    public SearchQueryDTO persistOrUpdate(SearchQueryDTO searchQueryDTO) {
        log.debug("Request to save SearchQuery : {}", searchQueryDTO);
        var searchQuery = searchQueryMapper.toEntity(searchQueryDTO);
        searchQuery = SearchQuery.persistOrUpdate(searchQuery);
        return searchQueryMapper.toDto(searchQuery);
    }

    /**
     * Delete the SearchQuery by id.
     *
     * @param id the id of the entity.
     */
    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete SearchQuery : {}", id);
        SearchQuery.findByIdOptional(id).ifPresent(searchQuery -> {
            searchQuery.delete();
        });
    }

    /**
     * Get one searchQuery by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<SearchQueryDTO> findOne(Long id) {
        log.debug("Request to get SearchQuery : {}", id);
        return SearchQuery.findByIdOptional(id).map(searchQuery -> searchQueryMapper.toDto((SearchQuery) searchQuery));
    }

    /**
     * Get all the searchQueries.
     * @param page the pagination information.
     * @return the list of entities.
     */
    @Override
    public Paged<SearchQueryDTO> findAll(Page page) {
        log.debug("Request to get all SearchQueries");
        return new Paged<>(SearchQuery.findAll().page(page)).map(searchQuery -> searchQueryMapper.toDto((SearchQuery) searchQuery));
    }
}
