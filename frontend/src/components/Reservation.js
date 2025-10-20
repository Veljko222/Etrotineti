import React, { useState, useEffect } from 'react';
import './Reservation.css';
import axios from 'axios';

export default function ReservationsSection() {
  const [reservations, setReservations] = useState([]);
  const [editReservation, setEditReservation] = useState(null);
  
  const handleSaveEdit = () => {
  if (!editReservation) return;

  // Provera da li su vrednosti validne
  if (!editReservation.date || editReservation.startTime == null || editReservation.endTime == null) {
    alert("Popunite sve podatke!");
    return;
  }

  axios.put("http://localhost:8080/api/iznajmljivanja/izmeni", null, {
  params: {
    idRezervacije: editReservation.id,
  
    
    pocetak: editReservation.startTime, // novi početak
    kraj: editReservation.endTime       // novi kraj
  },
  withCredentials: true
})
  .then(res => {
    console.log(editReservation);
    alert("Rezervacija uspešno izmenjena!");
    setEditReservation(null);
    // Refresh tabele
    window.location.reload();
  })
  .catch(err => {
    console.error("Greška pri izmeni rezervacije:", err);
    alert("Došlo je do greške.");
  });
};

  useEffect(() => {
    axios.get("http://localhost:8080/api/iznajmljivanja/my", { withCredentials: true })
      .then(res => {
        const data = res.data.map(r => ({
          id: r.idIznajmljivanje,
          city: r.grad.naziv,
          scooter: r.trotinet.naziv,
          date: r.datum,
          startTime: r.pocetak.split(':')[0],
          endTime: r.kraj.split(':')[0],   // dodajemo kraj
          duration: Math.ceil((new Date(`1970-01-01T${r.kraj}`) - new Date(`1970-01-01T${r.pocetak}`)) / 3600000),
          price: Math.ceil(((new Date(`1970-01-01T${r.kraj}`) - new Date(`1970-01-01T${r.pocetak}`)) / 3600000) * r.trotinet.cenaPoSatu)
        }));

        // sortiranje po datumu opadajuće
        data.sort((a, b) => new Date(b.date) - new Date(a.date));
        setReservations(data);
      })
      .catch(err => console.error("Greška pri učitavanju rezervacija:", err));
  }, []);

  const handleEdit = (reservation) => {
   setEditReservation(reservation);
  };

  const handleCancel = (id) => {
  if (!window.confirm("Da li ste sigurni da želite da obrišete ovu rezervaciju?")) return;

  axios.delete(`http://localhost:8080/api/iznajmljivanja/${id}`, { withCredentials: true })
    .then(res => {
      console.log(res.data);
      // Reload cele stranice
      window.location.reload();
    })
    .catch(err => console.error("Greška pri brisanju rezervacije:", err));
};



  const isEditable = (date, startTime) => {
    const now = new Date();
    const reservationStart = new Date(`${date}T${startTime}:00`);
    const diffHours = (reservationStart - now) / (1000 * 60 * 60);
    return diffHours >= 24;
  };

  return (
    <section className="reservations-section">
      <h2>Moje rezervacije</h2>

      {reservations.length === 0 ? (
        <p>Trenutno nemate aktivne rezervacije.</p>
      ) : (
        <div className="table-container">
          <table className="reservations-table">
            <thead>
              <tr>
                <th>Grad</th>
                <th>Trotinet</th>
                <th>Datum</th>
                <th>Početak</th>
                <th>Kraj</th> {/* dodato */}
                <th>Trajanje (h)</th>
                <th>Cena (din)</th>
                <th>Akcije</th>
              </tr>
            </thead>
            <tbody>
              {reservations.map((r) => {
                const editable = isEditable(r.date, r.startTime);
                return (
                  <tr key={r.id}>
                    <td>{r.city}</td>
                    <td>{r.scooter}</td>
                    <td>{r.date}</td>
                    <td>{r.startTime}:00</td>
                    <td>{r.endTime}:00</td> {/* prikaz kraja */}
                    <td>{r.duration}</td>
                    <td>{r.price}</td>
                    <td>
                      {editable ? (
                        <>
                          <button className="edit-btn" onClick={() => handleEdit(r)}>Izmeni</button>
                          <button className="cancel-btn" onClick={() => handleCancel(r.id)}>Otkaži</button>
                        </>
                      ) : (
                        <span className="locked-text">Nije moguće izmeniti</span>
                      )}
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
          {editReservation && (
  <div className="edit-modal">
    <h3>Izmena rezervacije</h3>
    <p>Grad: {editReservation.city}</p>
    <p>Trotinet: {editReservation.scooter}</p>

   <p>Datum: {editReservation.date}</p>

    <label>Početak:</label>
    <input 
      type="number" 
      min="8" 
      max="19" 
      value={editReservation.startTime} 
      onChange={(e) => setEditReservation({...editReservation, startTime: parseInt(e.target.value)})}
    />

    <label>Kraj:</label>
    <input 
      type="number" 
      min={editReservation.startTime + 1} 
      max="19" 
      value={editReservation.endTime} 
      onChange={(e) => setEditReservation({...editReservation, endTime: parseInt(e.target.value)})}
    />

    <button className="edit-btn" onClick={handleSaveEdit}>Sačuvaj izmene</button>
    <button className="cancel-btn" onClick={() => setEditReservation(null)}>Otkaži</button>
  </div>
)}
        </div>
      )}
    </section>
  );
}




