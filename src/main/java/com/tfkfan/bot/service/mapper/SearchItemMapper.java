package com.tfkfan.bot.service.mapper;

import com.tfkfan.bot.domain.*;
import com.tfkfan.bot.service.dto.SearchItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SearchItem} and its DTO {@link SearchItemDTO}.
 */
@Mapper(componentModel = "jakarta", uses = {})
public interface SearchItemMapper extends EntityMapper<SearchItemDTO, SearchItem> {
    default SearchItem fromId(String href) {
        if (href == null) {
            return null;
        }
        SearchItem searchItem = new SearchItem();
        searchItem.href = href;
        return searchItem;
    }
}
