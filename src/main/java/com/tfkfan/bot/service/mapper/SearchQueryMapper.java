package com.tfkfan.bot.service.mapper;

import com.tfkfan.bot.domain.*;
import com.tfkfan.bot.service.dto.SearchQueryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SearchQuery} and its DTO {@link SearchQueryDTO}.
 */
@Mapper(componentModel = "jakarta", uses = {})
public interface SearchQueryMapper extends EntityMapper<SearchQueryDTO, SearchQuery> {
    default SearchQuery fromId(Long id) {
        if (id == null) {
            return null;
        }
        SearchQuery searchQuery = new SearchQuery();
        searchQuery.id = id;
        return searchQuery;
    }
}
