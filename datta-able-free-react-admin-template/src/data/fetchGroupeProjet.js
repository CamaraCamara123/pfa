export const fetchGroupeProjets = async (path) => {
  const token = localStorage.getItem('token');
  const authToken = `Bearer ${token}`
  try {
    const response = await fetch(`${path}/groupe_projects`, {
      method: 'GET',
      headers: {
        'Authorization': authToken
      }
    });
    if (response.ok) {
      const data = await response.json();
      return data;
    }
  } catch (error) {
    console.error('Erreur lors de la récupération des groupes projet :', error);
    return [];
  }
};

export const fetchGroupeProjet = async (path, id) => {
  const token = localStorage.getItem('token');
  const authToken = `Bearer ${token}`
  try {
    const response = await fetch(`${path}/groupe_projects/${id}`, {
      headers: {
        'Authorization': authToken
      }
    });
    if (response.ok) {
      const data = await response.json();
      return data;
    }
  } catch (error) {
    console.error('Erreur lors de la récupération du groupe de projet :', error);
    return null;
  }
};

export const fetchGroupeProjetByEncadrant = async (path, id) => {
  const token = localStorage.getItem('token');
  const authToken = `Bearer ${token}`;
  try {
    const response = await fetch(`${path}/groupe_projects/encadrant/${id}`, {
      method: 'GET',
      headers: {
        'Authorization': authToken
      }
    });
    if (response.ok) {
      const data = await response.json();
      return data;
    }
  } catch (error) {
    console.error('Erreur lors de la récupération des groupes projet de l\'encadrant :', error);
    return [];
  }
};

export const fetchGroupeProjetByFiliereSemestre = async (path, id) => {
  const token = localStorage.getItem('token');
  const authToken = `Bearer ${token}`
  try {
    const response = await fetch(`${path}/groupe_projects/filiereSemestre/${id}`, {
      method: 'GET',
      headers: {
        'Authorization': authToken
      }
    });
    if (response.ok) {
      const data = await response.json();
      return data;
    }
  } catch (error) {
    console.error('Erreur lors de la récupération des groupes projet filiereSemestre :', error);
    return [];
  }
};

export const fetchGroupeProjetByEtudiantIngenieur = async (path, id) => {
  const token = localStorage.getItem('token');
  const authToken = `Bearer ${token}`
  try {
    const response = await fetch(`${path}/groupe_projects/encadrant/groupeProject/${id}`, {
      method: 'GET',
      headers: {
        'Authorization': authToken
      }
    });
    if (response.ok) {
      const data = await response.json();
      console.log("mon groupe pfa", data)
      return data;
    }
  } catch (error) {
    console.error('Erreur lors de la récupération du groupe projet de l\'etudiant :', error);
  }
};