export const fetchProfessors = async (path) => {
  try {
    const token = localStorage.getItem("token");
    const authToken = `Bearer ${token}`;
    const response = await fetch(`${path}/professors`, {
      headers: {
        'Authorization': authToken,
        'Content-Type': 'Application/json'
      },
    });
    if (response.ok) {
      const data = await response.json();
      return data;
    } 
  } catch (error) {
    console.error("Error fetching Professors:", error);
  }
};