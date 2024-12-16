import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SearchQuery from './search-query';
import SearchItem from './search-item';
import Subscriber from './subscriber';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="search-query/*" element={<SearchQuery />} />
        <Route path="search-item/*" element={<SearchItem />} />
        <Route path="subscriber/*" element={<Subscriber />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
