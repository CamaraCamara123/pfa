/* eslint-disable no-unused-vars */
import { useEffect, useRef, useState } from 'react';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import PropTypes from 'prop-types';
import { Col, Row } from 'react-bootstrap';
import { FormControl, InputLabel, MenuItem, Select } from '@mui/material';
import { useUserData } from '../../../contexts/userDataContext';

export default function StageForm({ open, stageToUpdate }) {
  // const formRef = useRef(null);
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    start_date: '',
    end_date: '',
    encadrantTechnique: '',
    entreprise: '',
    ville: '',
    rapport: null
  });

  const [message, setMessage] = useState({ type: '', content: '' });
  const { path, etudiantIngenieur, professors, userData } = useUserData();
  const navigate = useNavigate();
  const [modalIsOpen, setModalIsOpen] = useState(open);

  useEffect(() => {
    if (stageToUpdate) {
      setFormData({ ...stageToUpdate });
    } else {
      // resetForm();
    }
  }, [stageToUpdate]);

  const resetForm = () => {
    formRef.current.reset();
    setFormData({
      title: '',
      description: '',
      start_date: '',
      end_date: '',
      encadrantTechnique: '',
      entreprise: '',
      ville: '',
      rapport: null
    });
  };

  const handleCloseModal = () => {
    navigate('/stages');
    setModalIsOpen(false);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(formData);
    try {
      console.log(' etu : ', etudiantIngenieur);
      const token = localStorage.getItem('token');
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'multipart/form-data'
        }
      };
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      const url = stageToUpdate ? `${path}/stages/${stageToUpdate.id}` : `${path}/ingStudents/admin/addStage/${userData.id}`;
      let response = null;
      if (stageToUpdate) {
        response = await axios['put'](url, formData, config);
      } else {
        response = await axios['post'](url, formData);
      }
      if (response.status === 200) {
        setMessage({ type: 'success', content: stageToUpdate ? 'Mise à jour avec succès!' : 'Enregistré avec succès!' });
        handleCloseModal();
      }
    } catch (error) {
      console.error('Error:', error);
      setMessage({ type: 'error', content: error.response?.data?.message || 'Une erreur est survenue!' });
    }
  };

  const handleFileChange = (e) => {
    setFormData({ ...formData, rapport: e.target.files[0] });
  };

  return (
    <Dialog open={modalIsOpen} onClose={handleCloseModal} fullWidth>
      <DialogTitle>{stageToUpdate ? 'Upload rapport' : 'Créer Stage'}</DialogTitle>
      <form onSubmit={handleSubmit}>
        <DialogContent>
          {message.type === 'success' && <p className="success-message">{message.content}</p>}
          {message.type === 'error' && <p className="error-message">{message.content}</p>}
          {!stageToUpdate && (
            <>
              <Row>
                <Col>
                  <TextField
                    label="Titre"
                    name="title"
                    sx={{ marginBottom: '16px' }}
                    value={formData.title}
                    onChange={handleInputChange}
                    fullWidth
                    required
                  />
                </Col>
                <Col>
                  <TextField
                    label="Description"
                    name="description"
                    value={formData.description}
                    onChange={handleInputChange}
                    sx={{ marginBottom: '16px' }}
                    fullWidth
                    required
                  />
                </Col>
              </Row>
              <Row>
                <Col>
                  <TextField
                    label="Date début"
                    type="date"
                    name="start_date"
                    value={formData.start_date}
                    onChange={handleInputChange}
                    sx={{ marginBottom: '16px' }}
                    fullWidth
                    required
                  />
                </Col>
                <Col>
                  <TextField
                    label="Date fin"
                    type="date"
                    name="end_date"
                    value={formData.end_date}
                    onChange={handleInputChange}
                    sx={{ marginBottom: '16px' }}
                    fullWidth
                    required
                  />
                </Col>
              </Row>
              <Row>
                <Col>
                  <TextField
                    label="Encadrant Technique"
                    type="text"
                    name="encadrantTechnique"
                    value={formData.encadrantTechnique}
                    sx={{ marginBottom: '16px' }}
                    onChange={handleInputChange}
                    fullWidth
                  />
                </Col>
                <Col>
                  <TextField
                    label="Entreprise"
                    type="text"
                    name="entreprise"
                    value={formData.entreprise}
                    onChange={handleInputChange}
                    sx={{ marginBottom: '16px' }}
                    fullWidth
                  />
                </Col>
              </Row>
              <Row>
                <Col>
                  <TextField
                    label="Ville"
                    sx={{ marginBottom: '16px', marginTop: '16px' }}
                    type="text"
                    name="ville"
                    value={formData.ville}
                    onChange={handleInputChange}
                    fullWidth
                  />
                </Col>
              </Row>
            </>
          )}
          {stageToUpdate && (
            <Row>
              <Col style={{ border: '1px', marginBottom: '16px' }}>
                <input type="file" onChange={handleFileChange} />
              </Col>
            </Row>
          )}
        </DialogContent>
        <DialogActions>
          <Button color="secondary" onClick={handleCloseModal}>
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

StageForm.propTypes = {
  open: PropTypes.bool.isRequired,
  stageToUpdate: PropTypes.object,
  changeEncadrant: PropTypes.any
};
