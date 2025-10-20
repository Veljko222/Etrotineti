package com.etrotinetbackend.backend.repository;

import com.etrotinetbackend.backend.model.Stanje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StanjeRep extends JpaRepository<Stanje, Object> {
    // možeš dodati custom query kasnije ako treba
}
