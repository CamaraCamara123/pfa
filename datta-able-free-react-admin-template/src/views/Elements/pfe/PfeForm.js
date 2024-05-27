/* eslint-disable no-unused-vars */
import { useEffect, useState } from 'react';
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

export default function PFEForm({ open, PFEToUpdate, changeEncadrant, uploadFile }) {
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    start_date: '',
    end_date: '',
    encadrantTechnique: '',
    entreprise: '',
    ville: '',
    rapport: null,
    encadrantAcademique: null
  });

  const [message, setMessage] = useState({ type: '', content: '' });
  const { path, etudiantIngenieur, professors } = useUserData();
  const navigate = useNavigate();
  const [modalIsOpen, setModalIsOpen] = useState(open);

  useEffect(() => {
    if (PFEToUpdate) {
      setFormData({ ...PFEToUpdate });
    } else {
      resetForm();
    }
  }, [PFEToUpdate]);

  const resetForm = () => {
    setFormData({
      title: '',
      description: '',
      start_date: '',
      end_date: '',
      encadrantTechnique: '',
      entreprise: '',
      ville: '',
      rapport: null,
      encadrantAcademique: null
    });
  };

  const handleCloseModal = () => {
    navigate('/pfes');
    setModalIsOpen(false);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem('token');
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'multipart/form-data'
        }
      };

      const config1 = {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      };

      const formDataToSend = new FormData();

      for (const key in formData) {
        formDataToSend.append(key, formData[key]);
      }

      if (!changeEncadrant && !uploadFile) {
        let response;
        if (PFEToUpdate) {
          response = await axios.put(`${path}/pfes/${PFEToUpdate.id}`, formDataToSend, config);
        } else {
          formDataToSend.delete('encadrantAcademique');
          response = await axios.post(`${path}/ingStudents/admin/addPFE/${etudiantIngenieur.id}`, formDataToSend, config1);
        }
        if (response.status === 200) {
          setMessage({ type: 'success', content: PFEToUpdate ? 'Mise à jour avec succès!' : 'Enregistré avec succès!' });
          resetForm();
          setModalIsOpen(false);
        }
      } else {
        let response = null;
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
        if (!uploadFile && changeEncadrant) {
          const url = `${path}/pfes/change/${PFEToUpdate.id}/${formData.encadrantAcademique}`;
          response = await axios.post(url);
        } else {
          const formDataToSend = new FormData();
          formDataToSend.append('rapport', formData.rapport);
          response = await axios.put(`${path}/pfes/uploadRapport/${etudiantIngenieur.pfe.id}`, formDataToSend, config);
        }
        if (response.status === 200) {
          setMessage({ type: 'success', content: 'Mise à jour avec succès!' });
          setModalIsOpen(false);
        }
      }
    } catch (error) {
      console.error('Error:', error);
      setMessage({ type: 'error', content: error.response?.data?.message || 'Une erreur est survenue' });
    }
  };

  const handleFileChange = (e) => {
    setFormData({ ...formData, rapport: e.target.files[0] });
  };

  return (
    <Dialog open={modalIsOpen} onClose={handleCloseModal} fullWidth>
      <DialogTitle>{PFEToUpdate ? 'Update PFE' : 'Create PFE'}</DialogTitle>
      <form onSubmit={handleSubmit}>
        <DialogContent>
          {message.type === 'success' && <p className="success-message">{message.content}</p>}
          {message.type === 'error' && <p className="error-message">{message.content}</p>}
          {!changeEncadrant && !uploadFile && (
            <>
              <Row>
                <Col>
                  <TextField
                    label="Title"
                    name="title"
                    value={formData.title}
                    onChange={handleInputChange}
                    sx={{ marginBottom: '16px' }}
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
                    fullWidth
                    sx={{ marginBottom: '16px' }}
                    required
                  />
                </Col>
              </Row>
              <Row>
                <Col>
                  <TextField
                    label="Encadrant technique"
                    type="text"
                    name="encadrantTechnique"
                    value={formData.encadrantTechnique}
                    onChange={handleInputChange}
                    sx={{ marginBottom: '16px' }}
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
            </>
          )}
          <Row>
            {!changeEncadrant && !uploadFile && (
              <Col>
                <TextField
                  label="ville"
                  type="text"
                  name="ville"
                  value={formData.ville}
                  onChange={handleInputChange}
                  fullWidth
                  sx={{ marginBottom: '16px' }}
                />
              </Col>
            )}
            {changeEncadrant && (
              <Col>
                <FormControl style={{ width: 200 }}>
                  <InputLabel id="demo-simple-select-label">Changer encadrant</InputLabel>
                  <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={formData.encadrantAcademique}
                    label="Nom encadrant"
                    onChange={(e) => setFormData({ ...formData, encadrantAcademique: e.target.value })}
                  >
                    {professors.map((prof) => (
                      <MenuItem key={prof.id} value={prof.id}>
                        {prof.firstName} {prof.lastName}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>
              </Col>
            )}
            {uploadFile && (
              <Col style={{ border: '1px' }}>
                <input type="file" onChange={handleFileChange} />
              </Col>
            )}
          </Row>
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

PFEForm.propTypes = {
  open: PropTypes.bool.isRequired,
  PFEToUpdate: PropTypes.object,
  changeEncadrant: PropTypes.any,
  uploadFile: PropTypes.any
};
