package com.etrotinetbackend.backend.repository;

import com.etrotinetbackend.backend.model.Iznajmljivanje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface IznajmljivanjeRep extends JpaRepository<Iznajmljivanje, Long> {

    // Pretraga po korisniku
    List<Iznajmljivanje> findByKorisnikIdKorisnik(Long idKorisnik);

    // Pretraga po trotinetu
    List<Iznajmljivanje> findByTrotinetIdTrotinet(Long idTrotinet);

    // Pretraga po gradu
    List<Iznajmljivanje> findByGradNaziv(String nazivGrada);

    // Pretraga po datumu
    List<Iznajmljivanje> findByDatum(LocalDate datum);

    // Kombinacije
    List<Iznajmljivanje> findByKorisnikIdKorisnikAndDatum(Long idKorisnik, LocalDate datum);

    List<Iznajmljivanje> findByKorisnikIdKorisnikAndTrotinetIdTrotinet(Long idKorisnik, Long idTrotinet);

    List<Iznajmljivanje> findByKorisnikIdKorisnikAndGradNaziv(Long idKorisnik, String nazivGrada);

    List<Iznajmljivanje> findByTrotinetIdTrotinetAndDatum(Long idTrotinet, LocalDate datum);

    List<Iznajmljivanje> findByGradNazivAndDatum(String nazivGrada, LocalDate datum);

    List<Iznajmljivanje> findByKorisnikIdKorisnikAndTrotinetIdTrotinetAndDatum(Long idKorisnik, Long idTrotinet, LocalDate datum);
    // Pretraga po korisniku, gradu i datumu
List<Iznajmljivanje> findByKorisnikIdKorisnikAndGradNazivAndDatum(Long idKorisnik, String nazivGrada, LocalDate datum);

    // Primer metode za proveru poklapanja vremena
    List<Iznajmljivanje> findByGradNazivAndTrotinetIdTrotinetAndDatumAndPocetakLessThanAndKrajGreaterThan(
        String gradNaziv, 
        Long idTrotinet, 
        LocalDate datum, 
        LocalTime kraj,      // proveravamo da li rezervacija počinje pre kraja selektovanog termina
        LocalTime pocetak    // i da li rezervacija završava posle početka selektovanog termina
    );
 List<Iznajmljivanje> findByKorisnikIdKorisnikAndGradNazivAndTrotinetIdTrotinet(
            Long idKorisnik, String gradNaziv, Long idTrotinet);

            List<Iznajmljivanje> findByKorisnikIdKorisnikAndGradNazivAndTrotinetIdTrotinetAndDatum(
    Long idKorisnik, String gradNaziv, Long idTrotinet, LocalDate datum);
List<Iznajmljivanje> findByTrotinetIdTrotinetAndGradNazivAndDatum(Long idTrotinet, String nazivGrada, LocalDate datum);

  Iznajmljivanje findByIdIznajmljivanje(Long id);
}

