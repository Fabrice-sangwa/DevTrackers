import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Project from './project';
import ProjectDetail from './project-detail';
import ProjectUpdate from './project-update';
import ProjectDeleteDialog from './project-delete-dialog';

const ProjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Project />} />
    <Route path="new" element={<ProjectUpdate />} />
    <Route path=":id">
      <Route index element={<ProjectDetail />} />
      <Route path="edit" element={<ProjectUpdate />} />
      <Route path="delete" element={<ProjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProjectRoutes;
