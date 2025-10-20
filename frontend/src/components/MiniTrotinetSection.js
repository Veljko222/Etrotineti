import React, { useState, useEffect  } from 'react';
import axios from 'axios';
import './MiniTrotinetSection.css';
import trotinet1 from '../assets/trot1.jpg';
import trotinet2 from '../assets/trot2.jpg';
import trotinet3 from '../assets/trot3.jpg';

export default function MiniTrotinetCards({ selectedCity, selectedReservation, onSelect }) {
  const trotineti = [
    { id: 1, img: trotinet1, naziv: 'TrotinetMini', brzina: '25 km/h', baterija: '30 km', cena: '800 din/h' },
    { id: 2, img: trotinet2, naziv: 'TrotinetX1', brzina: '30 km/h', baterija: '35 km', cena: '900 din/h' },
    { id: 3, img: trotinet3, naziv: 'TrotinetPro', brzina: '28 km/h', baterija: '32 km', cena: '1200 din/h' },
  ];

  const [selectedId, setSelectedId] = useState(null);
  const [availableMap, setAvailableMap] = useState({}); // {1: true, 2: false, 3: true}

  // Kada se promeni grad ili rezervacija, pozovi backend
  useEffect(() => {
   // console.log("Provera dostupnosti trotinet-a za grad:", selectedCity, "i rezervaciju:", selectedReservation);
    if (!selectedCity || !selectedReservation) {
      setAvailableMap({});
      return;
    }

    const { date, start, end } = selectedReservation;

    axios.get('http://localhost:8080/api/trotineti/dostupni', {
      params: {
        grad: selectedCity,
        datum: date,
        pocetak: start,
        kraj: end
      },
      withCredentials: true
    })
    .then(res => setAvailableMap(res.data))
    .catch(err => {
      console.error('Greška pri dohvatanju dostupnosti trotinet-a:', err);
      setAvailableMap({});
    });
  }, [selectedCity, selectedReservation]);

  const handleSelect = (id) => {
    if (!selectedCity) return;
    if (availableMap[id] === false) return; // onemogući selekciju ako nije dostupan

    if (selectedId === id) {
      setSelectedId(null);
      onSelect && onSelect(null);
    } else {
      setSelectedId(id);
      onSelect && onSelect(id);
    }
  };

  return (
    <div className="mini-trotinet-container">
      <h2 className="mini-trotinet-title">Izaberite trotinet:</h2>
      <div className="mini-trotinet-cards">
        {trotineti.map(t => {
          const isDisabled = !selectedCity || (selectedReservation && availableMap[t.id] === false);
          return (
            <div
              key={t.id}
              className={`mini-trotinet-card ${selectedId === t.id ? 'selected' : ''} ${isDisabled ? 'disabled' : ''}`}
              onClick={() => handleSelect(t.id)}
            >
              <img src={t.img} alt={t.naziv} />
              <h4>{t.naziv}</h4>
              <ul>
                <li>{t.brzina}</li>
                <li>{t.baterija}</li>
                <li>{t.cena}</li>
              </ul>
            </div>
          );
        })}
      </div>
    </div>
  );
}