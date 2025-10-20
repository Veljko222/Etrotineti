package com.etrotinetbackend.backend.model;

import jakarta.persistence.*;


@Entity
@Table(name = "korisnik")
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idKorisnik") // <-- obavezno navedi ime kolone
    private Long idKorisnik;

    @Column(name = "ime", nullable = false)
    private String ime;

    @Column(name = "prezime", nullable = false)
    private String prezime;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "pass", nullable = false)
    private String pass;

     public Korisnik() {}

    public Korisnik(String ime, String prezime, String email, String pass) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.pass = pass;
    }
   

    // GETTERS & SETTERS
    public Long getIdKorisnik() { return idKorisnik; }
    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }

    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPass() { return pass; }
    public void setPass(String pass) { this.pass = pass; }

   
}
