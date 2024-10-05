package com.tfkfan.bot.service.mapper;

import com.tfkfan.bot.domain.*;
import com.tfkfan.bot.service.dto.SubscriberDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Subscriber} and its DTO {@link SubscriberDTO}.
 */
@Mapper(componentModel = "jakarta", uses = {})
public interface SubscriberMapper extends EntityMapper<SubscriberDTO, Subscriber> {
    default Subscriber fromId(Long id) {
        if (id == null) {
            return null;
        }
        Subscriber subscriber = new Subscriber();
        subscriber.id = id;
        return subscriber;
    }
}
