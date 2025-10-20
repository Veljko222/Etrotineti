package com.etrotinetbackend.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "iznajmljivanje")
public class Iznajmljivanje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idIznajmljivanje")
    private Long idIznajmljivanje;

    @ManyToOne
    @JoinColumn(name = "idKorisnik", nullable = false)
    private Korisnik korisnik;

    @ManyToOne
    @JoinColumn(name = "Naziv", nullable = false)
    private Gradovi grad;

    @ManyToOne
    @JoinColumn(name = "idTrotinet", nullable = false)
    private Trotinet trotinet;

    @Column(name = "datum", nullable = false)
    private LocalDate datum;

    @Column(name = "pocetak", nullable = false)
    private LocalTime pocetak;

    @Column(name = "kraj", nullable = false)
    private LocalTime kraj;

    public Iznajmljivanje() {}

    // Getteri i setteri
    public Long getIdIznajmljivanje() { return idIznajmljivanje; }
    public void setIdIznajmljivanje(Long idIznajmljivanje) { this.idIznajmljivanje = idIznajmljivanje; }

    public Korisnik getKorisnik() { return korisnik; }
    public void setKorisnik(Korisnik korisnik) { this.korisnik = korisnik; }

    public Gradovi getGrad() { return grad; }
    public void setGrad(Gradovi grad) { this.grad = grad; }

    public Trotinet getTrotinet() { return trotinet; }
    public void setTrotinet(Trotinet trotinet) { this.trotinet = trotinet; }

    public LocalDate getDatum() { return datum; }
    public void setDatum(LocalDate datum) { this.datum = datum; }

    public LocalTime getPocetak() { return pocetak; }
    public void setPocetak(LocalTime pocetak) { this.pocetak = pocetak; }

    public LocalTime getKraj() { return kraj; }
    public void setKraj(LocalTime kraj) { this.kraj = kraj; }
}
