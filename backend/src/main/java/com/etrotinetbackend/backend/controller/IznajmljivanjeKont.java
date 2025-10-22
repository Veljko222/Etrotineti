package com.etrotinetbackend.backend.controller;

import com.etrotinetbackend.backend.model.Gradovi;
import com.etrotinetbackend.backend.model.Iznajmljivanje;
import com.etrotinetbackend.backend.model.Korisnik;
import com.etrotinetbackend.backend.model.Stanje;
import com.etrotinetbackend.backend.model.StanjeId;
import com.etrotinetbackend.backend.model.Trotinet;
import com.etrotinetbackend.backend.repository.StanjeRep;
import com.etrotinetbackend.backend.service.IznajmljivanjeServis;
import com.etrotinetbackend.backend.service.TrotinetDostupnostServis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/iznajmljivanja")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class IznajmljivanjeKont {

    @Autowired
    private IznajmljivanjeServis service;
      @Autowired
    private StanjeRep stanjeRep;

    // Dobavljanje svih rezervacija za ulogovanog korisnika
    @GetMapping("/my")
    public List<Iznajmljivanje> getMyReservations(HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("user");
        if (user == null) return List.of();
        return service.findByUserId(user.getIdKorisnik());
    }

    // Pretraga sa opcionalnim parametrima
    @GetMapping("/search")
    public List<Iznajmljivanje> search(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long trotinetId,
            @RequestParam(required = false) String datum,
            @RequestParam(required = false) String gradNaziv
    ) {
        LocalDate localDate = datum != null ? LocalDate.parse(datum) : null;

        if (userId != null && trotinetId != null && localDate != null) {
            return service.findByUserAndTrotinetAndDate(userId, trotinetId, localDate);
        } else if (userId != null && localDate != null) {
            return service.findByUserAndDate(userId, localDate);
        } else if (userId != null && gradNaziv != null) {
            return service.findByUserAndGrad(userId, gradNaziv);
        } else if (userId != null) {
            return service.findByUserId(userId);
        } else if (trotinetId != null && localDate != null) {
            return service.findByTrotinetAndDate(trotinetId, localDate);
        } else if (gradNaziv != null && localDate != null) {
            return service.findByGradAndDate(gradNaziv, localDate);
        } else {
            // ako nema parametara, vrati praznu listu
            return List.of();
        }
    }

    // Brisanje rezervacije po ID-ju
@DeleteMapping("/{id}")
public String deleteReservation(@PathVariable Long id, HttpSession session) {
    Korisnik user = (Korisnik) session.getAttribute("user");
    if (user == null) return "Niste ulogovani";

    boolean deleted = service.deleteReservation(id, user.getIdKorisnik());
    return deleted ? "Uspesno obrisano" : "Nije moguce obrisati rezervaciju";
}

@GetMapping("/slobodni-termini")
public List<Integer> getAvailableHours(
        @RequestParam String grad,
        @RequestParam String datum,
        @RequestParam Long idTrotinet,  
        HttpSession session
) {
    LocalDate localDate = LocalDate.parse(datum);
     
    
    // 1. Dohvati sve rezervacije za taj trotinet tip u datom gradu i datumu
    List<Iznajmljivanje> rezervacije = service.findByTrotinetAndCityAndDate(idTrotinet, grad, localDate);

    // 2. Dohvati stanje (ukupan broj trotineta te vrste u tom gradu)
    Stanje s = stanjeRep.findById(new StanjeId(idTrotinet, grad)).orElse(null);
    int stanje = (s != null) ? s.getStanje() : 0; // ako nema unosa, broj je 0

    // 3. Definiši sve moguće sate (8-19)
    List<Integer> allHours = new ArrayList<>();
    for(int h=8; h<=19; h++) allHours.add(h);

    // 4. Proveri za svaki sat koliko rezervacija već postoji
    List<Integer> dostupniSati = new ArrayList<>();
    for(int h=8; h<=19; h++) {
        int count = 0;
        for(Iznajmljivanje r : rezervacije) {
            int start = r.getPocetak().getHour();
            int end = r.getKraj().getHour();
            if(h >= start && h < end) {
                count++;
            }
        }
        // Ako je broj rezervacija manji od stanja, sat je dostupan
        if(count < stanje) {
            dostupniSati.add(h);
        }
    }

    return dostupniSati;
}

//Dostupni trotineti za termin
@RestController
@RequestMapping("/api/trotineti")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class TrotinetDostupnostKontroler {

    @Autowired
    private TrotinetDostupnostServis servis;

    @GetMapping("/dostupni")
    public Map<Long, Boolean> getDostupni(
            @RequestParam String grad,
            @RequestParam String datum,
            @RequestParam int pocetak,
            @RequestParam int kraj,
            HttpSession session
    ) {
        Long userId = ((Korisnik) session.getAttribute("user")).getIdKorisnik();
        LocalDate localDate = LocalDate.parse(datum);
        LocalTime startTime = LocalTime.of(pocetak, 0);
        LocalTime endTime = LocalTime.of(kraj, 0);

        return servis.dostupnostPoTrotinetu(grad, localDate, startTime, endTime, userId);
    }
}
// Kreiranje nove rezervacije
@PostMapping("/kreiraj")
public String kreirajRezervaciju(
        @RequestParam String gradNaziv,
        @RequestParam Long idTrotinet,
        @RequestParam String datum,
        @RequestParam int pocetak,
        @RequestParam int kraj,
        HttpSession session
) {
    Korisnik user = (Korisnik) session.getAttribute("user");
    if (user == null) return "Niste ulogovani";

    
   

    

     Iznajmljivanje nova = new Iznajmljivanje();
    nova.setKorisnik(user);

    // Kreiramo "proxy" objekat sa samo ID-em
    Gradovi grad = new Gradovi();
    grad.setNaziv(gradNaziv);
    nova.setGrad(grad);

    Trotinet trotinet = new Trotinet();
    trotinet.setIdTrotinet(idTrotinet);
    nova.setTrotinet(trotinet);

    nova.setDatum(LocalDate.parse(datum));
    nova.setPocetak(LocalTime.of(pocetak, 0));
    nova.setKraj(LocalTime.of(kraj, 0));

    service.save(nova);
    return "Rezervacija uspešna!";
}
// Izmena postojeće rezervacije
@PutMapping("/izmeni")
public String izmeniRezervaciju(
        @RequestParam Long idRezervacije,

     
        @RequestParam int pocetak,
        @RequestParam int kraj,
        HttpSession session
) {
    Korisnik user = (Korisnik) session.getAttribute("user");
    if (user == null) return "Niste ulogovani";

       Iznajmljivanje rezervacija = service.findById(idRezervacije);

    // Proveri validnost sati
    if (pocetak < 8 || pocetak >= kraj || kraj > 19) {
        return "Nevalidan vremenski interval";
    }
    
    rezervacija.setPocetak(LocalTime.of(pocetak, 0));
    rezervacija.setKraj(LocalTime.of(kraj, 0));

    service.save(rezervacija);

    return "Rezervacija uspešno izmenjena!";
}

}