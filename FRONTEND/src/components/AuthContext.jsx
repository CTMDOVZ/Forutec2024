import React, { createContext, useState, useContext } from 'react';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(localStorage.getItem('token'));
  const [userId, setUserId] = useState(localStorage.getItem('userId'));  

  const login = (newToken, userId) => {
    setToken(newToken);
    setUserId(userId);
    localStorage.setItem('token', newToken);
    localStorage.setItem('userId', userId);  
  };

  const logout = () => {
    setToken(null);
    setUserId(null);
    localStorage.removeItem('token');
    localStorage.removeItem('userId');  
  };

  return (
    <AuthContext.Provider value={{ token, userId, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  return useContext(AuthContext);
};
