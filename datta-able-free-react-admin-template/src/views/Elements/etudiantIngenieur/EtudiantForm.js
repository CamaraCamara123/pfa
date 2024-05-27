/* eslint-disable prettier/prettier */
import { useEffect, useState } from 'react';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import { useNavigate } from 'react-router-dom';
import { fetchEtudiantIngenieurs } from '../../../data/fetchEtudiant_ing';
import { useUserData } from '../../../contexts/userDataContext';
import PropTypes from 'prop-types';
import { Col, Row } from 'react-bootstrap';
import { FormControl, InputLabel, MenuItem, Select } from '@mui/material';

export default function EtudiantForm({ open, userToUpdate }) {
    const [modalIsOpen, setModalIsOpen] = useState(open);
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [telephone, setTelephone] = useState('');
    const [dateNaissance, setDateNaissance] = useState('');
    const [lieuNaissance, setLieuNaissance] = useState('');
    const [cin, setCin] = useState('');
    const [cne, setCne] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [filiereSemestre, setFiliereSemestre] = useState(null);
    const [roles, setRoles] = useState([]);
    const { updateEtudiantIngenieurs, path, filiereSemestres } = useUserData();
    const navigate = useNavigate();

    const handleCloseModal = () => {
        console.log(roles);
        navigate("/ingenieurs");
        setModalIsOpen(false);
    };

    useEffect(() => {
        if (userToUpdate) {
            setFirstName(userToUpdate.firstName || '');
            setLastName(userToUpdate.lastName || '');
            setUsername(userToUpdate.username || '');
            setEmail(userToUpdate.email || '');
            setPassword(userToUpdate.password || '');
            setTelephone(userToUpdate.telephone || '');
            setDateNaissance(userToUpdate.dateNaissance || '');
            setLieuNaissance(userToUpdate.lieuNaissance || '');
            setCin(userToUpdate.cin);
            setCne(userToUpdate.cne);
            setFiliereSemestre(userToUpdate.filiereSemestre ? userToUpdate.filiereSemestre.id : null);
            setRoles(userToUpdate.roles || []);
        }
    }, [userToUpdate]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const today = new Date().toISOString().slice(0, 10);
        // setFiliereSemestre(etudiantIngenieur.filiereSemestre.id);
        try {
            const parsedCreatedAt = new Date();
            //   const createdAt = parsedCreatedAt.toISOString().split('T')[0];
            const updatedAt = parsedCreatedAt.toISOString().split('T')[0];
            const token = localStorage.getItem('token');
            const authToken = `Bearer ${token}`;
            const requestBody = {
                firstName,
                lastName,
                username,
                email,
                cin,
                cne,
                password,
                lieuNaissance,
                filiereSemestre: { id: filiereSemestre },
                telephone,
                isEnabled: 1,
                updatedAt,
                dateNaissance,
                roles: [{ id: 1 }]
            };
            if (userToUpdate) {
                try {
                    const response = await fetch(`${path}/ingStudents/admin/update/${userToUpdate.id}`, {
                        method: 'PUT',
                        headers: {
                            'Authorization': authToken,
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(requestBody)
                    });

                    if (response.ok) {
                        const etudiantIng = await response.json();
                        console.log('Nouveau étudiant enregistré :', etudiantIng);
                        setSuccessMessage('Inscription réussie !');
                        setErrorMessage('');
                        handleCloseModal();
                    }
                } catch (error) {
                    console.error('Erreur lors de la mise à jour de l\'étudiant :', error);
                }
            } else {
                try {
                    const response = await fetch(`${path}/ingStudents/admin/save?createdAt=${today}`, {
                        method: 'POST',
                        headers: {
                            'Authorization': authToken,
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(requestBody)
                    });

                    if (response.ok) {
                        const etudiantIng = await response.json();
                        fetchEtudiantIngenieurs(path, updateEtudiantIngenieurs);
                        console.log('Nouveau étudiant enregistré :', etudiantIng);
                        setSuccessMessage('Inscription réussie !');
                        setErrorMessage('');
                        handleCloseModal();
                    }
                } catch (error) {
                    console.error('Erreur lors de l\'enregistrement de l\'étudiant :', error);
                }
            }
        } catch (error) {
            console.error('Erreur generale', error);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        switch (name) {
            case 'firstName':
                setFirstName(value);
                break;
            case 'lastName':
                setLastName(value);
                break;
            case 'username':
                setUsername(value);
                break;
            case 'email':
                setEmail(value);
                break;
            case 'password':
                setPassword(value);
                break;
            case 'telephone':
                setTelephone(value);
                break;
            case 'dateNaissance':
                setDateNaissance(value);
                break;
            case 'lieuNaissance':
                setLieuNaissance(value);
                break;
            case 'cin':
                setCin(value);
                break;
            case 'cne':
                setCne(value);
                break;
            default:
                break;
        }
    };

    return (
        <Dialog open={modalIsOpen} onClose={handleCloseModal} fullWidth>
            <DialogTitle>Formulaire Etudiant</DialogTitle>
            <form onSubmit={handleSubmit}>
                <DialogContent>
                    {successMessage && <p className="success-message">{successMessage}</p>}
                    {errorMessage && <p className="error-message">{errorMessage}</p>}
                    <Row>
                        <Col>
                            <TextField
                                label="Nom"
                                name="firstName"
                                value={firstName}
                                onChange={handleInputChange}
                                fullWidth
                                sx={{ marginBottom: '16px' }}
                                required
                            />
                        </Col>
                        <Col>
                            <TextField
                                label="Prénom"
                                name="lastName"
                                value={lastName}
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
                                label="Username"
                                name="username"
                                value={username}
                                onChange={handleInputChange}
                                fullWidth
                                sx={{ marginBottom: '16px' }}
                                required
                            />
                        </Col>
                        <Col>
                            <TextField
                                label="Email"
                                name="email"
                                type="email"
                                value={email}
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
                                label="CIN"
                                name="cin"
                                type="text"
                                value={cin}
                                onChange={handleInputChange}
                                fullWidth
                                sx={{ marginBottom: '16px' }}
                                required
                            />
                        </Col>
                        <Col>
                            <TextField
                                label="CNE"
                                name="cne"
                                type="text"
                                value={cne}
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
                                label="Mot de passe"
                                name="password"
                                type="password"
                                value={password}
                                onChange={handleInputChange}
                                fullWidth
                                sx={{ marginBottom: '16px' }}
                                required
                            />
                        </Col>
                        <Col>
                            <TextField
                                label="Telephone"
                                name="telephone"
                                type="tel"
                                value={telephone}
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
                                label="Date of Birth"
                                name="dateNaissance"
                                type="date"
                                value={dateNaissance}
                                onChange={handleInputChange}
                                fullWidth
                                sx={{ marginBottom: '16px' }}
                                required
                            />
                        </Col>
                        <Col>
                            <TextField
                                label="Place of Birth"
                                name="lieuNaissance"
                                value={lieuNaissance}
                                onChange={handleInputChange}
                                fullWidth
                                sx={{ marginBottom: '16px' }}
                                required
                            />
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <FormControl style={{ width: 150, marginBottom: '16px' }}>
                                <InputLabel id="demo-simple-select-label1">Semestre</InputLabel>
                                <Select
                                    labelId="demo-simple-select-label1"
                                    id="semestre-filiere"
                                    value={filiereSemestre}
                                    label="Le semestre"
                                    onChange={(e) => setFiliereSemestre(e.target.value)}
                                >
                                    {filiereSemestres.map((fs) => (
                                        <MenuItem key={fs.id} value={fs.id}>
                                            {fs.filiere.name} {fs.semestre.count}
                                        </MenuItem>
                                    ))}
                                </Select>
                            </FormControl>
                        </Col>
                    </Row>
                </DialogContent>
                <DialogActions>
                    <Button color="error" onClick={handleCloseModal}>
                        Close
                    </Button>
                    <Button type="submit" variant="contained" style={{ backgroundColor: 'green', color: 'white' }}>
                        Save
                    </Button>
                </DialogActions>
            </form>
        </Dialog>
    );
}

EtudiantForm.propTypes = {
    open: PropTypes.bool.isRequired,
    userToUpdate: PropTypes.object
};
