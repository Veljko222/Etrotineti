package com.etrotinetbackend.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.etrotinetbackend.backend.model.Korisnik;
import java.util.Optional;

public interface KorisnikRep extends JpaRepository<Korisnik, Long> {
    Optional<Korisnik> findByEmail(String email);
    boolean existsByEmail(String email);
}

