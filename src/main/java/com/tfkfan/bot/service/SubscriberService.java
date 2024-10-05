package com.tfkfan.bot.service;

import com.tfkfan.bot.service.dto.SubscriberDTO;
import io.quarkus.panache.common.Page;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tfkfan.bot.domain.Subscriber}.
 */
public interface SubscriberService {
    /**
     * Save a subscriber.
     *
     * @param subscriberDTO the entity to save.
     * @return the persisted entity.
     */
    SubscriberDTO persistOrUpdate(SubscriberDTO subscriberDTO);

    /**
     * Delete the "id" subscriberDTO.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get all the subscribers.
     * @param page the pagination information.
     * @return the list of entities.
     */
    public Paged<SubscriberDTO> findAll(Page page);

    /**
     * Get the "id" subscriberDTO.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubscriberDTO> findOne(Long id);
}
