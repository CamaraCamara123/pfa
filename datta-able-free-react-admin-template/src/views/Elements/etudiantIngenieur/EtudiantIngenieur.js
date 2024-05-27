/* eslint-disable no-unused-vars */
import * as React from 'react';
import Box from '@mui/material/Box';
import { DataGrid } from '@mui/x-data-grid';
import { useNavigate } from 'react-router-dom';
import { Stack, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Button } from '@mui/material';
import { useUserData } from '../../../contexts/userDataContext';
import EtudiantForm from './EtudiantForm';
import { fetchEtudiantIngenieurs } from '../../../data/fetchEtudiant_ing';
import AddIcon from '@mui/icons-material/Add';
import { fetchFiliereSemestres } from '../../../data/fetchFiliereSemestre';

export default function EtudiantIngenieurList() {
  const [create, setCreate] = React.useState(false);
  const [deleteConfirmationOpen, setDeleteConfirmationOpen] = React.useState(false);
  const [deleteTargetId, setDeleteTargetId] = React.useState(null);
  const [updating, setUpdating] = React.useState(false);
  const [updatingEtudiant, setUpdatingEtudiant] = React.useState(null);
  // const [etudiants, setEtudiants] = React.useState([]);
  const {
    updateEtudiantIngenieur,
    updatePFEs,
    path,
    updateEtudiantIngenieurs,
    etudiantIngenieurs,
    updateFiliereSemestres,
    updateGroupeProjets
  } = useUserData();
  const navigate = useNavigate();

  React.useEffect(() => {
    const fetchData = async () => {
      try {
        const etudiantsData = await fetchEtudiantIngenieurs(path);
        updateEtudiantIngenieurs(etudiantsData);
        console.log('ingenieurs : ', etudiantsData);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, []);

  const handleEdit = async (etudiant) => {
    setCreate(false);
    setUpdatingEtudiant(etudiant);
    handleFiliereSemestre();
    setUpdating(!updating);
  };

  const fCreate = async () => {
    handleFiliereSemestre();
    setUpdating(false);
    setCreate(!create);
    handleEtudiantIngCreated();
  };

  const handleStage = async (user) => {
    updateEtudiantIngenieur(user);
    navigate('/stages');
  };

  const handlePFE = async (user) => {
    if (user.pfe) {
      let pff = [];
      pff.push(user.pfe);
      console.log('le pfe : ', pff);
      updateEtudiantIngenieur(user);
      navigate('/pfes');
      await updatePFEs(pff);
    } else {
      alert('Aucun pfe');
    }
  };

  const handlePFA = async (user) => {
    updateEtudiantIngenieur(user);
    if (user.groupeProject) {
      await updateGroupeProjets([user.groupeProject]);
      navigate('/groupeProjects');
    } else {
      alert('Aucun projet pour le moment');
    }
  };

  const handleDelete = async (id) => {
    try {
      const token = localStorage.getItem('token');
      const authToken = `Bearer ${token}`;
      await fetch(`http://localhost:8080/ingStudents/admin/delete/${id}`, {
        method: 'DELETE',
        headers: {
          Authorization: authToken,
          'Content-Type': 'application/json'
        }
      });

      handleEtudiantIngCreated();
      setDeleteConfirmationOpen(false);
    } catch (error) {
      console.error('Erreur de suppression:', error);
    }
  };

  const handleEtudiantIngCreated = async () => {
    try {
      const etudiantsData = await fetchEtudiantIngenieurs(path);
      updateEtudiantIngenieurs(etudiantsData);
      console.log('ingenieurs : ', etudiantsData);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  const handleFiliereSemestre = async () => {
    try {
      const fs = await fetchFiliereSemestres(path);
      updateFiliereSemestres(fs);
      console.log('filieres sem : ', fs);
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
      field: 'firstName',
      headerName: 'Nom',
      width: 100,
      editable: true
    },
    {
      field: 'lastName',
      headerName: 'Prenom',
      width: 100,
      editable: true
    },
    {
      field: 'cne',
      headerName: 'CNE',
      width: 100,
      editable: true
    },
    {
      field: 'filiereSemestre',
      headerName: 'Filiere',
      width: 150,
      valueGetter: (params) => {
        if (params) {
          return `${params.filiere.name} ${params.semestre.count}`;
        }
        return 'N/A';
      },
      renderCell: (params) => {
        if (params.row.filiereSemestre) {
          const filiere = params.row.filiereSemestre.filiere.name;
          const semestre = params.row.filiereSemestre.semestre.count;
          return (
            <Box>
              {filiere} {semestre}
            </Box>
          );
        } else {
          return <Box>N/A</Box>;
        }
      }
    },
    {
      field: 'email',
      headerName: 'Email',
      width: 150,
      editable: true
    },
    {
      field: 'telephone',
      headerName: 'Telephone',
      width: 100,
      editable: true
    },
    {
      field: 'dateNaissance',
      headerName: 'Date naissance',
      width: 100,
      editable: true
    },
    {
      headerName: 'Actions',
      width: 450,
      renderCell: (params) => {
        return (
          <div key={params.row.id}>
            <Stack direction={'row'}>
              <Box sx={{ marginX: 1 }}>
                <Button variant="contained" color="primary" size="small" onClick={() => handleEdit(params.row)}>
                  Editer
                </Button>
              </Box>

              <Box sx={{ marginX: 1 }}>
                <Button variant="contained" color="error" size="small" onClick={() => handleDeleteConfirmationOpen(params.row.id)}>
                  Supprimer
                </Button>
              </Box>
              <Box sx={{ marginX: 1 }}>
                <Button variant="contained" color="primary" size="small" onClick={() => handleStage(params.row)}>
                  stages
                </Button>
              </Box>
              <Box sx={{ marginX: 1 }}>
                <Button variant="contained" color="primary" size="small" onClick={() => handlePFA(params.row)}>
                  PFA
                </Button>
              </Box>
              <Box sx={{ marginX: 1 }}>
                <Button variant="contained" color="primary" size="small" onClick={() => handlePFE(params.row)}>
                  PFE
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
      <Box sx={{ padding: 3 }}>
        <Box sx={{ width: '10%', padding: '8px' }}>
          <Button onClick={() => fCreate()} variant="contained" color="primary">
            <AddIcon />
          </Button>
        </Box>
        <Box sx={{ height: 400, width: '100%' }}>
          <DataGrid rows={etudiantIngenieurs} columns={columns} pageSize={5} checkboxSelection disableSelectionOnClick />
        </Box>
      </Box>

      <Dialog
        open={deleteConfirmationOpen}
        onClose={handleDeleteConfirmationClose}
        aria-labelledby="delete-dialog-title"
        aria-describedby="delete-dialog-description"
      >
        <DialogTitle id="delete-dialog-title">{'Delete student'}</DialogTitle>
        <DialogContent>
          <DialogContentText id="delete-dialog-description">Voulez vous vraiment supprimer cet étudiant?</DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleDeleteConfirmationClose} color="primary">
            Annuler
          </Button>
          <Button onClick={() => handleDelete(deleteTargetId)} color="error" autoFocus>
            Supprimer
          </Button>
        </DialogActions>
      </Dialog>
      {create && <EtudiantForm open={create} />}
      {updating && <EtudiantForm open={updating} userToUpdate={updatingEtudiant} />}
    </div>
  );
}
