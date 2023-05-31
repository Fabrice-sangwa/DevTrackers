import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './task.reducer';

export const TaskDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const taskEntity = useAppSelector(state => state.task.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="taskDetailsHeading">
          <Translate contentKey="devTrackersApp.task.detail.title">Task</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{taskEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="devTrackersApp.task.name">Name</Translate>
            </span>
          </dt>
          <dd>{taskEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="devTrackersApp.task.description">Description</Translate>
            </span>
          </dt>
          <dd>{taskEntity.description}</dd>
          <dt>
            <span id="priority">
              <Translate contentKey="devTrackersApp.task.priority">Priority</Translate>
            </span>
          </dt>
          <dd>{taskEntity.priority}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="devTrackersApp.task.state">State</Translate>
            </span>
          </dt>
          <dd>{taskEntity.state}</dd>
          <dt>
            <Translate contentKey="devTrackersApp.task.assignedUsers">Assigned Users</Translate>
          </dt>
          <dd>
            {taskEntity.assignedUsers
              ? taskEntity.assignedUsers.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {taskEntity.assignedUsers && i === taskEntity.assignedUsers.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="devTrackersApp.task.users">Users</Translate>
          </dt>
          <dd>
            {taskEntity.users
              ? taskEntity.users.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {taskEntity.users && i === taskEntity.users.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="devTrackersApp.task.project">Project</Translate>
          </dt>
          <dd>{taskEntity.project ? taskEntity.project.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/task" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/task/${taskEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TaskDetail;
