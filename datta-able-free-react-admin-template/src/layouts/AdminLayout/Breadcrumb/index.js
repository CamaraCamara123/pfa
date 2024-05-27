import React, { useState, useEffect } from 'react';
import { ListGroup } from 'react-bootstrap';
import { Link, useLocation } from 'react-router-dom';

import navigation from '../../../menu-items';
import navigationProfesseur from '../../../menu-items-professeurs';
import menuItems_etudaint from '../../../menu_etudiant';
import { BASE_TITLE } from '../../../config/constant';
import { useAuth } from '../../../contexts/authContext';
import { useNavigate } from 'react-router-dom';
import { useUserData } from '../../../contexts/userDataContext';
// import axios from 'axios';

const Breadcrumb = () => {
  const location = useLocation();

  const [main, setMain] = useState([]);
  const [item, setItem] = useState([]);
  const { logout, isLoggedIn } = useAuth();
  const navigate = useNavigate();
  const { updateUserData, path } = useUserData();

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
              console.log('admin');
              sidebar(navigation);
            } else if (userData.roles.some((role) => role.name === 'ROLE_PROFESSOR')) {
              console.log('prof');
              sidebar(navigationProfesseur);
            } else if (userData.roles.some((role) => role.name === 'ROLE_ETUDIANT')) {
              console.log('etudiant',menuItems_etudaint);
              sidebar(menuItems_etudaint);
            }
          } else {
            // Si la réponse n'est pas OK, lancez une erreur
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

  // console.log("l'utilisateur : ",userIdData);
  if (!isLoggedIn) {
    navigate('/login');
  }
  // console.log('user : ', etudiantIngenieurs);
  const sidebar = (navigationbar) => {
    navigationbar.items.map((item, index) => {
      if (item.type && item.type === 'group') {
        getCollapse(item, index);
      }
      return false;
    });
  };

  const getCollapse = (item, index) => {
    if (item.children) {
      item.children.filter((collapse) => {
        if (collapse.type && collapse.type === 'collapse') {
          getCollapse(collapse, index);
        } else if (collapse.type && collapse.type === 'item') {
          if (location.pathname === collapse.url) {
            setMain(item);
            setItem(collapse);
          }
        }
        return false;
      });
    }
  };

  let mainContent, itemContent;
  let breadcrumbContent = '';
  let title = '';

  if (main && main.type === 'collapse') {
    mainContent = (
      <ListGroup.Item as="li" bsPrefix=" " className="breadcrumb-item">
        <Link to="#">{main.title}</Link>
      </ListGroup.Item>
    );
  }

  if (item && item.type === 'item') {
    title = item.title;
    itemContent = (
      <ListGroup.Item as="li" bsPrefix=" " className="breadcrumb-item">
        <Link to="#">{title}</Link>
      </ListGroup.Item>
    );

    if (item.breadcrumbs !== false) {
      breadcrumbContent = (
        <div className="page-header">
          <div className="page-block">
            <div className="row align-items-center">
              <div className="col-md-12">
                <div className="page-header-title">
                  <h5 className="m-b-10">{title}</h5>
                </div>
                <ListGroup as="ul" bsPrefix=" " className="breadcrumb">
                  <ListGroup.Item as="li" bsPrefix=" " className="breadcrumb-item">
                    <Link to="/">
                      <i className="feather icon-home" />
                    </Link>
                  </ListGroup.Item>
                  {mainContent}
                  {itemContent}
                </ListGroup>
              </div>
            </div>
          </div>
        </div>
      );
    }

    document.title = title + BASE_TITLE;
  }

  return <React.Fragment>{breadcrumbContent}</React.Fragment>;
};

export default Breadcrumb;
