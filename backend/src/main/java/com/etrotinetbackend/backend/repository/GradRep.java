package com.etrotinetbackend.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.etrotinetbackend.backend.model.Gradovi;

@Repository
public interface GradRep extends JpaRepository<Gradovi, String> {
    // ovde možeš dodati custom metode ako zatreba
}

