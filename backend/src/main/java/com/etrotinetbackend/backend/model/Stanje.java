package com.etrotinetbackend.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "stanje")
@IdClass(StanjeId.class)
public class Stanje {

    @Id
    @Column(name = "idTrotinet")
    private Long idTrotinet;

    @Id
    @Column(name = "Naziv")
    private String naziv;

    @Column(name = "stanje", nullable = false)
    private int stanje;

    public Stanje() {}

    public Stanje(Long idTrotinet, String naziv, int stanje) {
        this.idTrotinet = idTrotinet;
        this.naziv = naziv;
        this.stanje = stanje;
    }

    // Getteri i setteri
    public Long getIdTrotinet() { return idTrotinet; }
    public void setIdTrotinet(Long idTrotinet) { this.idTrotinet = idTrotinet; }

    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }

    public int getStanje() { return stanje; }
    public void setStanje(int stanje) { this.stanje = stanje; }
}



