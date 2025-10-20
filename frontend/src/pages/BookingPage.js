import React from 'react';
import { useEffect } from 'react';
import axios from 'axios';

import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import CalendarSection from '../components/Calendar';
import MiniTrotinetCards from '../components/MiniTrotinetSection';
import Trotineti from '../components/TrotinetSection';
import ReservationsSection from '../components/Reservation';
import '../css/BookingPage.css';
import { useState } from 'react';

export default function BookingPage() {
   const [selectedCity, setSelectedCity] = useState('');
   const [cities, setCities] = useState([]);
   const [selectedTrotinet, setSelectedTrotinet] = useState(null);
    const [selectedReservation, setSelectedReservation] = useState(null); 
    const handleCityChange = (e) => {
    setSelectedCity(e.target.value);
  };
const handleRezervisi = () => {
  if (!selectedCity || !selectedTrotinet || !selectedReservation) {
    alert("Molimo vas, izaberite grad, trotinet i vremenski period.");
    return;
  }

  // Provera kontinuiteta sati (start < end)
  const { start, end } = selectedReservation;
  if (start >= end) {
    alert("Izabrani opseg sati nije validan.");
    return;
  }

  // Slanje rezervacije na backend
  axios.post("http://localhost:8080/api/iznajmljivanja/kreiraj", null, {
    params: {
      gradNaziv: selectedCity,               // ID grada (ako je string, prilagodi)
      idTrotinet: selectedTrotinet,
      datum: selectedReservation.date,
      pocetak: start,
      kraj: end
    },
    withCredentials: true
  })
  .then(res => {
    alert(res.data);
      window.location.reload();
    // Reset selekcija nakon uspešne rezervacije
    setSelectedReservation(null);
    setSelectedTrotinet(null);
  })
  .catch(err => {
    console.error(err);
    alert("Došlo je do greške prilikom rezervacije.");
  });
};

  useEffect(() => {
  axios.get("http://localhost:8080/api/gradovi", { withCredentials: true })
    .then(res => setCities(res.data))
    .catch(err => console.error("Greška pri povlačenju gradova:", err));
}, []);

/*useEffect(() => {
    setSelectedReservation(null);
  }, [selectedCity, selectedTrotinet]);

  useEffect(() => {
    setSelectedTrotinet(null);
  }, [selectedReservation]);*/


  return (
    <div>
      <Navbar showAuthButtons={false} />
           <section className="booking-section">
      <h1>Dobrodošli na zakazivanje termina</h1>
      <p className="subtitle">Selektujte grad:</p>

      <select
        value={selectedCity}
        onChange={(e) => setSelectedCity(e.target.value)}
        className="city-select"
      >
        <option value="" disabled hidden>Izaberite grad</option>
        {cities.map((city, idx) => (
    <option key={idx} value={city}>{city}</option>
  ))}
      </select>
    </section>
      

      <div className="calendar-section">
  <div className="calendar-container">
  <CalendarSection 
  selectedCity={selectedCity} 
  selectedTrotinet={selectedTrotinet} 
  onSelectionChange={setSelectedReservation}
/>
  </div>

  <div className="trotinet-container">
    <MiniTrotinetCards 
  selectedCity={selectedCity} 
  selectedReservation={selectedReservation} 
  onSelect={setSelectedTrotinet} 
/>
  </div>
</div>

<button className="reserve-btn" onClick={handleRezervisi}>Rezerviši</button>

    <ReservationsSection/>
   

      <Footer />
    </div>
  );
}