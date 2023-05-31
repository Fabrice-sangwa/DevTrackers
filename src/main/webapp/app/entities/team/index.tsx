import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Team from './team';
import TeamDetail from './team-detail';
import TeamUpdate from './team-update';
import TeamDeleteDialog from './team-delete-dialog';

const TeamRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Team />} />
    <Route path="new" element={<TeamUpdate />} />
    <Route path=":id">
      <Route index element={<TeamDetail />} />
      <Route path="edit" element={<TeamUpdate />} />
      <Route path="delete" element={<TeamDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TeamRoutes;
