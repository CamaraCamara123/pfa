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

export default function ChangeEncadrantGrp({ open, groupeToUpdate }) {
  const [message, setMessage] = useState({ type: '', content: '' });
  const { path, professors } = useUserData();
  const navigate = useNavigate();
  const [modalIsOpen, setModalIsOpen] = useState(open);
  const [encadrantAcademique, setEncadrantAcademique] = useState(null);

  const handleCloseModal = () => {
    navigate('/groupeProjects');
    setModalIsOpen(false);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem('token');
      let response = null;
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;

      const url = `${path}/groupe_projects/change/${groupeToUpdate.id}/${encadrantAcademique}`;
      response = await axios.post(url);
      if (response.status === 200) {
        setMessage({ type: 'success', content: 'Mise à jour avec succès!' });
        setModalIsOpen(false);
      }
    } catch (error) {
      console.error('Error:', error);
      setMessage({ type: 'error', content: error.response?.data?.message || 'Une erreur est survenue' });
    }
  };

  return (
    <Dialog open={modalIsOpen} onClose={handleCloseModal} fullWidth>
      <DialogTitle>Changer Encadrant</DialogTitle>
      <form onSubmit={handleSubmit}>
        <DialogContent>
          {message.type === 'success' && <p className="success-message">{message.content}</p>}
          {message.type === 'error' && <p className="error-message">{message.content}</p>}
          <Row>
            <Col>
              <FormControl style={{ width: 200 }}>
                <InputLabel id="demo-simple-select-label">Changer encadrant</InputLabel>
                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  value={encadrantAcademique}
                  label="Nom encadrant"
                  onChange={(e) => setEncadrantAcademique(e.target.value)}
                >
                  {professors.map((prof) => (
                    <MenuItem key={prof.id} value={prof.id}>
                      {prof.firstName} {prof.lastName}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Col>
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

ChangeEncadrantGrp.propTypes = {
  open: PropTypes.bool.isRequired,
  groupeToUpdate: PropTypes.object
};
