/* eslint-disable eqeqeq */
import React, { useContext, useEffect, useState } from 'react';

import { ConfigContext } from '../../../contexts/ConfigContext';
import useWindowSize from '../../../hooks/useWindowSize';

import NavLogo from './NavLogo';
import NavContent from './NavContent';
import navigation from '../../../menu-items';
import menuItems_etudaint from '../../../menu_etudiant';
import menuItems_prof from '../../../menu-items-professeurs';
import menuItems_etudaint_final from '../../../menu_etudiant_final';
import { useUserData } from '../../../contexts/userDataContext';
import { useAuth } from '../../../contexts/authContext';
import { useNavigate } from 'react-router-dom';
const Navigation = () => {
  const configContext = useContext(ConfigContext);
  const { layoutType, collapseMenu } = configContext.state;
  const windowSize = useWindowSize();
  const { updateUserData, path } = useUserData();
  const { logout } = useAuth();
  const [navItems, setNavItems] = useState({ items: [] });
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUserData = async () => {
      const token = localStorage.getItem('token');
      const authToken = `Bearer ${token}`;
      try {
        if (token) {
          const response = await fetch(`${path}/api/v1/auth/userinfo`, {
            headers: {
              Authorization: authToken
            }
          });
          if (response.ok) {
            const userData = await response.json();
            updateUserData(userData);
            localStorage.setItem('user', JSON.stringify(userData));
            if (userData.roles.some((role) => role.name === 'ROLE_ADMIN')) {
              setNavItems(navigation);
            } else if (userData.roles.some((role) => role.name === 'ROLE_PROFESSOR')) {
              setNavItems(menuItems_prof);
            } else if (userData.roles.some((role) => role.name === 'ROLE_ETUDIANT')) {
              const response1 = await fetch(`${path}/ingStudents/admin/${userData.id}`, {
                headers: {
                  Authorization: authToken
                }
              });
              if (response1.ok) {
                const data = await response1.json();
                console.log(data.filiereSemestre);
                if (data.filiereSemestre.semestre.count === 5) {
                  setNavItems(menuItems_etudaint_final);
                } else {
                  setNavItems(menuItems_etudaint);
                }
              } else {
                throw new Error("Erreur lors de la récupération des données de l'étudiant");
              }
            }
          } else {
            throw new Error('Erreur lors de la récupération des données utilisateur');
          }
        }
      } catch (error) {
        console.error('Erreur lors de la récupération des données utilisateur :', error);
        logout();
        navigate('/login');
      }
    };

    fetchUserData();
  }, []);

  let navClass = ['pcoded-navbar'];

  navClass = [...navClass, layoutType];

  if (windowSize.width < 992 && collapseMenu) {
    navClass = [...navClass, 'mob-open'];
  } else if (collapseMenu) {
    navClass = [...navClass, 'navbar-collapsed'];
  }

  let navBarClass = ['navbar-wrapper'];
  let navContent = null;
  if (navItems && navItems.items) {
    navContent = (
      <div className={navBarClass.join(' ')}>
        <NavLogo />
        <NavContent navigation={navItems.items} />
      </div>
    );
  }

  return (
    <React.Fragment>
      <nav className={navClass.join(' ')}>{navContent}</nav>
    </React.Fragment>
  );
};

export default Navigation;
