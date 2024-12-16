import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SearchQuery from './search-query';
import SearchQueryDetail from './search-query-detail';
import SearchQueryUpdate from './search-query-update';
import SearchQueryDeleteDialog from './search-query-delete-dialog';

const SearchQueryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SearchQuery />} />
    <Route path="new" element={<SearchQueryUpdate />} />
    <Route path=":id">
      <Route index element={<SearchQueryDetail />} />
      <Route path="edit" element={<SearchQueryUpdate />} />
      <Route path="delete" element={<SearchQueryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SearchQueryRoutes;
