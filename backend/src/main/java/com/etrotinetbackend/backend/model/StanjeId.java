package com.etrotinetbackend.backend.model;

import java.io.Serializable;
import java.util.Objects;

public class StanjeId implements Serializable {
    private Long idTrotinet;
    private String naziv;

    public StanjeId() {}

    public StanjeId(Long idTrotinet, String naziv) {
        this.idTrotinet = idTrotinet;
        this.naziv = naziv;
    }

    public Long getIdTrotinet() { return idTrotinet; }
    public void setIdTrotinet(Long idTrotinet) { this.idTrotinet = idTrotinet; }

    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StanjeId)) return false;
        StanjeId that = (StanjeId) o;
        return Objects.equals(idTrotinet, that.idTrotinet) &&
               Objects.equals(naziv, that.naziv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTrotinet, naziv);
    }
}
