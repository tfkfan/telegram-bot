import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './subscriber.reducer';

export const SubscriberDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const subscriberEntity = useAppSelector(state => state.subscriber.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="subscriberDetailsHeading">
          <Translate contentKey="telegramBotApp.subscriber.detail.title">Subscriber</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{subscriberEntity.id}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="telegramBotApp.subscriber.active">Active</Translate>
            </span>
          </dt>
          <dd>{subscriberEntity.active ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/subscriber" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/subscriber/${subscriberEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SubscriberDetail;
