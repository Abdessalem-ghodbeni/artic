package com.artic.artic_doctors.Controllers;

import com.artic.artic_doctors.Entities.Doctor;
import com.artic.artic_doctors.Exception.RessourceNotFound;
import com.artic.artic_doctors.Services.DoctorsServicesImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/docteur")
@RequiredArgsConstructor
public class DoctorsController {
    private  final DoctorsServicesImpl doctorsServices;

    @GetMapping(path = "all_doctors")
    public ResponseEntity<?> GetALLUniversity() {
        try {
            List<Doctor> docteurs =doctorsServices.recupreAllDoctors();

            return ResponseEntity.ok(docteurs);
        } catch (RessourceNotFound exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("une chose mal passé");
        }
    }
}
