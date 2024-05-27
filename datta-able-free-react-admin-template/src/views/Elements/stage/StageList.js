import * as React from 'react';
import Box from '@mui/material/Box';
import { DataGrid } from '@mui/x-data-grid';
import axios from 'axios';
import StageForm from './StageForm';
import { Button, Stack, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import { useUserData } from '../../../contexts/userDataContext';
import { fetchStages, fetchStagesByStudent } from '../../../data/fetchStage';

export default function StageList() {
  const [create, setCreate] = React.useState(false);
  const [deleteConfirmationOpen, setDeleteConfirmationOpen] = React.useState(false);
  const [deleteTargetId, setDeleteTargetId] = React.useState(null);
  const [updating, setUpdating] = React.useState(false);
  const [updateStage, setupdateStage] = React.useState(null);
  const { stages, updateStages, etudiantIngenieur, path, userData } = useUserData();

  React.useEffect(() => {
    const fetchDataEtudiant = async () => {
      try {
        if (userData.roles.some((role) => role.name === 'ROLE_ADMIN') || userData.roles.some((role) => role.name === 'ROLE_PROFESSOR')) {
          if (etudiantIngenieur) {
            const stageData = await fetchStagesByStudent(path, etudiantIngenieur.id);
            updateStages(stageData);
            console.log('groupe : ', stageData);
          } else {
            const stageData = await fetchStages(path);
            updateStages(stageData);
            console.log('groupe : ', stageData);
          }
        } else if (userData.roles.some((role) => role.name === 'ROLE_ETUDIANT')) {
          const stageData = await fetchStagesByStudent(path, userData.id);
          updateStages(stageData);
          console.log('groupe : ', stageData);
        }
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };
    fetchDataEtudiant();
  }, []);
  const handleEdit = async (filiere) => {
    setCreate(false);
    setupdateStage(filiere);
    setUpdating(!updating);
  };

  const fCreate = async () => {
    setUpdating(false);
    setCreate(!create);
  };

  const handleDelete = async (id) => {
    try {
      const token = localStorage.getItem('token');

      await axios.delete(`http://localhost:8080/stages/${id}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      fetchDataEtudiant();
      setDeleteConfirmationOpen(false);
    } catch (error) {
      console.error('Error deleting filiere:', error);
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
      field: 'ingStudent',
      headerName: 'Etudiant',
      width: 150,
      renderCell: (params) => {
        if (params.row.ingStudent) {
          const nom = params.row.ingStudent.firstName;
          const prenom = params.row.ingStudent.lastName;
          return (
            <div>
              {nom} {prenom}
            </div>
          );
        } else return <div>N/A</div>;
      }
    },
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
      field: 'encadrantTechnique',
      headerName: 'Encadrant Technique',
      width: 150,
      editable: true
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
      headerName: 'Actions',
      width: 300,
      renderCell: (params) => {
        return (
          <div key={params.row.id}>
            <Stack direction={'row'}>
              {userData.roles.some((role) => role.name === 'ROLE_ETUDIANT') && (
                <Box sx={{ marginX: 1 }}>
                  <Button variant="contained" color="primary" size="small" onClick={() => handleEdit(params.row)}>
                    Upload
                  </Button>
                </Box>
              )}
              {userData.roles.some((role) => role.name === 'ROLE_ADMIN') && (
                <Box>
                  <Button variant="contained" color="error" size="small" onClick={() => handleDeleteConfirmationOpen(params.row.id)}>
                    Supprimer
                  </Button>
                </Box>
              )}
              <Box>
                <a
                  href={params.row.urlRapport}
                  target="_blank"
                  rel="noreferrer"
                  className="btn btn-warning"
                  style={{
                    height: '32px',
                    marginLeft: '3px',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    marginTop: '10px'
                  }}
                >
                  rapport
                </a>
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
          {userData.roles.some((role) => role.name === 'ROLE_ETUDIANT') && (
            <Button onClick={() => fCreate()} variant="contained" color="primary">
              Créer
            </Button>
          )}
        </Box>
        <Box sx={{ height: 400, width: '100%' }}>
          <DataGrid rows={stages} columns={columns} pageSize={5} checkboxSelection disableSelectionOnClick />
        </Box>
      </Box>

      <Dialog
        open={deleteConfirmationOpen}
        onClose={handleDeleteConfirmationClose}
        aria-labelledby="delete-dialog-title"
        aria-describedby="delete-dialog-description"
      >
        <DialogTitle id="delete-dialog-title">{'Delete filiere'}</DialogTitle>
        <DialogContent>
          <DialogContentText id="delete-dialog-description">Voulez-vous vraiment supprimer ce stage?</DialogContentText>
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
      {create && <StageForm open={create} />}
      {updating && <StageForm open={updating} stageToUpdate={updateStage} />}
    </div>
  );
}
