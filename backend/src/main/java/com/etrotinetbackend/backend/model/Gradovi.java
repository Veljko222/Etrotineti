package com.etrotinetbackend.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "gradovi")
public class Gradovi {

    @Id
    @Column(name = "Naziv", nullable = false)
    private String naziv;  // JPA voli lowercase field name, ali mapira na kolonu

    public Gradovi() {}

    public Gradovi(String naziv) {
        this.naziv = naziv;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
