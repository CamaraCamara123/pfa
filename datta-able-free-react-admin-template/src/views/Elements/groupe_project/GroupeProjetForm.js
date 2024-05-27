/* eslint-disable no-unused-vars */
import { useEffect, useState } from 'react';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import Button from '@mui/material/Button';
import { useUserData } from '../../../contexts/userDataContext';
import axios from 'axios';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import { Box } from '@mui/material';
import OutlinedInput from '@mui/material/OutlinedInput';
import Chip from '@mui/material/Chip';
import PropTypes from 'prop-types';
import { Col, Row } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250
    }
  }
};

export default function GroupeProjetForm({ open, groupeToUpdate }) {
  const [modalIsOpen, setModalIsOpen] = useState(open);
  const [pfa, setPfa] = useState(null);
  const [encadrant, setEncadrant] = useState(null);
  const [students, setStudents] = useState([]);
  const [filiereSemestre, setFiliereSemestre] = useState(null);
  const [message, setMessage] = useState('');
  const { path, etudiantIngenieurs, etudiantIngenieur } = useUserData();
  const navigate = useNavigate();

  const handleCloseModal = () => {
    setModalIsOpen(false);
    navigate('/groupeProjects');
  };

  useEffect(() => {
    if (groupeToUpdate) {
      const { pfa, encadrant, students, filiereSemestre } = groupeToUpdate;
      setPfa(pfa ? pfa.id : null);
      setEncadrant(encadrant ? encadrant.id : null);
      setStudents(students ? students.map((student) => student.id) : []);
      setFiliereSemestre(filiereSemestre ? filiereSemestre.id : null);
    } else {
      setFiliereSemestre(etudiantIngenieur.filiereSemestre.id);
    }
  }, [groupeToUpdate]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const sts = students.map((id) => ({ id }));
    try {
      const token = localStorage.getItem('token');
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      const response = await axios[groupeToUpdate ? 'put' : 'post'](
        `${path}/groupe_projects${groupeToUpdate ? `/${groupeToUpdate.id}` : ''}`,
        {
          pfa: { id: pfa },
          encadrant: { id: encadrant },
          filiereSemestre: { id: filiereSemestre },
          students: sts
        }
      );

      if (response.status === 200) {
        setMessage(groupeToUpdate ? 'Updated successful!' : 'Registration successful!');
        handleCloseModal();
      }
    } catch (error) {
      console.error('Erreur generale', error);
      setMessage(error.response.data.message || 'An error occurred');
    }
  };

  const handleChangeStudents = (event) => {
    const {
      target: { value }
    } = event;
    setStudents(typeof value === 'string' ? value.split(',') : value);
  };

  return (
    <Dialog open={modalIsOpen} onClose={handleCloseModal} fullWidth>
      <DialogTitle>Formulaire de groupe PFA</DialogTitle>
      <form onSubmit={handleSubmit}>
        <DialogContent>
          {message && <p className={message.includes('error') ? 'error-message' : 'success-message'}>{message}</p>}
          <Row>
            <Col>
              <FormControl sx={{ m: 1, width: 300 }}>
                <InputLabel id="demo-multiple-Etudiants-label">Etudiants</InputLabel>
                <Select
                  labelId="demo-multiple-Etudiants-label"
                  id="demo-multiple-Etudiants"
                  multiple
                  value={students}
                  onChange={handleChangeStudents}
                  input={<OutlinedInput id="select-multiple-Etudiants" label="Etudiants" />}
                  renderValue={(selected) => (
                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                      {selected.map((studentId) => {
                        const selectedStudent = etudiantIngenieurs.find((student) => student.id === studentId);
                        return <Chip key={studentId} label={selectedStudent ? selectedStudent.firstName : ''} />;
                      })}
                    </Box>
                  )}
                  MenuProps={MenuProps}
                >
                  {etudiantIngenieurs.map(({ id, firstName, lastName }) => (
                    <MenuItem key={id} value={id}>
                      {`${firstName} ${lastName}`}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
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

GroupeProjetForm.propTypes = {
  open: PropTypes.bool.isRequired,
  groupeToUpdate: PropTypes.object
};
