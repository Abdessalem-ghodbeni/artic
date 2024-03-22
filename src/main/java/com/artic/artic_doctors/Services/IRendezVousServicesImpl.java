package com.artic.artic_doctors.Services;

import com.artic.artic_doctors.Entities.Disponibilite;
import com.artic.artic_doctors.Entities.Doctor;
import com.artic.artic_doctors.Entities.Patient;
import com.artic.artic_doctors.Entities.Rendez_Vous;
import com.artic.artic_doctors.Exception.DoctorNotAvailableException;
import com.artic.artic_doctors.Exception.RessourceNotFound;
import com.artic.artic_doctors.Repository.IDoctorRepository;
import com.artic.artic_doctors.Repository.IPatientRepository;
import com.artic.artic_doctors.Repository.IRendezVousRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class IRendezVousServicesImpl implements IRendezVousServices{
    private final IRendezVousRepository rendezVousRepository;
    private final IPatientRepository patientRepository;
    private final IDoctorRepository doctorRepository;
   @Transactional
    @Override
    public String addRendezVous(Rendez_Vous RendezVous) {
       Patient patient = patientRepository.findById(RendezVous.getPatient().getId()).orElseThrow(() -> new RessourceNotFound("accun patient avec l'id :" + RendezVous.getPatient().getId()));
       Doctor doctor = doctorRepository.findById(RendezVous.getDoctor().getId()).orElseThrow(() -> new RessourceNotFound("accun doctor avec l'id :" + RendezVous.getDoctor().getId()));

//       boolean isDisponible = false;
//       for (Disponibilite disponibilite : doctor.getDisponibilites()) {
//           if (disponibilite.getDateDisponibilite().equals(dateRendezVous)) {
//               isDisponible = true;
//               break;
//           }
//       }
       boolean isDisponible = doctor.getDisponibilites().isEmpty() ||
               !doctor.getDisponibilites().stream()
                       .map(Disponibilite::getDateDisponibilite)
                       .collect(Collectors.toList())
                       .contains(RendezVous.getDateRerndezVous());
       Rendez_Vous rendezvous = null;
       if (isDisponible) {

//           Rendez_Vous rendezVous = new Rendez_Vous();
           RendezVous.setPatient(patient);
           RendezVous.setDoctor(doctor);
//           rendezVous.setDateRerndezVous(RendezVous.get);
           patient.getRendezVous().add(RendezVous);
           doctor.getRendezVous().add(RendezVous);
           Disponibilite disponibilite = new Disponibilite();
           disponibilite.setDateDisponibilite(RendezVous.getDateRerndezVous());
           disponibilite.setDoctor(doctor);
           doctor.getDisponibilites().add(disponibilite);
           patientRepository.save(patient);
           doctorRepository.save(doctor);
           rendezvous = rendezVousRepository.save(RendezVous);
           return rendezvous.toString();
//       }
       } else {
           throw new DoctorNotAvailableException("Le médecin n'est pas disponible à la date spécifiée. Veuillez choisir une autre date.");
       }

   }




}
