import axios from 'axios';

const API_URL = 'http://3.235.222.9/api';

export const fetchLogin = async (email, password) => {
  const response = await axios.post(`${API_URL}/auth/login`, { email, password });
  return response.data;
};

export const fetchRegister = async (firstName, lastName, email, password, isAdmin) => {
  const response = await axios.post(`${API_URL}/auth/register`, { firstName, lastName, email, password, isAdmin });
  return response.data;
};
