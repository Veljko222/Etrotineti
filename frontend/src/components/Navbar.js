import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css'; 
import axios from 'axios';

function Navbar({ showAuthButtons = true, onLogout }) {

  return (
    <nav className="navbar">
      <Link to="/" className="logo">
          E-trotineti
        </Link>
      <div className="nav-buttons">
       {showAuthButtons ? (
          <>
            <Link to="/login"><button className="btn">Uloguj se</button></Link>
            <Link to="/register"><button className="btn">Registruj se</button></Link>
          </>
        ) : (
          <>
            <Link to="/booking"><button className="btn">Rezervacije</button></Link>
            <Link to="/"><button className="btn" onClick={onLogout}>Odjavi se</button></Link>
          </>
        )}
      </div>
    </nav>
  );
}

export default Navbar;

