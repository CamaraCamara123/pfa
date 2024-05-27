import React from 'react';
import { BrowserRouter } from 'react-router-dom';

// auth provider

import routes, { renderRoutes } from './routes';
import { AuthProvider } from './contexts/authContext';
import { UserDataProvider } from './contexts/userDataContext';

const App = () => {
  return (
    <React.Fragment>
      <BrowserRouter>
      <AuthProvider>
      <UserDataProvider>
        {renderRoutes(routes)}
      </UserDataProvider>
      </AuthProvider>
      </BrowserRouter>
    </React.Fragment>
  );
};

export default App;
