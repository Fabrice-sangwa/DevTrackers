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
import { IProblem } from 'app/shared/model/problem.model';
import { State } from 'app/shared/model/enumerations/state.model';
import { Priority } from 'app/shared/model/enumerations/priority.model';
import { getEntity, updateEntity, createEntity, reset } from './problem.reducer';

export const ProblemUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const projects = useAppSelector(state => state.project.entities);
  const problemEntity = useAppSelector(state => state.problem.entity);
  const loading = useAppSelector(state => state.problem.loading);
  const updating = useAppSelector(state => state.problem.updating);
  const updateSuccess = useAppSelector(state => state.problem.updateSuccess);
  const stateValues = Object.keys(State);
  const priorityValues = Object.keys(Priority);

  const handleClose = () => {
    navigate('/problem');
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
      ...problemEntity,
      ...values,
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
          state: 'EN_COURS',
          priority: 'URGENT',
          ...problemEntity,
          users: problemEntity?.users?.map(e => e.id.toString()),
          project: problemEntity?.project?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="devTrackersApp.problem.home.createOrEditLabel" data-cy="ProblemCreateUpdateHeading">
            <Translate contentKey="devTrackersApp.problem.home.createOrEditLabel">Create or edit a Problem</Translate>
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
                  id="problem-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('devTrackersApp.problem.description')}
                id="problem-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('devTrackersApp.problem.state')}
                id="problem-state"
                name="state"
                data-cy="state"
                type="select"
              >
                {stateValues.map(state => (
                  <option value={state} key={state}>
                    {translate('devTrackersApp.State.' + state)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('devTrackersApp.problem.priority')}
                id="problem-priority"
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
              <ValidatedField
                label={translate('devTrackersApp.problem.users')}
                id="problem-users"
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
                id="problem-project"
                name="project"
                data-cy="project"
                label={translate('devTrackersApp.problem.project')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/problem" replace color="info">
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

export default ProblemUpdate;
