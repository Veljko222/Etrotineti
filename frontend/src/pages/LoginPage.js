import React, { useState } from 'react';
import '../css/LoginPage.css';
import eyeOpen from '../assets/open.jpg';
import eyeClosed from '../assets/closed.jpg';
import axios from 'axios'; 

function Login() {
  const [formData, setFormData] = useState({
    email: '',
    pass: '',
  });

  const [showPassword, setShowPassword] = useState(false);
  const [errors, setErrors] = useState({});

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    let validationErrors = {};

    if (!emailRegex.test(formData.email)) validationErrors.email = 'Nevažeći email';
    if (!formData.password) validationErrors.password = 'Lozinka je obavezna';

    setErrors(validationErrors);

    if (Object.keys(validationErrors).length === 0) {
    try {
      const response = await axios.post('http://localhost:8080/api/auth/login', {
        email: formData.email,
        pass: formData.password, // mora da se poklapa sa DTO
      }, { withCredentials: true });

      alert(response.data.message); // backend vraća LoginResponse
      if (response.data.message === "Uspesan login") {
        // ovde možeš redirectovati korisnika npr. na /dashboard
        window.location.href = "/booking";
      }

    } catch (error) {
      console.error(error);
      alert('Došlo je do greške pri logovanju');
    }
  }
};

  return (
    <div className="login-container">
      <h2>Login</h2>
      <form className="login-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            placeholder="Unesite email"
          />
          {errors.email && <span className="error">{errors.email}</span>}
        </div>

        <div className="form-group password-group">
          <div className="password-wrapper">
            <input
              type={showPassword ? 'text' : 'password'}
              name="password"
              value={formData.password}
              onChange={handleChange}
              placeholder="Unesite lozinku"
            />
            <img
              src={showPassword ? eyeClosed : eyeOpen}
              alt="Toggle password visibility"
              className="eye-icon"
              onClick={() => setShowPassword(!showPassword)}
            />
          </div>
          {errors.password && <span className="error">{errors.password}</span>}
        </div>

        <button type="submit" className="btn">Ulogujte se</button>
        <p className="register-link">
          Nemate nalog? <a href="/register">Registrujte se</a>
        </p>
      </form>
    </div>
  );
}

export default Login;
