package com.tfkfan.bot.service.impl;

import com.tfkfan.bot.domain.Subscriber;
import com.tfkfan.bot.service.Paged;
import com.tfkfan.bot.service.SubscriberService;
import com.tfkfan.bot.service.dto.SubscriberDTO;
import com.tfkfan.bot.service.mapper.SubscriberMapper;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@Transactional
public class SubscriberServiceImpl implements SubscriberService {

    private final Logger log = LoggerFactory.getLogger(SubscriberServiceImpl.class);

    @Inject
    SubscriberMapper subscriberMapper;

    @Override
    @Transactional
    public SubscriberDTO persistOrUpdate(SubscriberDTO subscriberDTO) {
        log.debug("Request to save Subscriber : {}", subscriberDTO);
        var subscriber = subscriberMapper.toEntity(subscriberDTO);
        subscriber = Subscriber.persistOrUpdate(subscriber);
        return subscriberMapper.toDto(subscriber);
    }

    /**
     * Delete the Subscriber by id.
     *
     * @param id the id of the entity.
     */
    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Subscriber : {}", id);
        Subscriber.findByIdOptional(id).ifPresent(subscriber -> {
            subscriber.delete();
        });
    }

    /**
     * Get one subscriber by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<SubscriberDTO> findOne(Long id) {
        log.debug("Request to get Subscriber : {}", id);
        return Subscriber.findByIdOptional(id).map(subscriber -> subscriberMapper.toDto((Subscriber) subscriber));
    }

    /**
     * Get all the subscribers.
     * @param page the pagination information.
     * @return the list of entities.
     */
    @Override
    public Paged<SubscriberDTO> findAll(Page page) {
        log.debug("Request to get all Subscribers");
        return new Paged<>(Subscriber.findAll().page(page)).map(subscriber -> subscriberMapper.toDto((Subscriber) subscriber));
    }
}
