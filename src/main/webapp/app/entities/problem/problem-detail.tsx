import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './problem.reducer';

export const ProblemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const problemEntity = useAppSelector(state => state.problem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="problemDetailsHeading">
          <Translate contentKey="devTrackersApp.problem.detail.title">Problem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{problemEntity.id}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="devTrackersApp.problem.description">Description</Translate>
            </span>
          </dt>
          <dd>{problemEntity.description}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="devTrackersApp.problem.state">State</Translate>
            </span>
          </dt>
          <dd>{problemEntity.state}</dd>
          <dt>
            <span id="priority">
              <Translate contentKey="devTrackersApp.problem.priority">Priority</Translate>
            </span>
          </dt>
          <dd>{problemEntity.priority}</dd>
          <dt>
            <Translate contentKey="devTrackersApp.problem.users">Users</Translate>
          </dt>
          <dd>
            {problemEntity.users
              ? problemEntity.users.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {problemEntity.users && i === problemEntity.users.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="devTrackersApp.problem.project">Project</Translate>
          </dt>
          <dd>{problemEntity.project ? problemEntity.project.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/problem" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/problem/${problemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProblemDetail;
