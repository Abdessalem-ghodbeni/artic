package com.artic.artic_doctors.Controllers;

import com.artic.artic_doctors.Entities.Rendez_Vous;
import com.artic.artic_doctors.Exception.DoctorNotAvailableException;
import com.artic.artic_doctors.Exception.RessourceNotFound;
import com.artic.artic_doctors.Services.IRendezVousServicesImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin("*")
@RequestMapping("/rendezVous")
@RequiredArgsConstructor
public class RendezVousController {

    private final IRendezVousServicesImpl rendezVousServices;

//    @PostMapping(path = "/add/{idPatient}/{idDoctor}")
@PostMapping(path = "/add")
//    public ResponseEntity<?> ajouterUnRendezVous(@PathVariable("idPatient") Long idPatient, @PathVariable("idDoctor") Long  idDoctor, @RequestBody Date dateRendezVous){
    public ResponseEntity<?> ajouterUnRendezVous(@RequestBody Rendez_Vous rendezVous){
        try{
            String rendeVous=rendezVousServices.addRendezVous(rendezVous);
            return new ResponseEntity<>(rendeVous,HttpStatus.CREATED);
        }catch (DoctorNotAvailableException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RessourceNotFound exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }

    }



}
