import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IProject } from 'app/shared/model/project.model';
import { getEntities as getProjects } from 'app/entities/project/project.reducer';
import { ITask } from 'app/shared/model/task.model';
import { Priority } from 'app/shared/model/enumerations/priority.model';
import { State } from 'app/shared/model/enumerations/state.model';
import { getEntity, updateEntity, createEntity, reset } from './task.reducer';

export const TaskUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const projects = useAppSelector(state => state.project.entities);
  const taskEntity = useAppSelector(state => state.task.entity);
  const loading = useAppSelector(state => state.task.loading);
  const updating = useAppSelector(state => state.task.updating);
  const updateSuccess = useAppSelector(state => state.task.updateSuccess);
  const priorityValues = Object.keys(Priority);
  const stateValues = Object.keys(State);

  const handleClose = () => {
    navigate('/task');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getProjects({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...taskEntity,
      ...values,
      assignedUsers: mapIdList(values.assignedUsers),
      users: mapIdList(values.users),
      project: projects.find(it => it.id.toString() === values.project.toString()),
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
          priority: 'URGENT',
          state: 'EN_COURS',
          ...taskEntity,
          assignedUsers: taskEntity?.assignedUsers?.map(e => e.id.toString()),
          users: taskEntity?.users?.map(e => e.id.toString()),
          project: taskEntity?.project?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="devTrackersApp.task.home.createOrEditLabel" data-cy="TaskCreateUpdateHeading">
            <Translate contentKey="devTrackersApp.task.home.createOrEditLabel">Create or edit a Task</Translate>
          </h2>
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
                  id="task-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('devTrackersApp.task.name')}
                id="task-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('devTrackersApp.task.description')}
                id="task-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('devTrackersApp.task.priority')}
                id="task-priority"
                name="priority"
                data-cy="priority"
                type="select"
              >
                {priorityValues.map(priority => (
                  <option value={priority} key={priority}>
                    {translate('devTrackersApp.Priority.' + priority)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label={translate('devTrackersApp.task.state')} id="task-state" name="state" data-cy="state" type="select">
                {stateValues.map(state => (
                  <option value={state} key={state}>
                    {translate('devTrackersApp.State.' + state)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('devTrackersApp.task.assignedUsers')}
                id="task-assignedUsers"
                data-cy="assignedUsers"
                type="select"
                multiple
                name="assignedUsers"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('devTrackersApp.task.users')}
                id="task-users"
                data-cy="users"
                type="select"
                multiple
                name="users"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="task-project"
                name="project"
                data-cy="project"
                label={translate('devTrackersApp.task.project')}
                type="select"
              >
                <option value="" key="0" />
                {projects
                  ? projects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/task" replace color="info">
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

export default TaskUpdate;
