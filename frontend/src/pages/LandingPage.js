import React, { useState, useEffect } from 'react';
import { useLocation } from "react-router-dom";
import '../css/LandingPage.css';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import heroImage from '../assets/hero-img.jpg';
import trotinet1 from '../assets/trot1.jpg';
import trotinet2 from '../assets/trot2.jpg';
import trotinet3 from '../assets/trot3.jpg';
import axios from 'axios';


import { Link } from 'react-router-dom';

function LandingPage() {
  const [user, setUser] = useState(null);
  const location = useLocation();

const fetchUser = () => {
  axios.get("http://localhost:8080/api/auth/current-user", { withCredentials: true })
    .then(res => {
      console.log("Current user:", res.data);  // ispise usera u konzolu
      setUser(res.data);
      console.log(user);
    })
    .catch(() => {
      console.log("No user logged in");       // ispise ako je null
      setUser(null);
    });
};

  useEffect(() => {
    fetchUser();
  }, [location.pathname]);

  return (
       <div>

           <Navbar showAuthButtons={!user} onLogout={() => {
        axios.post('http://localhost:8080/api/auth/logout', {}, { withCredentials: true })
          .then(() => fetchUser());
      }} />

      {/* Hero Section */}
      <header className="hero" style={{ backgroundImage: `url(${heroImage})` }}>
        <h1>Ride Green = Ride Smart.</h1>
        <Link to="/register"><button className="btn hero-btn">Započni</button></Link>
      </header>

      {/* About Section */}
      <section className="about-section">
  <div className="about-content">
    <h2>O Nama</h2>
    <p>
      E-trotineti je inovativna platforma za električne trotinete. 
      Pružamo brz, ekološki prihvatljiv i zabavan način transporta po gradu.
    </p>
  </div>
</section>



           <section className="trotinet-section">
        <h2>Naši Trotineti</h2>
        <div className="trotinet-cards">
          <div className="trotinet-card">
            <img src={trotinet1} alt="Trotinet 1" />
            <ul>
              <li>Model: TrotinetMini</li>
              <li>Najveća brzina: 25 km/h</li>
              <li>Baterija: 30 km domet</li>
              <li>Težina: 12 kg</li>
              <li>Cena: 800 din/h</li>
            </ul>
          </div>
          <div className="trotinet-card">
            <img src={trotinet2} alt="Trotinet 2" />
            <ul>
              <li>Model: TrotinetX1</li>
              <li>Najveća brzina: 30 km/h</li>
              <li>Baterija: 35 km domet</li>
              <li>Težina: 14 kg</li>
              <li>Cena: 900 din/h</li>
            </ul>
          </div>
          <div className="trotinet-card">
            <img src={trotinet3} alt="Trotinet 3" />
            <ul>
              <li>Model: TrotinetPro</li>
              <li>Najveća brzina: 28 km/h</li>
              <li>Baterija: 32 km domet</li>
              <li>Težina: 13 kg</li>
              <li>Cena: 1200 din/h</li>
            </ul>
          </div>
        </div>
      </section>

      <section className="locations-section">
  <h2>Naše Lokacije</h2>
  <div className="map-container">
    <iframe
      title="lokacije"
      src="https://www.google.com/maps/d/embed?mid=1OXWjneqtpgjNBlSONLBfRjdWpHi4YGI&ehbc=2E312F"
      width="100%"
      height="500"
      style={{ border: 0 }}
      allowFullScreen=""
      loading="lazy"
      referrerPolicy="no-referrer-when-downgrade"
    ></iframe>
  </div>
</section>
      <Footer />
  
    </div>
  );
 
}

export default LandingPage;
