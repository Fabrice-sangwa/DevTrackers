import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProject } from 'app/shared/model/project.model';
import { getEntities } from './project.reducer';

export const Project = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const projectList = useAppSelector(state => state.project.entities);
  const loading = useAppSelector(state => state.project.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="project-heading" data-cy="ProjectHeading">
        <Translate contentKey="devTrackersApp.project.home.title">Projects</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="devTrackersApp.project.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/project/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="devTrackersApp.project.home.createLabel">Create new Project</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {projectList && projectList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="devTrackersApp.project.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="devTrackersApp.project.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="devTrackersApp.project.objective">Objective</Translate>
                </th>
                <th>
                  <Translate contentKey="devTrackersApp.project.delay">Delay</Translate>
                </th>
                <th>
                  <Translate contentKey="devTrackersApp.project.resources">Resources</Translate>
                </th>
                <th>
                  <Translate contentKey="devTrackersApp.project.version">Version</Translate>
                </th>
                <th>
                  <Translate contentKey="devTrackersApp.project.dashboard">Dashboard</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {projectList.map((project, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/project/${project.id}`} color="link" size="sm">
                      {project.id}
                    </Button>
                  </td>
                  <td>{project.name}</td>
                  <td>{project.objective}</td>
                  <td>{project.delay}</td>
                  <td>{project.resources}</td>
                  <td>{project.version}</td>
                  <td>{project.dashboard ? <Link to={`/dashboard/${project.dashboard.id}`}>{project.dashboard.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/project/${project.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/project/${project.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/project/${project.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="devTrackersApp.project.home.notFound">No Projects found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Project;
