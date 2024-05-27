import React, { createContext, useContext, useState } from 'react';
import PropTypes from 'prop-types';
// Créez un contexte UserDataContext
export const UserDataContext = createContext();

// Créez un Hook personnalisé pour utiliser le contexte UserDataContext
export function useUserData() {
  return useContext(UserDataContext);
}

// Composant Provider pour envelopper l'application et fournir les données utilisateur
export function UserDataProvider({ children }) {
  const [modalIsOpen, setModalIsOpen] = useState(false);

  const [userData, setUserData] = useState(null);
  const [userIdData, setUserIdData] = useState();

  const [salles, setSalles] = useState([]);
  const [salle, setsalle] = useState();

  const [etudiantIngenieurs, setEtudiantIngenieurs] = useState([]);
  const [etudiantIngenieur, setEtudiantIngenieur] = useState(null);

  const [etudiantPrepas, setEtudiantPrepas] = useState([]);
  const [etudiantPrepa, setEtudiantPrepa] = useState(null);

  const [groupes, setGroupes] = useState([]);
  const [groupe, setGroupe] = useState(null);

  const [sections, setSections] = useState([]);
  const [section, setSection] = useState(null);

  const [semestres, setSemestres] = useState([]);
  const [semestre, setSemestre] = useState(null);

  const [filieres, setFilieres] = useState([]);
  const [filiere, setFiliere] = useState(null);

  const [filiereSemestres, setFiliereSemestres] = useState([]);
  const [filiereSemestre, setFiliereSemestre] = useState(null);

  const [PFAs, setPFAs] = useState([]);
  const [PFA, setPFA] = useState(null);

  const [groupeProjets, setGroupeProjets] = useState([]);
  const [groupeProjet, setGroupeProjet] = useState(null);

  const [PFEs, setPFEs] = useState([]);
  const [PFE, setPFE] = useState(null);

  const [stages, setStages] = useState([]);
  const [stage, setStage] = useState(null);

  const [professors, setProfessors] = useState([]);
  const [professor, setProfessor] = useState(null);

  const [timeTables, setTimeTables] = useState([]);
  const [timeTable, setTimeTable] = useState(null);

  const [timeSlots, setTimeSlots] = useState([]);
  const [timeSlot, setTimeSlot] = useState(null);

  const [modules, setModules] = useState([]);
  const [module, setModule] = useState(null);

  const [elements, setElements] = useState([]);
  const [element, setElement] = useState(null);

  const [path, setPath] = useState('http://localhost:8080');

  const openModal = () => {
    setModalIsOpen(true);
    setPath('http://localhost:8080');
  };
  const closeModal = () => {
    setModalIsOpen(false);
  };

  const updateUserData = (data) => {
    setUserData(data);
  };
  const updateUserIdData = (data) => {
    setUserIdData(data);
  };

  const updateSalles = (data) => {
    setSalles(data);
  };
  const updateSalle = (data) => {
    setsalle(data);
  };

  const updateEtudiantIngenieurs = (data) => {
    setEtudiantIngenieurs(data);
  };
  const updateEtudiantIngenieur = (data) => {
    setEtudiantIngenieur(data);
  };

  const updateEtudiantPrepas = (data) => {
    setEtudiantPrepas(data);
  };
  const updateEtudiantPrepa = (data) => {
    setEtudiantPrepa(data);
  };

  const updateGroupes = (data) => {
    setGroupes(data);
  };
  const updateGroupe = (data) => {
    setGroupe(data);
  };

  const updateSections = (data) => {
    setSections(data);
  };
  const updateSection = (data) => {
    setSection(data);
  };

  const updateSemestres = (data) => {
    setSemestres(data);
  };
  const updateSemestre = (data) => {
    setSemestre(data);
  };

  const updateFilieres = (data) => {
    setFilieres(data);
  };
  const updateFiliere = (data) => {
    setFiliere(data);
  };

  const updateFiliereSemestres = (data) => {
    setFiliereSemestres(data);
  };
  const updateFiliereSemestre = (data) => {
    setFiliereSemestre(data);
  };

  const updateGroupeProjets = (data) => {
    setGroupeProjets(data);
  };

  const updateGroupeProjet = (data) => {
    setGroupeProjet(data);
  };
  const updatePFEs = (data) => {
    setPFEs(data);
  };
  const updatePFE = (data) => {
    setPFE(data);
  };

  const updateStages = (data) => {
    setStages(data);
  };
  const updateStage = (data) => {
    setStage(data);
  };

  const updateProfessors = (data) => {
    setProfessors(data);
  };
  const updateProfessor = (data) => {
    setProfessor(data);
  };

  const updateTimeTables = (data) => {
    setTimeTables(data);
  };
  const updateTimeTable = (data) => {
    setTimeTable(data);
  };

  const updateTimeSlots = (data) => {
    setTimeSlots(data);
  };
  const updateTimeSlot = (data) => {
    setTimeSlot(data);
  };

  const updateModules = (data) => {
    setModules(data);
  };
  const updateModule = (data) => {
    setModule(data);
  };

  const updateElements = (data) => {
    setElements(data);
  };
  const updateElement = (data) => {
    setElement(data);
  };

  const updatePFAs = (data) => {
    setPFAs(data);
  };
  const updatePFA = (data) => {
    setPFA(data);
  };

  return (
    <UserDataContext.Provider
      value={{
        userData,
        updateUserData,
        userIdData,
        updateUserIdData,
        salles,
        updateSalles,
        salle,
        updateSalle,
        etudiantIngenieurs,
        updateEtudiantIngenieurs,
        etudiantIngenieur,
        updateEtudiantIngenieur,
        etudiantPrepas,
        updateEtudiantPrepas,
        etudiantPrepa,
        updateEtudiantPrepa,
        groupes,
        updateGroupes,
        groupe,
        updateGroupe,
        sections,
        updateSections,
        section,
        updateSection,
        semestres,
        updateSemestres,
        semestre,
        updateSemestre,
        filiereSemestres,
        updateFiliereSemestres,
        filiereSemestre,
        updateFiliereSemestre,
        modalIsOpen,
        closeModal,
        openModal,
        filieres,
        updateFilieres,
        filiere,
        updateFiliere,
        PFEs,
        updatePFEs,
        PFE,
        updatePFE,
        groupeProjets,
        updateGroupeProjets,
        groupeProjet,
        updateGroupeProjet,
        PFAs,
        updatePFAs,
        PFA,
        updatePFA,
        stages,
        updateStages,
        stage,
        updateStage,
        professors,
        updateProfessors,
        professor,
        updateProfessor,
        elements,
        updateElements,
        element,
        updateElement,
        modules,
        updateModules,
        module,
        updateModule,
        timeTables,
        updateTimeTables,
        timeTable,
        updateTimeTable,
        timeSlots,
        updateTimeSlots,
        timeSlot,
        updateTimeSlot,
        path
      }}
    >
      {children}
    </UserDataContext.Provider>
  );
}

UserDataProvider.propTypes = {
  children: PropTypes.node.isRequired
};
