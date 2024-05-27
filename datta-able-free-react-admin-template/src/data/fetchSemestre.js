export const fetchSemestres = async (path) => {
  const token = localStorage.getItem('token');
  const authToken = `Bearer ${token}`;
  try {
    const response = await fetch(`${path}/semestres`, {
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
    console.error('Erreur lors de la récupération des semestres :', error);
  }
};

export const fetchSemestre = async (path, id) => {
  const token = localStorage.getItem('token');
  const authToken = `Bearer ${token}`;
  try {
    const response = await fetch(`${path}/semestres/${id}`, {
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
    console.error('Erreur lors de la récupération des données du semestre :', error);
  }
};