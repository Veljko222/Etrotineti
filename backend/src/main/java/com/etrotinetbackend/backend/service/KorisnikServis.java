package com.etrotinetbackend.backend.service;

import  com.etrotinetbackend.backend.model.Korisnik;
import  com.etrotinetbackend.backend.repository.KorisnikRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KorisnikServis {

    @Autowired
    private KorisnikRep korisnikRepository;

    public String registerUser(Korisnik korisnik) {
        if (korisnikRepository.existsByEmail(korisnik.getEmail())) {
            return "Email već postoji";
        }

        // bez hashovanja lozinke
        korisnikRepository.save(korisnik);
        return "Uspešno registrovan korisnik";
    }
}
