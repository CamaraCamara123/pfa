export const fetchPfes = async (path) => {
  const token = localStorage.getItem('token');
  const authToken = `Bearer ${token}`;
  try {
    const response = await fetch(`${path}/pfes`, {
      headers: {
        'Authorization': authToken
      }
    });
    if (response.ok) {
      const data = await response.json();
      return data;
    }
  } catch (error) {
    console.error('Erreur lors de la récupération des données de PFEs :', error);
  }
};

export const fetchPFE = async (path, id) => {
  const token = localStorage.getItem('token');
  const authToken = `Bearer ${token}`;
  try {
    const response = await fetch(`${path}/pfes/${id}`, {
      headers: {
        'Authorization': authToken
      }
    });
    if (response.ok) {
      const data = await response.json();
      return data;
    }
  } catch (error) {
    console.error('Erreur lors de la récupération des données du pfe :', error);
    return null;
  }
};

export const fetchEncadrantPfes = async (path, id) => {
  const token = localStorage.getItem('token');
  const authToken = `Bearer ${token}`;
  try {
    const response = await fetch(`${path}/pfes/encadrant/${id}`, {
      headers: {
        'Authorization': authToken
      }
    });
    if (response.ok) {
      const data = await response.json();
      return data;
    }
  } catch (error) {
    console.error('Erreur lors de la récupération des données de PFEs :', error);
  }
};