import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITeam } from 'app/shared/model/team.model';
import { getEntities } from './team.reducer';

export const Team = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const teamList = useAppSelector(state => state.team.entities);
  const loading = useAppSelector(state => state.team.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="team-heading" data-cy="TeamHeading">
        <Translate contentKey="devTrackersApp.team.home.title">Teams</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="devTrackersApp.team.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/team/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="devTrackersApp.team.home.createLabel">Create new Team</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {teamList && teamList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="devTrackersApp.team.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="devTrackersApp.team.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="devTrackersApp.team.users">Users</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {teamList.map((team, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/team/${team.id}`} color="link" size="sm">
                      {team.id}
                    </Button>
                  </td>
                  <td>{team.name}</td>
                  <td>
                    {team.users
                      ? team.users.map((val, j) => (
                          <span key={j}>
                            {val.id}
                            {j === team.users.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/team/${team.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/team/${team.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/team/${team.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="devTrackersApp.team.home.notFound">No Teams found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Team;
