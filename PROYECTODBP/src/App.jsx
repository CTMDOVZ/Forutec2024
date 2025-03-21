import React from 'react';
import { BrowserRouter as Router, Route, Routes, useNavigate } from 'react-router-dom';
import Login from './components/Login';
import Register from './components/Register';
import { AuthProvider } from './components/AuthContext';
import LogoutButton from './components/LogoutButton';
import NavBar from './components/NavBar';
const Home = () => {
  const navigate = useNavigate();

  return (
    <div><h1>Welcome to the FORUTEC App</h1>
      <form>
      <p>Please use the menu to navigate through the application.</p>
      <button onClick={() => navigate('/login')}>Go to Login</button>
      <button onClick={() => navigate('/register')}>Go to Register</button>
      <LogoutButton />
      </form>
    </div>
    
  );
};

const App = () => {
  return (
    <AuthProvider>
      <Router>
        
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
