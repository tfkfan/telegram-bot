import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SearchItem from './search-item';

const SearchItemRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SearchItem />} />
  </ErrorBoundaryRoutes>
);

export default SearchItemRoutes;
