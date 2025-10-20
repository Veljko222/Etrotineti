import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Calendar.css';

export default function CalendarSection({ selectedCity, selectedTrotinet, onSelectionChange }) {
  const [selectedDate, setSelectedDate] = useState('');
  const [availableHours, setAvailableHours] = useState([]);
  const [startHour, setStartHour] = useState(null);
  const [endHour, setEndHour] = useState(null);
useEffect(() => {
  //  console.log("Trenutno selektovani trotinet:", selectedTrotinet);
      //  console.log("Provera dostupnosti trotinet-a za grad:", selectedCity, "i rezervaciju:", startHour, endHour, availableHours);

  }, [selectedTrotinet]);
  // Dohvatanje slobodnih sati kada se promeni grad ili datum
 useEffect(() => {
  if (!selectedCity || !selectedDate) return;

  // Ako trotinet nije selektovan, prikazi punu satnicu (8-19)
  if (!selectedTrotinet) {
    const allHours = [];
    for (let h = 8; h <= 19; h++) allHours.push(h);
    setAvailableHours(allHours);
    return;
  }

  // Ako je trotinet selektovan, pozovi backend
  axios.get(`http://localhost:8080/api/iznajmljivanja/slobodni-termini`, {
    params: { 
      grad: selectedCity,
      datum: selectedDate,
      idTrotinet: selectedTrotinet
    },
    withCredentials: true
  })
  .then(res => setAvailableHours(res.data))
  .catch(err => console.error("Greška pri učitavanju slobodnih sati:", err));
}, [selectedCity, selectedDate, selectedTrotinet]);

useEffect(() => {
  if (!selectedTrotinet) return;

  // proveri da li trenutno izabrani satovi još uvek postoje u dostupnim
  const range = [];
  if (startHour !== null && endHour !== null) {
    for (let h = startHour; h <= endHour; h++) range.push(h);
    const allAvailable = range.every(h => availableHours.includes(h));
    if (!allAvailable) {
      setStartHour(null);
      setEndHour(null);
      if (onSelectionChange) onSelectionChange(null); // reset i u parentu
    }
  }
}, [availableHours, selectedTrotinet]);
  // Klik na sat
  const handleHourClick = (hour) => {
  if (!selectedCity) return;

  // Ponovni klik na startHour → deselect
  if (hour === startHour) {
    setStartHour(null);
    setEndHour(null);
    return;
  }

  if (startHour === null) {
    // prvi klik → start
    setStartHour(hour);
    setEndHour(null);
  } else if (hour > startHour) {
    // drugi klik → kraj
    // Provera kontinuiteta
    const range = [];
    for (let h = startHour; h <= hour; h++) range.push(h);
    const allAvailable = range.every(h => availableHours.includes(h));

    if (!allAvailable) {
      alert("Izabrani opseg sadrži nedostupne sate, probajte ponovo.");
      return;
    }

    setEndHour(hour);
    if (onSelectionChange) {
      onSelectionChange({
        date: selectedDate,
        start: startHour,
        end: hour,
        available: availableHours
      });
    }
  } else {
    // klik pre start → reset start
    setStartHour(hour);
    setEndHour(null);
  }
};


  // Da li je sat selektovan (vizuelno)
  const isSelected = (hour) => {
    if (startHour === null) return false;
    if (endHour === null) return hour === startHour;
    return hour >= startHour && hour <= endHour;
  };

  return (
    <section className="calendar-section">
      <div className="calendar-container">
        <h2>Izaberite datum i vremenski opseg:</h2>

        <input
          type="date"
          value={selectedDate}
          onChange={(e) => {
            setSelectedDate(e.target.value);
            setStartHour(null);
            setEndHour(null);
          }}
          className="date-picker"
          disabled={!selectedCity}
        />

        {selectedDate && (
          <div className="times-container">
            {availableHours.length > 0 ? (
              availableHours.map(hour => (
                <button
                  key={hour}
                  className={`time-slot ${isSelected(hour) ? 'selected' : ''}`}
                  onClick={() => handleHourClick(hour)}
                  disabled={!selectedCity}
                >
                  {hour}:00
                </button>
              ))
            ) : (
              <p className="no-times">Nema slobodnih termina za taj dan.</p>
            )}
          </div>
        )}

        {startHour !== null && endHour !== null && (
          <p className="selection-info">
            Izabrano: {selectedDate} od {startHour}:00 do {endHour}:00
          </p>
        )}
      </div>
    </section>
  );
}
