export const fetchPFAs = async (path) => {
  const token = localStorage.getItem('token');
  const authToken = `Bearer ${token}`;
  try {
    const response = await fetch(`${path}/pfas`, {
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
    console.error('Erreur lors de la récupération des données de pfas :', error);
    return [];
  }
};

export const fetchStage = async (path, id) => {
  const token = localStorage.getItem('token');
  const authToken = `Bearer ${token}`;
  try {
    const response = await fetch(`${path}/pfas/${id}`, {
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
    console.error('Erreur lors de la récupération des données du pfa :', error);
    return null;
  }
};