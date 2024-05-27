/* eslint-disable no-unused-vars */
import * as React from 'react';
import Box from '@mui/material/Box';
import { DataGrid } from '@mui/x-data-grid';
import axios from 'axios';
import PFEForm from './PfeForm';
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Stack } from '@mui/material';
import { useUserData } from '../../../contexts/userDataContext';
import { Card, Col, Row } from 'react-bootstrap';
import { fetchEncadrantPfes, fetchPfes } from '../../../data/fetchPfe';
import { fetchEtudiantIngenieur } from '../../../data/fetchEtudiant_ing';
import { fetchProfessors } from '../../../data/fetchProfesseur';

export default function PfeList() {
  const [create, setCreate] = React.useState(false);
  const [deleteConfirmationOpen, setDeleteConfirmationOpen] = React.useState(false);
  const [deleteTargetId, setDeleteTargetId] = React.useState(null);
  const [updating, setUpdating] = React.useState(false);
  const [changeEn, setChangeEn] = React.useState(false);
  const [updatePFE, setUpdatePFE] = React.useState(null);
  const [pfe, setPfe] = React.useState();
  const [pfes, setPfes] = React.useState([]);
  const [upload, setUpload] = React.useState(false);
  const [load, setLoad] = React.useState(0);
  const { path, userData, updateEtudiantIngenieur, updateProfessors, PFEs } = useUserData();

  React.useEffect(() => {
    const fetchData = async () => {
      try {
        const token = localStorage.getItem('token');
        const config = {
          headers: {
            Authorization: `Bearer ${token}`
          }
        };

        if (userData.roles.some((role) => ['ROLE_ADMIN'].includes(role.name))) {
          if (PFEs.length === 0) {
            const response = await axios.get(`${path}/pfes`, config);
            setPfes(response.data);
          } else {
            setPfes(PFEs);
            console.log(PFEs);
          }
        } else if (userData.roles.some((role) => role.name === 'ROLE_ETUDIANT')) {
          const response = await axios.get(`${path}/ingStudents/admin/${userData.id}`, config);
          setPfe(response.data.pfe);
          updateEtudiantIngenieur(response.data);
        } else if (userData.roles.some((role) => role.name === 'ROLE_PROFESSOR')) {
          const response = await axios.get(`${path}/pfes/encadrant/${userData.id}`, config);
          setPfes(response.data);
        }
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    if (!pfe && pfes.length === 0 && load < 4) {
      fetchData();
      setLoad(load + 1);
    }
  }, [path, updateEtudiantIngenieur, userData.id, userData.roles, pfe, pfes]);

  const handleEdit = (PFE) => {
    closeModals();
    setUpdatePFE(PFE);
    setUpdating(!updating);
  };

  const handleDelete = async (id) => {
    try {
      const token = localStorage.getItem('token');
      await axios.delete(`${path}/pfes/${id}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      fetchData();
      setDeleteConfirmationOpen(false);
    } catch (error) {
      console.error('Error deleting PFE:', error);
    }
  };

  const closeModals = () => {
    setCreate(false);
    setUpdating(false);
    setChangeEn(false);
    setUpload(false);
  };

  const handleDeleteConfirmationOpen = (id) => {
    setDeleteTargetId(id);
    setDeleteConfirmationOpen(true);
  };

  const handleDeleteConfirmationClose = () => {
    setDeleteTargetId(null);
    setDeleteConfirmationOpen(false);
  };

  const uploadRapport = () => {
    closeModals();
    setUpload(!upload);
  };
  const affectations = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get(`${path}/pfes/affectations`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      if (response.status === 200) {
        setPfes(response.data);
        setDeleteConfirmationOpen(false);
      }
    } catch (error) {
      console.error('Error fetching affectations:', error);
    }
  };

  const fetchProfs = async () => {
    try {
      const profs = await fetchProfessors(path);
      updateProfessors(profs);
    } catch (error) {
      console.log('echec', error);
    }
  };

  const changerEncadrant = (row) => {
    fetchProfs();
    setPfe(row);
    closeModals();
    setChangeEn(!changeEn);
  };

  const renderPageEtudiant = () => {
    return (
      <Box sx={{ height: 400, width: '100%' }}>
        <Row>
          <Col>
            <Box></Box>
            <Card className="user-list">
              <Card.Header>
                <Card.Title as="h5" style={{ display: 'flex', justifyContent: 'end' }}>
                  {!pfe ? (
                    <Button onClick={() => setCreate(true)} variant="contained" color="primary">
                      Créer
                    </Button>
                  ) : (
                    <Stack direction="row">
                      <Box sx={{ marginX: 1 }}>
                        <Button variant="contained" color="primary" size="small" onClick={() => handleEdit(pfe)}>
                          Editer
                        </Button>
                      </Box>
                      <Box>
                        <Button variant="contained" color="success" size="small" onClick={() => uploadRapport()}>
                          upload
                        </Button>
                      </Box>
                    </Stack>
                  )}
                </Card.Title>
              </Card.Header>
              <Card.Body className="p-0">
                {pfe ? (
                  <Row>
                    <Col>
                      <Card className="card-social">
                        <Card.Body className="border-bottom">
                          <div className="row align-items-center justify-content-center">
                            <div className="col text-start">
                              <Box
                                sx={{
                                  display: 'flex',
                                  justifyContent: 'center',
                                  marginX: '10px',
                                  border: '2px solid',
                                  padding: '5px',
                                  fontFamily: 'cursive'
                                }}
                              >
                                <h3>{pfe.title}</h3>
                              </Box>
                              <Box sx={{ margin: '15px', border: '2px' }}>
                                <h5 className="text-muted mb-0">
                                  <span style={{ fontSize: '20px', fontWeight: 'bold' }}>Etudiant :</span>
                                  <span className="text-muted">
                                    {userData.firstName} {userData.lastName}
                                  </span>
                                </h5>
                              </Box>
                              <Box sx={{ margin: '15px' }}>
                                <h5 className="text-muted mb-0">
                                  <span style={{ fontSize: '20px', fontWeight: 'bold' }}>Emal :</span>
                                  <span className="text-muted">{userData.email}</span>
                                </h5>
                              </Box>
                              <hr />
                              <Box sx={{ margin: '15px', border: '2px' }}>
                                <h5 className="text-muted mb-0">
                                  <span style={{ fontSize: '20px', fontWeight: 'bold' }}>Encadrant Technique : </span>
                                  <span className="text-muted">{pfe.encadrantTechnique}</span>
                                </h5>
                              </Box>
                              {pfe.encadrantAcademique && (
                                <Box sx={{ margin: '15px', border: '2px' }}>
                                  <h5 className="text-muted mb-0">
                                    <span style={{ fontSize: '20px', fontWeight: 'bold' }}>Encadrant academique :</span>
                                    <span className="text-muted">
                                      {pfe.encadrantAcademique.firstName} {pfe.encadrantAcademique.lastName}
                                    </span>
                                  </h5>
                                </Box>
                              )}
                              <hr />
                              <Row style={{ margin: '5px' }}>
                                <Col>
                                  <p>
                                    <span className="text-muted" style={{ fontSize: '20px', fontWeight: 'bold' }}>
                                      {' '}
                                      Entrepise :{' '}
                                    </span>{' '}
                                    {pfe.entreprise}
                                  </p>
                                </Col>
                                <Col>
                                  <p>
                                    <span className="text-muted" style={{ fontSize: '20px', fontWeight: 'bold' }}>
                                      {' '}
                                      Ville :{' '}
                                    </span>{' '}
                                    {pfe.ville}
                                  </p>
                                </Col>
                              </Row>
                              <Row style={{ margin: '5px' }}>
                                <Col>
                                  <p>
                                    <span className="text-muted" style={{ fontSize: '20px', fontWeight: 'bold' }}>
                                      {' '}
                                      Début :{' '}
                                    </span>{' '}
                                    {pfe.start_date}
                                  </p>
                                </Col>
                                <Col>
                                  <p>
                                    <span className="text-muted" style={{ fontSize: '20px', fontWeight: 'bold' }}>
                                      {' '}
                                      Fin :{' '}
                                    </span>{' '}
                                    {pfe.end_date}
                                  </p>
                                </Col>
                              </Row>
                              <hr />
                              <Row>
                                <Col>
                                  <p>
                                    <span className="text-muted" style={{ fontSize: '20px', fontWeight: 'bold' }}>
                                      {' '}
                                      Description :{' '}
                                    </span>{' '}
                                    {pfe.description}
                                  </p>
                                </Col>
                              </Row>
                              <hr />
                              {pfe.urlRapport && (
                                <Box className="text-end">
                                  <a href={pfe.urlRapport} target="_blank" rel="noreferrer" className="btn btn-primary">
                                    Voir rapport
                                  </a>
                                </Box>
                              )}
                            </div>
                          </div>
                        </Card.Body>
                      </Card>
                    </Col>
                  </Row>
                ) : (
                  <Box>
                    <h3>Aucun projet</h3>
                  </Box>
                )}
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Box>
    );
  };

  const columns = [
    {
      field: 'title',
      headerName: 'Libelle',
      width: 150,
      editable: true
    },
    {
      field: 'description',
      headerName: 'Description',
      width: 250,
      editable: true
    },
    {
      field: 'start_date',
      headerName: 'Début',
      width: 150,
      editable: true
    },
    {
      field: 'end_date',
      headerName: 'Fin',
      width: 150,
      editable: true
    },
    {
      field: 'encadrantTechnique',
      headerName: 'Encadrant Technique',
      width: 150,
      editable: true
    },
    {
      field: 'encadrantAcademique',
      headerName: 'Encadrant Academique',
      width: 150,
      renderCell: (params) => {
        if (params.row.encadrantAcademique) {
          const nom = params.row.encadrantAcademique.firstName;
          const prenom = params.row.encadrantAcademique.lastName;

          return (
            <div>
              {nom} {prenom}
            </div>
          );
        } else return <div>N/A</div>;
      }
    },
    {
      field: 'entreprise',
      headerName: 'Entrepise',
      width: 150,
      editable: true
    },
    {
      field: 'ville',
      headerName: 'Ville',
      width: 150,
      editable: true
    },
    {
      headerName: 'Action',
      width: 300,
      renderCell: (params) => {
        return (
          <div key={params.row.id}>
            <Stack direction={'row'}>
              {params.row.urlRapport && (
                <Box sx={{ marginX: 1 }}>
                  <a href={params.row.urlRapport} target="_blank" rel="noreferrer">
                    voir rapport
                  </a>
                </Box>
              )}
              {params.row.encadrantAcademique && userData.roles.some((role) => role.name === 'ROLE_ADMIN') && (
                <Box sx={{ marginX: 1 }}>
                  <Button onClick={() => changerEncadrant(params.row)} variant="contained" color="primary">
                    Changer encadrant
                  </Button>
                </Box>
              )}
            </Stack>
          </div>
        );
      }
    }
  ];

  return (
    <div>
      <Box sx={{ padding: 3 }}>
        {userData?.roles.some((role) => ['ROLE_ADMIN', 'ROLE_PROFESSOR'].includes(role.name)) ? (
          <Box sx={{ height: 400, width: '100%' }}>
            <Box sx={{ width: '10%', padding: '8px' }}>
              {userData.roles.some((role) => role.name === 'ROLE_ADMIN') && (
                <Row>
                  <Col>
                    <Button onClick={affectations} variant="contained" color="primary">
                      Affectations
                    </Button>
                  </Col>
                </Row>
              )}
            </Box>
            <DataGrid rows={pfes} columns={columns} pageSize={5} checkboxSelection disableSelectionOnClick />
          </Box>
        ) : (
          renderPageEtudiant()
        )}
      </Box>

      <Dialog
        open={deleteConfirmationOpen}
        onClose={handleDeleteConfirmationClose}
        aria-labelledby="delete-dialog-title"
        aria-describedby="delete-dialog-description"
      >
        <DialogTitle id="delete-dialog-title">{'Delete PFE'}</DialogTitle>
        <DialogContent>
          <DialogContentText id="delete-dialog-description">Are you sure you want to delete this PFE?</DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleDeleteConfirmationClose} color="primary">
            Fermer
          </Button>
          <Button onClick={() => handleDelete(deleteTargetId)} color="error" autoFocus>
            Supprimer
          </Button>
        </DialogActions>
      </Dialog>
      {create && <PFEForm open={create} />}
      {updating && <PFEForm open={updating} PFEToUpdate={updatePFE} />}
      {upload && <PFEForm open={upload} PFEToUpdate={pfe} uploadFile={upload} />}
      {changeEn && <PFEForm open={changeEn} PFEToUpdate={pfe} changeEncadrant={pfe.encadrantAcademique} />}
    </div>
  );
}
