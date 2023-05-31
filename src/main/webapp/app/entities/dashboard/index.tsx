import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Dashboard from './dashboard';
import DashboardDetail from './dashboard-detail';
import DashboardUpdate from './dashboard-update';
import DashboardDeleteDialog from './dashboard-delete-dialog';

const DashboardRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Dashboard />} />
    <Route path="new" element={<DashboardUpdate />} />
    <Route path=":id">
      <Route index element={<DashboardDetail />} />
      <Route path="edit" element={<DashboardUpdate />} />
      <Route path="delete" element={<DashboardDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DashboardRoutes;
