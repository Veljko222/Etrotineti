import React, { useState } from 'react';
import '../css/RegisterPage.css';
import eyeOpen from '../assets/open.jpg';
import eyeClosed from '../assets/closed.jpg';
import axios from "axios";


function Register() {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
  });

  const [showPassword, setShowPassword] = useState(false);
  const [errors, setErrors] = useState({});

  // Regex validacije
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  const passwordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    let validationErrors = {};

    if (!formData.firstName) validationErrors.firstName = 'Ime je obavezno';
    if (!formData.lastName) validationErrors.lastName = 'Prezime je obavezno';
    if (!emailRegex.test(formData.email)) validationErrors.email = 'Nevažeći email';
    if (!passwordRegex.test(formData.password)) validationErrors.password =
      'Lozinka mora imati veliko slovo, broj i specijalni karakter, min 8 karaktera';

    setErrors(validationErrors);

    if (Object.keys(validationErrors).length === 0) {
  axios.post("http://localhost:8080/api/auth/register", {
    ime: formData.firstName,
    prezime: formData.lastName,
    email: formData.email,
    pass: formData.password
  })
  .then(res => {
    alert(res.data); // poruka sa backend-a
    // opciono: redirect na login stranu
    window.location.href = "/login";
  })
.catch(error => {
  if (error.response) {
    console.error(error.response.data); // vidi šta tačno vraća server
    alert("Greška prilikom registracije: " + error.response.data.message);
  } else {
    alert("Greška prilikom registracije: " + error.message);
  }
});

}

  };

  return (
    <div className="register-container">
      <h2>Registracija</h2>
      <form className="register-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Ime</label>
          <input
            type="text"
            name="firstName"
            value={formData.firstName}
            onChange={handleChange}
            placeholder="Unesite ime"
          />
          {errors.firstName && <span className="error">{errors.firstName}</span>}
        </div>

        <div className="form-group">
          <label>Prezime</label>
          <input
            type="text"
            name="lastName"
            value={formData.lastName}
            onChange={handleChange}
            placeholder="Unesite prezime"
          />
          {errors.lastName && <span className="error">{errors.lastName}</span>}
        </div>

        <div className="form-group">
          <label>Email</label>
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
          <label>Lozinka</label>
          <div className="password-wrapper">
            <input
              type={showPassword ? 'text' : 'password'}
              name="password"
              value={formData.password}
              onChange={handleChange}
              placeholder="Unesite lozinku"
            />
            <span
              className="toggle-password"
              onClick={() => setShowPassword(!showPassword)}
            >
             <img
                src={showPassword ? eyeClosed : eyeOpen}
                alt="Toggle password visibility"
                className="eye-icon"
                onClick={() => setShowPassword(!showPassword)}
                /> 
            </span>
          </div>
          {errors.password && <span className="error">{errors.password}</span>}
        </div>

        <button type="submit" className="btn">Registruj se</button>
        <p className="login-link">
            Već imate nalog? <a href="/login">Ulogujte se</a>
            </p>
      </form>
    </div>
  );
}

export default Register;
