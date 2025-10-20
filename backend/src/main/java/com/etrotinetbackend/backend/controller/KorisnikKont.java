package com.etrotinetbackend.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.etrotinetbackend.backend.dto.LoginRequest;
import com.etrotinetbackend.backend.dto.LoginResponse;
import com.etrotinetbackend.backend.model.Korisnik;
import com.etrotinetbackend.backend.repository.KorisnikRep;
import com.etrotinetbackend.backend.service.KorisnikServis;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")// omogućava frontend da šalje zahteve
public class KorisnikKont {

    @Autowired
    private KorisnikServis userService;
    @Autowired
    private KorisnikRep korisnikRepository;

    @PostMapping("/register")
    public String register(@RequestBody Korisnik korisnik) {
        return userService.registerUser(korisnik);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request, HttpSession session) {
        System.out.println("Login attempt: " + request.getEmail() + " / " + request.getPass());
        return korisnikRepository.findByEmail(request.getEmail())
            .map(korisnik -> {
                if (korisnik.getPass().equals(request.getPass())) {
                    session.setAttribute("user", korisnik);
                     System.out.println("Current user: " + session.getAttribute("user"));
                             System.out.println("Session ID: " + session.getId());

                    return new LoginResponse("Uspesan login");
                } else {
                    return new LoginResponse("Pogresna lozinka");
                    

                }
            })
            .orElseGet(() -> new LoginResponse("Korisnik ne postoji"));
    }
    @GetMapping("/current-user")
    public Korisnik getCurrentUser(HttpSession session) {
        System.out.println("Current user: " + session.getAttribute("user"));
        System.out.println("Session ID: " + session.getId());
        return (Korisnik) session.getAttribute("user");
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Odjavljen";
    }


}



