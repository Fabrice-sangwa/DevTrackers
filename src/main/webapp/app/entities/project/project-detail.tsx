import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './project.reducer';

export const ProjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const projectEntity = useAppSelector(state => state.project.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="projectDetailsHeading">
          <Translate contentKey="devTrackersApp.project.detail.title">Project</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{projectEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="devTrackersApp.project.name">Name</Translate>
            </span>
          </dt>
          <dd>{projectEntity.name}</dd>
          <dt>
            <span id="objective">
              <Translate contentKey="devTrackersApp.project.objective">Objective</Translate>
            </span>
          </dt>
          <dd>{projectEntity.objective}</dd>
          <dt>
            <span id="delay">
              <Translate contentKey="devTrackersApp.project.delay">Delay</Translate>
            </span>
          </dt>
          <dd>{projectEntity.delay}</dd>
          <dt>
            <span id="resources">
              <Translate contentKey="devTrackersApp.project.resources">Resources</Translate>
            </span>
          </dt>
          <dd>{projectEntity.resources}</dd>
          <dt>
            <span id="version">
              <Translate contentKey="devTrackersApp.project.version">Version</Translate>
            </span>
          </dt>
          <dd>{projectEntity.version}</dd>
          <dt>
            <Translate contentKey="devTrackersApp.project.dashboard">Dashboard</Translate>
          </dt>
          <dd>{projectEntity.dashboard ? projectEntity.dashboard.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/project" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/project/${projectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProjectDetail;
