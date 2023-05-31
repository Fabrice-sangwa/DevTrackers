import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDashboard } from 'app/shared/model/dashboard.model';
import { getEntities as getDashboards } from 'app/entities/dashboard/dashboard.reducer';
import { IProject } from 'app/shared/model/project.model';
import { getEntity, updateEntity, createEntity, reset } from './project.reducer';

export const ProjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dashboards = useAppSelector(state => state.dashboard.entities);
  const projectEntity = useAppSelector(state => state.project.entity);
  const loading = useAppSelector(state => state.project.loading);
  const updating = useAppSelector(state => state.project.updating);
  const updateSuccess = useAppSelector(state => state.project.updateSuccess);

  const handleClose = () => {
    navigate('/project');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDashboards({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...projectEntity,
      ...values,
      dashboard: dashboards.find(it => it.id.toString() === values.dashboard.toString()),
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
          ...projectEntity,
          dashboard: projectEntity?.dashboard?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="devTrackersApp.project.home.createOrEditLabel" data-cy="ProjectCreateUpdateHeading">
            <Translate contentKey="devTrackersApp.project.home.createOrEditLabel">Create or edit a Project</Translate>
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
                  id="project-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('devTrackersApp.project.name')} id="project-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('devTrackersApp.project.objective')}
                id="project-objective"
                name="objective"
                data-cy="objective"
                type="text"
              />
              <ValidatedField
                label={translate('devTrackersApp.project.delay')}
                id="project-delay"
                name="delay"
                data-cy="delay"
                type="text"
              />
              <ValidatedField
                label={translate('devTrackersApp.project.resources')}
                id="project-resources"
                name="resources"
                data-cy="resources"
                type="text"
              />
              <ValidatedField
                label={translate('devTrackersApp.project.version')}
                id="project-version"
                name="version"
                data-cy="version"
                type="text"
              />
              <ValidatedField
                id="project-dashboard"
                name="dashboard"
                data-cy="dashboard"
                label={translate('devTrackersApp.project.dashboard')}
                type="select"
              >
                <option value="" key="0" />
                {dashboards
                  ? dashboards.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/project" replace color="info">
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

export default ProjectUpdate;
