import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/search-query">
        <Translate contentKey="global.menu.entities.searchQuery" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/search-item">
        <Translate contentKey="global.menu.entities.searchItem" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/subscriber">
        <Translate contentKey="global.menu.entities.subscriber" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
