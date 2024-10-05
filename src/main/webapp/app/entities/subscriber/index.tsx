import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Subscriber from './subscriber';
import SubscriberDetail from './subscriber-detail';

const SubscriberRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Subscriber />} />
    <Route path=":id">
      <Route index element={<SubscriberDetail />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SubscriberRoutes;
