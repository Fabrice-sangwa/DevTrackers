import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITask } from 'app/shared/model/task.model';
import { getEntities } from './task.reducer';

export const Task = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const taskList = useAppSelector(state => state.task.entities);
  const loading = useAppSelector(state => state.task.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="task-heading" data-cy="TaskHeading">
        <Translate contentKey="devTrackersApp.task.home.title">Tasks</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="devTrackersApp.task.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/task/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="devTrackersApp.task.home.createLabel">Create new Task</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {taskList && taskList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="devTrackersApp.task.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="devTrackersApp.task.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="devTrackersApp.task.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="devTrackersApp.task.priority">Priority</Translate>
                </th>
                <th>
                  <Translate contentKey="devTrackersApp.task.state">State</Translate>
                </th>
                <th>
                  <Translate contentKey="devTrackersApp.task.assignedUsers">Assigned Users</Translate>
                </th>
                <th>
                  <Translate contentKey="devTrackersApp.task.users">Users</Translate>
                </th>
                <th>
                  <Translate contentKey="devTrackersApp.task.project">Project</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {taskList.map((task, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/task/${task.id}`} color="link" size="sm">
                      {task.id}
                    </Button>
                  </td>
                  <td>{task.name}</td>
                  <td>{task.description}</td>
                  <td>
                    <Translate contentKey={`devTrackersApp.Priority.${task.priority}`} />
                  </td>
                  <td>
                    <Translate contentKey={`devTrackersApp.State.${task.state}`} />
                  </td>
                  <td>
                    {task.assignedUsers
                      ? task.assignedUsers.map((val, j) => (
                          <span key={j}>
                            {val.id}
                            {j === task.assignedUsers.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {task.users
                      ? task.users.map((val, j) => (
                          <span key={j}>
                            {val.id}
                            {j === task.users.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>{task.project ? <Link to={`/project/${task.project.id}`}>{task.project.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/task/${task.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/task/${task.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/task/${task.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="devTrackersApp.task.home.notFound">No Tasks found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Task;
