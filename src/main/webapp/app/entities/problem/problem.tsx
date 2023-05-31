import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProblem } from 'app/shared/model/problem.model';
import { getEntities } from './problem.reducer';

export const Problem = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const problemList = useAppSelector(state => state.problem.entities);
  const loading = useAppSelector(state => state.problem.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="problem-heading" data-cy="ProblemHeading">
        <Translate contentKey="devTrackersApp.problem.home.title">Problems</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="devTrackersApp.problem.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/problem/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="devTrackersApp.problem.home.createLabel">Create new Problem</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {problemList && problemList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="devTrackersApp.problem.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="devTrackersApp.problem.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="devTrackersApp.problem.state">State</Translate>
                </th>
                <th>
                  <Translate contentKey="devTrackersApp.problem.priority">Priority</Translate>
                </th>
                <th>
                  <Translate contentKey="devTrackersApp.problem.users">Users</Translate>
                </th>
                <th>
                  <Translate contentKey="devTrackersApp.problem.project">Project</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {problemList.map((problem, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/problem/${problem.id}`} color="link" size="sm">
                      {problem.id}
                    </Button>
                  </td>
                  <td>{problem.description}</td>
                  <td>
                    <Translate contentKey={`devTrackersApp.State.${problem.state}`} />
                  </td>
                  <td>
                    <Translate contentKey={`devTrackersApp.Priority.${problem.priority}`} />
                  </td>
                  <td>
                    {problem.users
                      ? problem.users.map((val, j) => (
                          <span key={j}>
                            {val.id}
                            {j === problem.users.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>{problem.project ? <Link to={`/project/${problem.project.id}`}>{problem.project.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/problem/${problem.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/problem/${problem.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/problem/${problem.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="devTrackersApp.problem.home.notFound">No Problems found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Problem;
