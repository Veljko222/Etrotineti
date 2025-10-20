package com.etrotinetbackend.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "trotinet")
public class Trotinet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTrotinet")
    private Long idTrotinet;

    @Column(name = "naziv", nullable = false)
    private String naziv;

    @Column(name = "cenaPoSatu", nullable = false)
    private int cenaPoSatu;

    
// Getteri i setteri


    
    public Long getIdTrotinet() {
        return idTrotinet;
    }

    public void setIdTrotinet(Long idTrotinet) {
        this.idTrotinet = idTrotinet;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getCenaPoSatu() {
        return cenaPoSatu;
    }

    public void setCenaPoSatu(int cenaPoSatu) {
        this.cenaPoSatu = cenaPoSatu;
    }
}
