import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Problem from './problem';
import ProblemDetail from './problem-detail';
import ProblemUpdate from './problem-update';
import ProblemDeleteDialog from './problem-delete-dialog';

const ProblemRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Problem />} />
    <Route path="new" element={<ProblemUpdate />} />
    <Route path=":id">
      <Route index element={<ProblemDetail />} />
      <Route path="edit" element={<ProblemUpdate />} />
      <Route path="delete" element={<ProblemDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProblemRoutes;
