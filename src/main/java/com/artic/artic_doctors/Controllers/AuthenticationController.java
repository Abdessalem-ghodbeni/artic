package com.artic.artic_doctors.Controllers;

import com.artic.artic_doctors.Entities.*;
import com.artic.artic_doctors.Exception.RessourceNotFound;
import com.artic.artic_doctors.Services.IAuthenticationServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final IAuthenticationServices authenticationServices;
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody User user) {
        return authenticationServices.login(user.getEmail(), user.getPassword());
    }

    @PostMapping("/refreshToken")
    public AuthenticationResponse refreshToken(@RequestBody RefreshTokenRequest refreshToken) {
        return authenticationServices.refreshToken(refreshToken);
    }

    @PostMapping("/registerPatient")
    public ResponseEntity<?> registerEtudiant(@RequestBody Patient patient) {
        try {

            return new ResponseEntity<>(authenticationServices.registerPatient(patient), HttpStatus.CREATED);
        } catch (RessourceNotFound ressourceNotFound) {
            return new ResponseEntity<>(ressourceNotFound.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register/docteur")
    public ResponseEntity<?> registerDoctor(@RequestBody Doctor doctor) {
        try {

            return new ResponseEntity<>(authenticationServices.registerDoctor(doctor), HttpStatus.CREATED);
        } catch (RessourceNotFound ressourceNotFound) {
            return new ResponseEntity<>(ressourceNotFound.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
