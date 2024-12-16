import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './search-query.reducer';

export const SearchQueryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const searchQueryEntity = useAppSelector(state => state.searchQuery.entity);
  return (
    <Row>
      <Col md="8">
        <p className="head" data-cy="searchQueryDetailsHeading">
          <Translate contentKey="telegramBotApp.searchQuery.detail.title">SearchQuery</Translate>
        </p>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{searchQueryEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="telegramBotApp.searchQuery.value">Value</Translate>
            </span>
          </dt>
          <dd>{searchQueryEntity.value}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="telegramBotApp.searchQuery.active">Active</Translate>
            </span>
          </dt>
          <dd>{searchQueryEntity.active ? 'true' : 'false'}</dd>
          <dt>
            <span id="minPrice">
              <Translate contentKey="telegramBotApp.searchQuery.minPrice">Min Price</Translate>
            </span>
          </dt>
          <dd>{searchQueryEntity.minPrice}</dd>
          <dt>
            <span id="maxPrice">
              <Translate contentKey="telegramBotApp.searchQuery.maxPrice">Max Price</Translate>
            </span>
          </dt>
          <dd>{searchQueryEntity.maxPrice}</dd>
        </dl>
        <Button tag={Link} to="/search-query" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/search-query/${searchQueryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SearchQueryDetail;
