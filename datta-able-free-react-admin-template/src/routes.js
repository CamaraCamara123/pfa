import React, { Suspense, Fragment, lazy } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';

import Loader from './components/Loader/Loader';
import AdminLayout from './layouts/AdminLayout';

import { BASE_URL } from './config/constant';
import Signin1 from './views/auth/signin/SignIn1';
// import { AuthProvider } from './contexts/authContext';
// import { UserDataProvider } from './contexts/userDataContext';

export const renderRoutes = (routes = []) => (
  <Suspense fallback={<Loader />}>
    <Routes>
      <Route path="/login" element={<Signin1 />} index />
      {routes.map((route, i) => {
        const Guard = route.guard || Fragment;
        const Layout = route.layout || Fragment;
        const Element = route.element;

        return (
          <Route
            key={i}
            path={route.path}
            element={
              <Guard>
                <Layout>{route.routes ? renderRoutes(route.routes) : <Element props={true} />}</Layout>
              </Guard>
            }
          />
        );
      })}
    </Routes>
  </Suspense>
);

const routes = [
  // {
  //   exact: 'true',
  //   path: '/login',
  //   element: lazy(() => import('./views/auth/signin/SignIn1'))
  // },
  // {
  //   exact: 'true',
  //   path: '/auth/signin-1',
  //   element: lazy(() => import('./views/auth/signin/SignIn1'))
  // },
  // {
  //   exact: 'true',
  //   path: '/auth/signup-1',
  //   element: lazy(() => import('./views/auth/signup/SignUp1'))
  // },
  // {
  //   exact: 'true',
  //   path: '/auth/reset-password-1',
  //   element: lazy(() => import('./views/auth/reset-password/ResetPassword1'))
  // },
  {
    path: '*',
    layout: AdminLayout,
    routes: [
      {
        exact: 'true',
        path: '/app/dashboard/default',
        element: lazy(() => import('./views/dashboard'))
      },
      {
        exact: 'true',
        path: '/basic/button',
        element: lazy(() => import('./views/ui-elements/basic/BasicButton'))
      },
      {
        exact: 'true',
        path: '/ingenieurs',
        element: lazy(() => import('./views/Elements/etudiantIngenieur/EtudiantIngenieur'))
      },
      {
        exact: 'true',
        path: '/groupeProjects',
        element: lazy(() => import('./views/Elements/groupe_project/GroupeProjetList'))
      },
      {
        exact: 'true',
        path: '/filieres',
        element: lazy(() => import('./views/Elements/filiere/FiliereList'))
      },
      {
        exact: 'true',
        path: '/stages',
        element: lazy(() => import('./views/Elements/stage/StageList'))
      },
      {
        exact: 'true',
        path: '/pfes',
        element: lazy(() => import('./views/Elements/pfe/PfeList'))
      },
      {
        exact: 'true',
        path: '/pfas',
        element: lazy(() => import('./views/Elements/pfa/PfaList'))
      },
      // {
      //   exact: 'true',
      //   path: '/stages',
      //   element: lazy(() => import('./views/Elements/stage/'))
      // },
      // {
      //   exact: 'true',
      //   path: '/pfes',
      //   element: lazy(() => import('./views/Elements/'))
      // },
      {
        exact: 'true',
        path: '/basic/badges',
        element: lazy(() => import('./views/ui-elements/basic/BasicBadges'))
      },
      {
        exact: 'true',
        path: '/basic/breadcrumb',
        element: lazy(() => import('./views/ui-elements/basic/BasicBreadcrumb'))
      },
      {
        exact: 'true',
        path: '/basic/pagination',
        element: lazy(() => import('./views/ui-elements/basic/BasicPagination'))
      },
      {
        exact: 'true',
        path: '/basic/collapse',
        element: lazy(() => import('./views/ui-elements/basic/BasicCollapse'))
      },
      {
        exact: 'true',
        path: '/basic/tabs-pills',
        element: lazy(() => import('./views/ui-elements/basic/BasicTabsPills'))
      },
      {
        exact: 'true',
        path: '/basic/typography',
        element: lazy(() => import('./views/ui-elements/basic/BasicTypography'))
      },
      {
        exact: 'true',
        path: '/forms/form-basic',
        element: lazy(() => import('./views/forms/FormsElements'))
      },
      {
        exact: 'true',
        path: '/tables/bootstrap',
        element: lazy(() => import('./views/tables/BootstrapTable'))
      },
      {
        exact: 'true',
        path: '/charts/nvd3',
        element: lazy(() => import('./views/charts/nvd3-chart'))
      },
      {
        exact: 'true',
        path: '/sample-page',
        element: lazy(() => import('./views/extra/SamplePage'))
      },
      {
        path: '*',
        exact: 'true',
        element: () => <Navigate to={BASE_URL} />
      }
    ]
  }
];

export default routes;
