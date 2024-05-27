export const fetchEtudiantIngenieurs = async (path) => {
  const token = localStorage.getItem('token');
  const authToken = `Bearer ${token}`;
  try {
    const response = await fetch(`${path}/ingStudents/admin/all`, {
      headers: {
        'Authorization': authToken
      }
    });
    if (response.ok) {
      const data = await response.json();
      return data;
    } 
  } catch (error) {
    console.error('Erreur lors de la récupération des données de l\'étudiant :', error);
    return [];
  }
};

export const fetchEtudiantIngenieursByFiliereSemestre = async (path,id) => {
  const token = localStorage.getItem('token');
  const authToken = `Bearer ${token}`;
  try {
    const response = await fetch(`${path}/ingStudents/admin/byFiliereSemestre/${id}`, {
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
    console.error('Erreur lors de la récupération des données de l\'étudiant :', error);
    return [];
  }
};

export const fetchEtudiantIngenieur = async (path, id) => {
  const token = localStorage.getItem('token');
  const authToken = `Bearer ${token}`;
  try {
    const response = await fetch(`${path}/ingStudents/admin/${id}`, {
      headers: {
        'Authorization': authToken,
        'Content-Type': 'application/json'
      }
    });
    if (response.ok) {
      const data = await response.json();
      // console.log(data);
      return data;
    } 
  } catch (error) {
    console.error('Erreur lors de la récupération des données de l\'étudiant :', error);
    return null;
  }
};