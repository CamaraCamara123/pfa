import { useEffect, useState } from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useUserData } from '../../../contexts/userDataContext';
import PropTypes from 'prop-types';
import axios from 'axios';
import { Col, Row } from 'react-bootstrap';

export default function PfaForm({ open, pfaToUpdate, groupe }) {
  const [modalIsOpen, setModalIsOpen] = useState(open);
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    start_date: null,
    rapport: null,
    end_date: null,
    successMessage: '',
    errorMessage: ''
  });

  const { title, description, start_date, rapport, end_date, successMessage, errorMessage } = formData;

  const { path } = useUserData();
  const navigate = useNavigate();

  const handleCloseModal = () => {
    navigate('/groupeProjects');
    setFormData({ ...formData, successMessage: '', errorMessage: '' });
    setModalIsOpen(false);
  };

  useEffect(() => {
    console.log('pfaToUpdload: ', pfaToUpdate);
    if (pfaToUpdate) {
      setFormData({
        ...formData,
        title: pfaToUpdate.title || '',
        description: pfaToUpdate.description || '',
        start_date: pfaToUpdate.start_date || null,
        end_date: pfaToUpdate.end_date || null,
        rapport: pfaToUpdate.url || null
      });
    }
  }, [pfaToUpdate]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const token = localStorage.getItem('token');
      const formData = new FormData(); // Create FormData object

      formData.append('title', title);
      formData.append('description', description);
      formData.append('start_date', start_date);
      formData.append('end_date', end_date);
      formData.append('rapport', rapport);

      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'multipart/form-data'
        }
      };

      let response;
      if (pfaToUpdate) {
        response = await axios.put(`${path}/pfas/${pfaToUpdate.id}`, formData, config);
      } else {
        response = await axios.post(`${path}/pfas/${groupe.id}`, formData, config);
      }

      if (response.status === 200) {
        setFormData({
          ...formData,
          successMessage: pfaToUpdate ? 'Mise à jour avec succès!' : 'Enregistré avec succès!',
          errorMessage: '',
          modalIsOpen: false
        });
        handleCloseModal();
      }
    } catch (error) {
      console.error('Error occurred while submitting PFA', error);
      setFormData({ ...formData, errorMessage: error.response ? error.response.data.message : 'An error occurred' });
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleFileChange = (e) => {
    setFormData({ ...formData, rapport: e.target.files[0] });
  };

  return (
    <Dialog open={modalIsOpen} onClose={handleCloseModal} fullWidth>
      <DialogTitle>Formulaire PFA</DialogTitle>
      <form onSubmit={handleSubmit}>
        <DialogContent>
          {successMessage && <p className="success-message">{successMessage}</p>}
          {errorMessage && <p className="error-message">{errorMessage}</p>}
          {!pfaToUpdate && (
            <>
              <Row>
                <Col>
                  <TextField
                    label="Titre PFA"
                    name="title"
                    variant="outlined"
                    value={title}
                    onChange={handleInputChange}
                    fullWidth
                    margin="normal"
                    required
                  />
                </Col>
                <Col>
                  <TextField
                    label="Description"
                    name="description"
                    variant="outlined"
                    value={description}
                    onChange={handleInputChange}
                    fullWidth
                    margin="normal"
                    required
                  />
                </Col>
              </Row>
              <Row>
                <Col>
                  <TextField
                    type="date"
                    label="Début"
                    name="start_date"
                    value={start_date}
                    onChange={handleInputChange}
                    fullWidth
                    margin="normal"
                  />
                </Col>
                <Col>
                  <TextField
                    type="date"
                    label="Fin"
                    name="end_date"
                    value={end_date}
                    onChange={handleInputChange}
                    fullWidth
                    margin="normal"
                  />
                </Col>
              </Row>
            </>
          )}
          {pfaToUpdate && (
            <Row>
              <Col style={{ border: '1px' }}>
                <input type="file" onChange={handleFileChange} />
              </Col>

              {pfaToUpdate.url && (
                <Col>
                  <a href={pfaToUpdate.url} target="_blank" rel="noreferrer">
                    Lien vers le rapport actuel.
                  </a>
                </Col>
              )}
            </Row>
          )}
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

PfaForm.propTypes = {
  open: PropTypes.bool.isRequired,
  pfaToUpdate: PropTypes.object,
  groupe: PropTypes.object
};
