export const fetchFiliereSemestres = async (path) => {
  const token = localStorage.getItem('token');
  const authToken = `Bearer ${token}`
  try {
    const response = await fetch(`${path}/filiere_semestre`, {
      headers: {
        'Authorization': authToken,
        'Content-Type': 'application/json'
      }
    });
    if (response.ok) {
      const data = await response.json();
      return data;
    } 
  } catch (error) {
    console.error('Erreur lors de la récupération des filiere semestre :', error);
    return [];
  }
};

export const fetchFiliereSemestre = async (path, id) => {
  const token = localStorage.getItem('token');
  const authToken = `Bearer ${token}`
  try {
    const response = await fetch(`${path}/filiere_semestre/${id}`, {
      headers: {
        'Authorization': authToken,
        'Content-Type': 'application/json'
      }
    });
    if (response.ok) {
      const data = await response.json();
      return data;
    } 
  } catch (error) {
    console.error('Erreur lors de la récupération des données du filiere semestre :', error);
    return null;
  }
};

export const fetchFiliereSemestreBySemestre = async (path, id) => {
  const token = localStorage.getItem('token');
  const authToken = `Bearer ${token}`
  try {
    const response = await fetch(`${path}/filiere_semestre/semestre/${id}`, {
      headers: {
        'Authorization': authToken,
        'Content-Type': 'application/json'
      }
    });
    if (response.ok) {
      const data = await response.json();
      return data;
    } 
  } catch (error) {
    console.error('Erreur lors de la récupération des données du filiere semestre par semestre :', error);
    return null;
  }
};

export const fetchFiliereSemestreByFiliere = async (path, id) => {
  const token = localStorage.getItem('token');
  const authToken = `Bearer ${token}`
  try {
    const response = await fetch(`${path}/filiere_semestre/filiere/${id}`, {
      headers: {
        'Authorization': authToken,
        'Content-Type': 'application/json'
      }
    });
    if (response.ok) {
      const data = await response.json();
      return data;
    } 
  } catch (error) {
    console.error('Erreur lors de la récupération des données du filiere semestre par filiere :', error);
    return null;
  }
};