import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISearchQuery } from 'app/shared/model/search-query.model';
import { getEntity, updateEntity, createEntity, reset } from './search-query.reducer';

export const SearchQueryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const searchQueryEntity = useAppSelector(state => state.searchQuery.entity);
  const loading = useAppSelector(state => state.searchQuery.loading);
  const updating = useAppSelector(state => state.searchQuery.updating);
  const updateSuccess = useAppSelector(state => state.searchQuery.updateSuccess);

  const handleClose = () => {
    navigate('/search-query' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.minPrice !== undefined && typeof values.minPrice !== 'number') {
      values.minPrice = Number(values.minPrice);
    }
    if (values.maxPrice !== undefined && typeof values.maxPrice !== 'number') {
      values.maxPrice = Number(values.maxPrice);
    }

    const entity = {
      ...searchQueryEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...searchQueryEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <p className="head" id="telegramBotApp.searchQuery.home.createOrEditLabel" data-cy="SearchQueryCreateUpdateHeading">
            <Translate contentKey="telegramBotApp.searchQuery.home.createOrEditLabel">Create or edit a SearchQuery</Translate>
          </p>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="search-query-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('telegramBotApp.searchQuery.value')}
                id="search-query-value"
                name="value"
                data-cy="value"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('telegramBotApp.searchQuery.active')}
                id="search-query-active"
                name="active"
                data-cy="active"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('telegramBotApp.searchQuery.minPrice')}
                id="search-query-minPrice"
                name="minPrice"
                data-cy="minPrice"
                type="number"
              />
              <ValidatedField
                label={translate('telegramBotApp.searchQuery.maxPrice')}
                id="search-query-maxPrice"
                name="maxPrice"
                data-cy="maxPrice"
                type="number"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/search-query" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default SearchQueryUpdate;
