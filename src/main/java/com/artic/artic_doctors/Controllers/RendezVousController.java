package com.artic.artic_doctors.Controllers;

import com.artic.artic_doctors.Entities.Rendez_Vous;
import com.artic.artic_doctors.Exception.DoctorNotAvailableException;
import com.artic.artic_doctors.Exception.RessourceNotFound;
import com.artic.artic_doctors.Services.IRendezVousServicesImpl;
import jakarta.websocket.server.PathParam;
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

    @PostMapping(path = "/add")
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

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> SupprimerRendeVous(@PathVariable("id") long idRdv) {
        try {
          rendezVousServices.deleteRendezVous(idRdv);
            return ResponseEntity.ok("rendez vous deleted avec succé");
        } catch (RessourceNotFound exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("rendezvous n'existe pas  avec id  " + idRdv);
        }

    }
}
