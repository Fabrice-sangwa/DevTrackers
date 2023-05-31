import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Project from './project';
import Task from './task';
import Problem from './problem';
import Team from './team';
import Message from './message';
import Dashboard from './dashboard';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="project/*" element={<Project />} />
        <Route path="task/*" element={<Task />} />
        <Route path="problem/*" element={<Problem />} />
        <Route path="team/*" element={<Team />} />
        <Route path="message/*" element={<Message />} />
        <Route path="dashboard/*" element={<Dashboard />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
