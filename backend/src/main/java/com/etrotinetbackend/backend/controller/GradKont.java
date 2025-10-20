package com.etrotinetbackend.backend.controller;


import com.etrotinetbackend.backend.model.Gradovi;
import com.etrotinetbackend.backend.repository.GradRep;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class GradKont {

    private final GradRep gradRepository;

    // Constructor injection
    public GradKont(GradRep gradRepository) {
        this.gradRepository = gradRepository;
    }

    @GetMapping("/gradovi")
    public List<String> getGradovi() {
        // Poziva se na instancu gradRepository, ne statički
        return gradRepository.findAll()
                .stream()
                .map(Gradovi::getNaziv)
                .collect(Collectors.toList());
    }
}
