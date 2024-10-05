package com.tfkfan.bot.service.impl;

import com.tfkfan.bot.domain.SearchItem;
import com.tfkfan.bot.service.Paged;
import com.tfkfan.bot.service.SearchItemService;
import com.tfkfan.bot.service.dto.SearchItemDTO;
import com.tfkfan.bot.service.mapper.SearchItemMapper;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@Transactional
public class SearchItemServiceImpl implements SearchItemService {

    private final Logger log = LoggerFactory.getLogger(SearchItemServiceImpl.class);

    @Inject
    SearchItemMapper searchItemMapper;

    @Override
    @Transactional
    public SearchItemDTO persistOrUpdate(SearchItemDTO searchItemDTO) {
        log.debug("Request to save SearchItem : {}", searchItemDTO);
        var searchItem = searchItemMapper.toEntity(searchItemDTO);
        searchItem = SearchItem.persistOrUpdate(searchItem);
        return searchItemMapper.toDto(searchItem);
    }

    /**
     * Delete the SearchItem by href.
     *
     * @param href the id of the entity.
     */
    @Override
    @Transactional
    public void delete(String href) {
        log.debug("Request to delete SearchItem : {}", href);
        SearchItem.findByIdOptional(href).ifPresent(searchItem -> {
            searchItem.delete();
        });
    }

    /**
     * Get one searchItem by href.
     *
     * @param href the href of the entity.
     * @return the entity.
     */
    @Override
    public Optional<SearchItemDTO> findOne(String href) {
        log.debug("Request to get SearchItem : {}", href);
        return SearchItem.findByIdOptional(href).map(searchItem -> searchItemMapper.toDto((SearchItem) searchItem));
    }

    /**
     * Get all the searchItems.
     * @param page the pagination information.
     * @return the list of entities.
     */
    @Override
    public Paged<SearchItemDTO> findAll(Page page) {
        log.debug("Request to get all SearchItems");
        return new Paged<>(SearchItem.findAll().page(page)).map(searchItem -> searchItemMapper.toDto((SearchItem) searchItem));
    }
}
