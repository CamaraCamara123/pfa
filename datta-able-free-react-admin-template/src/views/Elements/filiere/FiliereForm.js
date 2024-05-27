import { useEffect, useState } from 'react';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import { useNavigate } from 'react-router-dom';
import { fetchFilieres } from '../../../data/fetchFiliere';
import { useUserData } from '../../../contexts/userDataContext';
import { Col, Row } from 'react-bootstrap';
import PropTypes from 'prop-types';

export default function FiliereForm({ open, filiereToUpdate }) {
  const [modalIsOpen, setModalIsOpen] = useState(open);
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const { updateFilieres, path } = useUserData();
  const navigate = useNavigate();

  const handleCloseModal = () => {
    navigate('/filieres');
    setModalIsOpen(false);
  };

  const fetchData = async () => {
    try {
      const filiereData = await fetchFilieres(path);
      updateFilieres(filiereData);
      console.log('filieres : ', filiereData);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };
  useEffect(() => {
    if (filiereToUpdate) {
      setName(filiereToUpdate.name || '');
      setDescription(filiereToUpdate.description || '');
    }
  }, [filiereToUpdate]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const requestBody = {
      name,
      description
    };
    try {
      const token = localStorage.getItem('token');
      const authToken = `Bearer ${token}`;
      if (filiereToUpdate) {
        try {
          const response = await axios.put(`${path}/filieres/${filiereToUpdate.id}`, {
            method: 'PUT',
            headers: {
              'Authorization': authToken,
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
          });

          if (response.status === 200) {
            fetchData();
            setSuccessMessage('Mise à jour avec succes!');
            setErrorMessage('');
            setModalIsOpen(false);
          }
        } catch (error) {
          console.error("Erreur lors de l'enregistrement de la filiere", error);
          setErrorMessage(error.response.data.message);
          setSuccessMessage('');
        }
      } else {
        try {
          const response = await fetch(`${path}/filieres`, {
            method: 'POST',
            headers: {
              'Authorization': authToken,
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
          });

          if (response.status === 200) {
            const filiere_ = response.data;
            fetchData();
            console.log('Nouveau etudiant enregistré :', filiere_);
            setSuccessMessage('Enregistré avec succes!');
            setErrorMessage('');
            setModalIsOpen(false);
          }
        } catch (error) {
          console.error("Erreur lors de l'enregistrement de la filiere ", error);
          setErrorMessage(error.response.data.message);
          setSuccessMessage('');
        }
      }
    } catch (error) {
      console.error('Erreur generale', error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    switch (name) {
      case 'name':
        setName(value);
        break;
      case 'description':
        setDescription(value);
        break;
      default:
        break;
    }
  };

  return (
    <Dialog open={modalIsOpen} onClose={handleCloseModal} fullWidth>
      <DialogTitle>Formulaire Filière</DialogTitle>
      <form onSubmit={handleSubmit}>
        <DialogContent>
          {successMessage && <p className="success-message">{successMessage}</p>}
          {errorMessage && <p className="error-message">{errorMessage}</p>}

          <Row>
            <Col>
              <TextField
                label="Libelle"
                name="name"
                value={name}
                onChange={handleInputChange}
                fullWidth
                sx={{ marginBottom: '16px' }}
                required
              />
            </Col>
            <Col>
              <TextField
                label="Description"
                name="description"
                value={description}
                onChange={handleInputChange}
                fullWidth
                sx={{ marginBottom: '16px' }}
                required
              />
            </Col>
          </Row>
        </DialogContent>
        <DialogActions>
          <Button color="error" onClick={handleCloseModal}>
            Fermer
          </Button>
          <Button type="submit" variant="contained" style={{ backgroundColor: 'green', color: 'white' }}>
            Envoyer
          </Button>
        </DialogActions>
      </form>
    </Dialog>
  );
}

FiliereForm.propTypes = {
  open: PropTypes.bool.isRequired,
  filiereToUpdate: PropTypes.object
};
