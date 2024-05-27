/* eslint-disable jsx-a11y/anchor-has-content */
/* eslint-disable no-unused-vars */
import * as React from 'react';
import Box from '@mui/material/Box';
import { DataGrid } from '@mui/x-data-grid';
import axios from 'axios';
import GroupeProjetForm from './GroupeProjetForm';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import { Button, Stack, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import { useUserData } from '../../../contexts/userDataContext';
import { fetchGroupeProjetByEncadrant, fetchGroupeProjets } from '../../../data/fetchGroupeProjet';
import { fetchFiliereSemestres } from '../../../data/fetchFiliereSemestre';
import { fetchEtudiantIngenieur, fetchEtudiantIngenieursByFiliereSemestre } from '../../../data/fetchEtudiant_ing';
// import ViewPfa from './ViewPfa';
// import { fetchProfessors } from '../../../data/fetchProfesseur';
import { Card, Col, Row, Table } from 'react-bootstrap';
import AddIcon from '@mui/icons-material/Add';
import avatar1 from '../../../assets/images/user/avatar-1.jpg';
import PfaForm from '../pfa/PfaForm';
import BorderColorIcon from '@mui/icons-material/BorderColor';
import AddCircleOutlineOutlinedIcon from '@mui/icons-material/AddCircleOutlineOutlined';
import FileDownloadIcon from '@mui/icons-material/FileDownload';
import PDFViewer from '../../pdf/PdfViewer';
import { fetchProfessors } from '../../../data/fetchProfesseur';
import ChangeEncadrantGrp from './ChangeEncadrantGrp';

export default function GroupeProjetList() {
  const [create, setCreate] = React.useState(false);
  const [createPFA, setCreatePFA] = React.useState(false);
  const [deleteConfirmationOpen, setDeleteConfirmationOpen] = React.useState(false);
  const [deleteTargetId, setDeleteTargetId] = React.useState(null);
  const [updating, setUpdating] = React.useState(false);
  // const [updatingPFA, setUpdatingPFA] = React.useState(false);
  const [updateGroupeProjet, setUpdateGroupeProjet] = React.useState(null);
  const [groupeProject, setGroupeProjet] = React.useState(null);
  const [updatePFA, setUpdatePFA] = React.useState(false);
  const [change, setChange] = React.useState(false);
  const {
    groupeProjets,
    updateGroupeProjets,
    path,
    updateFiliereSemestres,
    updateEtudiantIngenieurs,
    updateEtudiantIngenieur,
    etudiantIngenieur,
    userData,
    updateProfessors
  } = useUserData();

  React.useEffect(() => {
    const fetchDatas = async () => {
      try {
        console.log('groupe projet :', groupeProjets);

        if (!etudiantIngenieur) {
          const groupeData = await fetchGroupeProjets(path);
          updateGroupeProjets(groupeData);
        }
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    const fetchDataEtudiant = async () => {
      try {
        const etudiantData = await fetchEtudiantIngenieur(path, userData.id);
        setGroupeProjet(etudiantData.groupeProject);
        updateEtudiantIngenieur(etudiantData);
        console.log('groupe : ', etudiantData);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    const fetchDataProf = async () => {
      try {
        console.log('groupe : ', userData.id);
        const groupeData = await fetchGroupeProjetByEncadrant(path, userData.id);
        updateGroupeProjets(groupeData);
        console.log('groupe : ', groupeData);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    if (userData.roles.some((role) => role.name === 'ROLE_ADMIN')) {
      fetchDatas();
    } else if (userData.roles.some((role) => role.name === 'ROLE_ETUDIANT')) {
      fetchDataEtudiant();
    } else if (userData.roles.some((role) => role.name === 'ROLE_PROFESSOR')) {
      fetchDataProf();
    }
  }, []);

  const closeMoodal = () => {
    setCreate(false);
    setUpdating(false);
    setCreatePFA(false);
    setUpdatePFA(false);
    setChange(false);
  };
  const fetchEtuFileres = async () => {
    try {
      const id = etudiantIngenieur.filiereSemestre.id;
      const etudiantsFiliere = await fetchEtudiantIngenieursByFiliereSemestre(path, id);
      updateEtudiantIngenieurs(etudiantsFiliere);
      const FiliereSemestre = await fetchFiliereSemestres(path);
      updateFiliereSemestres(FiliereSemestre);
    } catch (error) {
      console.log('echec', etudiantIngenieur);
    }
  };

  const handleEdit = async () => {
    fetchEtuFileres();
    closeMoodal();
    setUpdateGroupeProjet(groupeProject);
    setUpdating(!updating);
  };

  const changeEncadrant = async (row) => {
    const profs = await fetchProfessors(path);
    updateProfessors(profs);
    closeMoodal();
    setChange(!change);
    setUpdateGroupeProjet(row);
  };

  const fCreate = async () => {
    fetchEtuFileres();
    closeMoodal();
    setCreate(!create);
  };

  const affects = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get(`${path}/groupe_projects/affectation`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      if (response.status == 200) {
        updateGroupeProjets(response.data);
        setDeleteConfirmationOpen(false);
      }
    } catch (error) {
      console.error('Error deleting groupe Projet:', error);
    }
  };

  const handlPfa = async () => {
    closeMoodal();
    setCreatePFA(!createPFA);
    console.log('hello ', createPFA);
  };

  const handlUpdatePfa = async () => {
    closeMoodal();
    setUpdatePFA(!updatePFA);
  };

  const handleDelete = async (id) => {
    try {
      const token = localStorage.getItem('token');

      await axios.delete(`http://localhost:8080/groupe_projects/${id}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      handlegroupeProjetCreated();
      setDeleteConfirmationOpen(false);
    } catch (error) {
      console.error('Error deleting groupe Projet:', error);
    }
  };

  const handlegroupeProjetCreated = async () => {
    try {
      const groupeData = await fetchGroupeProjets(path);
      updateGroupeProjets(groupeData);
      console.log('groupes : ', groupeData);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  const handleDeleteConfirmationOpen = (id) => {
    setDeleteTargetId(id);
    setDeleteConfirmationOpen(true);
  };

  const handleDeleteConfirmationClose = () => {
    setDeleteTargetId(null);
    setDeleteConfirmationOpen(false);
  };

  const columns = [
    {
      field: 'pfa',
      headerName: 'Titre Pfa',
      width: 150,
      renderCell: (params) => {
        if (params.row.pfa) {
          const title = params.row.pfa.title;
          return <div>{title}</div>;
        } else return <div>N/A</div>;
      }
    },
    {
      field: 'encadrant',
      headerName: 'Encadrant',
      width: 150,
      renderCell: (params) => {
        if (params.row.encadrant) {
          const nom = params.row.encadrant.firstName;
          const prenom = params.row.encadrant.lastName;
          return (
            <Box>
              {nom} {prenom}
            </Box>
          );
        } else {
          return <Box>Pas encadrant</Box>;
        }
      }
    },
    {
      field: 'students',
      headerName: 'Etudiants',
      width: 300,
      renderCell: (params) => {
        const etudiants = params.row.students;
        return (
          <Box>
            {etudiants.map((etudiant, index) => (
              <span key={index}>
                {etudiant.firstName} {etudiant.lastName} ,{' '}
              </span>
            ))}
          </Box>
        );
      }
    },
    {
      field: 'filiereSemestre',
      headerName: 'Filiere',
      width: 100,
      renderCell: (params) => {
        const filiere = params.row.filiereSemestre.filiere.name;
        const semestre = params.row.filiereSemestre.semestre.count;
        return <Box>{`${filiere} ${semestre}`}</Box>;
      }
    },
    {
      headerName: 'Actions',
      width: 350,
      renderCell: (params) => {
        return (
          <div key={params.row.id}>
            <Stack direction={'row'}>
              {userData.roles.some((role) => role.name === 'ROLE_ADMIN') && (
                <Box sx={{ marginX: 1 }}>
                  <Button
                    variant="contained"
                    color="primary"
                    size="small"
                    onClick={() => changeEncadrant(params.row)}
                    startIcon={<EditIcon />}
                  >
                    changer Encadrant
                  </Button>
                </Box>
              )}

              <Box>
                <Button
                  variant="contained"
                  color="error"
                  size="small"
                  onClick={() => handleDeleteConfirmationOpen(params.row.id)}
                  startIcon={<DeleteIcon />}
                >
                  Supprimer
                </Button>
              </Box>
            </Stack>
          </div>
        );
      }
    }
  ];

  return (
    <div>
      {userData.roles.some((role) => role.name === 'ROLE_ADMIN') || userData.roles.some((role) => role.name === 'ROLE_PROFESSOR') ? (
        <>
          <Box sx={{ padding: 3 }}>
            {userData.roles.some((role) => role.name === 'ROLE_ADMIN') && (
              <Box sx={{ width: '10%', padding: '8px' }}>
                <Button onClick={() => affects()} variant="contained" color="primary">
                  Affections
                </Button>
              </Box>
            )}
            <Box sx={{ height: 400, width: '100%' }}>
              <DataGrid rows={groupeProjets} columns={columns} pageSize={5} checkboxSelection disableSelectionOnClick />
            </Box>
          </Box>
        </>
      ) : (
        //section étudiants
        <Row>
          <Col>
            <Card className="user-list d-flex">
              <Card.Header>
                <Card.Title as="h5" style={{ display: 'flex', justifyContent: 'space-between' }}>
                  <span style={{}}>Détails projet</span>
                  <span className="text-end">
                    {!groupeProject ? (
                      <span style={{ marginLeft: '4px' }}>
                        <Button onClick={() => fCreate()} variant="contained" color="primary">
                          <AddIcon />
                        </Button>
                      </span>
                    ) : (
                      <span style={{ marginLeft: '4px' }}>
                        {!groupeProject.encadrant && (
                          <Button onClick={() => handleEdit()} variant="contained" color="warning">
                            <BorderColorIcon />
                          </Button>
                        )}
                      </span>
                    )}
                    {groupeProject && groupeProject.encadrant && (
                      <>
                        <span style={{ marginLeft: '2px' }}>
                          {!groupeProject.pfa && (
                            <Button onClick={() => handlPfa()} variant="contained" color="secondary">
                              <AddCircleOutlineOutlinedIcon />
                            </Button>
                          )}
                        </span>
                        <span style={{ marginLeft: '2px', marginRigth: '2px' }}>
                          {groupeProject.pfa && (
                            <Button onClick={() => handlUpdatePfa()} variant="contained" color="success">
                              <FileDownloadIcon />
                            </Button>
                          )}
                        </span>
                      </>
                    )}
                  </span>
                </Card.Title>
              </Card.Header>
              <Card.Body className="p-0">
                <Row>
                  <Col xl={4}>
                    <Card className="card-social">
                      <Card.Body className="border-bottom">
                        <div className="row align-items-center justify-content-center">
                          {groupeProject && (
                            <div className="col text-start">
                              {groupeProject.pfa && (
                                <div
                                  style={{
                                    display: 'flex',
                                    justifyContent: 'center',
                                    marginX: '10px',
                                    border: '2px solid',
                                    padding: '5px',
                                    fontFamily: 'cursive'
                                  }}
                                >
                                  <h3>{groupeProject.pfa.title}</h3>
                                </div>
                              )}
                              {groupeProject.encadrant && (
                                <div className="mt-5">
                                  <h5 className="text-c-blue mb-2">
                                    <span style={{ fontSize: '20px', fontWeight: 'bold' }}>Encadrant :</span>
                                    <span className="text-muted">
                                      {groupeProject.encadrant.firstName ? ' ' + groupeProject.encadrant.firstName : ''}{' '}
                                      {groupeProject.encadrant.lastName ? groupeProject.encadrant.lastName : ''}
                                    </span>
                                  </h5>
                                  <hr />
                                </div>
                              )}
                              {groupeProject.pfa && (
                                <div className="mt-2">
                                  <p>
                                    <span className="text-c-blue" style={{ fontSize: '20px', fontWeight: 'bold' }}>
                                      {' '}
                                      Description :{' '}
                                    </span>{' '}
                                    {groupeProject.pfa.description ? groupeProject.pfa.description : ''}
                                  </p>
                                  <hr />
                                </div>
                              )}
                              {groupeProject.pfa && (
                                <div className="mt-2 text-end">
                                  {groupeProject.pfa.url && (
                                    <p>
                                      <a href={groupeProject.pfa.url} className="btn btn-primary" target="_blank" rel="noreferrer">
                                        Le lien vers le rapport
                                      </a>
                                    </p>
                                  )}
                                  <hr />
                                </div>
                              )}
                            </div>
                          )}
                        </div>
                      </Card.Body>
                    </Card>
                  </Col>
                </Row>
                {groupeProject && (
                  <Row>
                    <Col>
                      <span className="text-center" style={{ fontSize: '25px', fontWeight: 'bold' }}>
                        <h3>Les membres du groupe</h3>
                      </span>
                      <Table responsive hover>
                        <thead>
                          <tr>
                            <th>Etudiant</th>
                            <th>Email</th>
                            <th>Telephone</th>
                            <th>CNE</th>
                            <th>CIN</th>
                          </tr>
                        </thead>
                        <tbody>
                          {groupeProject.students.map((data) => (
                            <tr key={data.id}>
                              {' '}
                              {/* Assuming each student object has a unique 'id' */}
                              <td>
                                <img className="rounded-circle" style={{ width: '40px' }} src={avatar1} alt="activity-user" />
                              </td>
                              <td>
                                <h6 className="mb-1">
                                  {data.firstName} {data.lastName}
                                </h6>
                                <p className="m-0">
                                  <span className="text-c-green">{data.email}</span>
                                </p>
                              </td>
                              <td>
                                <span className="pie_1">{data.telephone}</span>
                              </td>
                              <td>
                                <h6 className="m-0">{data.cne}</h6>
                              </td>
                              <td>
                                <h6 className="m-0">{data.cin}</h6>
                              </td>
                            </tr>
                          ))}
                        </tbody>
                      </Table>
                    </Col>
                  </Row>
                )}
              </Card.Body>
            </Card>
          </Col>
        </Row>
      )}
      <Dialog
        open={deleteConfirmationOpen}
        onClose={handleDeleteConfirmationClose}
        aria-labelledby="delete-dialog-title"
        aria-describedby="delete-dialog-description"
      >
        <DialogTitle id="delete-dialog-title">{'Delete groupeProjet'}</DialogTitle>
        <DialogContent>
          <DialogContentText id="delete-dialog-description">Voulez-vous vraiment supprimer ce groupe PFA?</DialogContentText>
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
      {create && <GroupeProjetForm open={create} />}
      {updating && <GroupeProjetForm open={updating} groupeToUpdate={updateGroupeProjet} />}
      {createPFA && <PfaForm open={createPFA} groupe={groupeProject} />}
      {updatePFA && <PfaForm open={updatePFA} pfaToUpdate={groupeProject.pfa} />}
      {change && <ChangeEncadrantGrp open={change} groupeToUpdate={updateGroupeProjet} />}
    </div>
  );
}
