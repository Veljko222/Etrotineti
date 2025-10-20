package com.etrotinetbackend.backend.service;

import com.etrotinetbackend.backend.model.Iznajmljivanje;
import com.etrotinetbackend.backend.repository.IznajmljivanjeRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class IznajmljivanjeServis {

    @Autowired
    private IznajmljivanjeRep repository;

    public Iznajmljivanje findById(Long id) {
        return repository.findByIdIznajmljivanje(id);
    }
    // --- Pretrage po korisniku ---

    public List<Iznajmljivanje> findByUserId(Long userId) {
        return repository.findByKorisnikIdKorisnik(userId);
    }

    public List<Iznajmljivanje> findByUserAndDate(Long userId, LocalDate datum) {
        return repository.findByKorisnikIdKorisnikAndDatum(userId, datum);
    }

    public List<Iznajmljivanje> findByUserAndTrotinet(Long userId, Long trotinetId) {
        return repository.findByKorisnikIdKorisnikAndTrotinetIdTrotinet(userId, trotinetId);
    }

    public List<Iznajmljivanje> findByUserAndGrad(Long userId, String nazivGrada) {
        return repository.findByKorisnikIdKorisnikAndGradNaziv(userId, nazivGrada);
    }

    public List<Iznajmljivanje> findByUserAndTrotinetAndDate(Long userId, Long trotinetId, LocalDate datum) {
        return repository.findByKorisnikIdKorisnikAndTrotinetIdTrotinetAndDatum(userId, trotinetId, datum);
    }

    // --- Pretrage po trotinetu ---
    public List<Iznajmljivanje> findByTrotinet(Long trotinetId) {
        return repository.findByTrotinetIdTrotinet(trotinetId);
    }

    public List<Iznajmljivanje> findByTrotinetAndDate(Long trotinetId, LocalDate datum) {
        return repository.findByTrotinetIdTrotinetAndDatum(trotinetId, datum);
    }

    // --- Pretrage po gradu ---
    public List<Iznajmljivanje> findByGrad(String nazivGrada) {
        return repository.findByGradNaziv(nazivGrada);
    }

    public List<Iznajmljivanje> findByGradAndDate(String nazivGrada, LocalDate datum) {
        return repository.findByGradNazivAndDatum(nazivGrada, datum);
    }

    public List<Iznajmljivanje> findByUserIdAndCityAndDate(Long userId, String grad, LocalDate datum) {
    return repository.findByKorisnikIdKorisnikAndGradNazivAndDatum(userId, grad, datum);
}


    // --- Pretrage po datumu ---
    public List<Iznajmljivanje> findByDate(LocalDate datum) {
        return repository.findByDatum(datum);
    }

    public boolean deleteReservation(Long reservationId, Long userId) {
    // Proveri da li rezervacija pripada korisniku
    return repository.findById(reservationId)
            .filter(r -> r.getKorisnik().getIdKorisnik().equals(userId))
            .map(r -> {
                repository.delete(r);
                return true;
            })
            .orElse(false);
}

        public List<Integer> getAvailableHours(Long userId, String grad, LocalDate datum) {
            List<Iznajmljivanje> rezervacije = repository.findByKorisnikIdKorisnikAndGradNazivAndDatum(userId, grad, datum);

            List<Integer> allHours = new ArrayList<>();
            for(int h=8; h<=19; h++) allHours.add(h);

            for(Iznajmljivanje r : rezervacije){
                int start = r.getPocetak().getHour();
                int end = r.getKraj().getHour();
                for(int h=start; h<end; h++){
                    allHours.remove((Integer) h);
                }
            }
            return allHours;
        }
       public List<Iznajmljivanje> findByUserAndCityAndDateAndTrotinet(Long userId, String grad, Long idTrotinet, LocalDate datum) {
    return repository.findByKorisnikIdKorisnikAndGradNazivAndTrotinetIdTrotinetAndDatum(userId, grad, idTrotinet, datum);
}
public List<Iznajmljivanje> findByTrotinetAndCityAndDate(Long idTrotinet, String grad, LocalDate datum) {
    return repository.findByTrotinetIdTrotinetAndGradNazivAndDatum(idTrotinet, grad, datum);
}
  public Iznajmljivanje save(Iznajmljivanje iznajmljivanje) {
        return repository.save(iznajmljivanje);
    }

}

