import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../components/AuthContext'; 
import LogoutButton from '../components/LogoutButton';  

const NavBar = () => {
  const { token } = useAuth();

  return (
    <nav>
      <ul className="navbar-list">
        <li>
          <Link to="/">Home</Link>
        </li>
        {!token ? (
          <>
            <li>
              <Link to="/login">Login</Link>
            </li>
            <li>
              <Link to="/register">Register</Link>
            </li>
          </>
        ) : (
          <>
            <li>
              <Link to={`/user/${userId}`}>Profile</Link> {/* Agregar enlace al perfil del usuario */}
            </li>
            <li>
              <LogoutButton />
            </li>
          </>
        )}
      </ul>
    </nav>
  );
};

export default NavBar;
