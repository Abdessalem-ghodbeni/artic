package com.artic.artic_doctors.Services;

import com.artic.artic_doctors.Entities.*;
import com.artic.artic_doctors.Exception.DoctorNotAvailableException;
import com.artic.artic_doctors.Exception.RessourceNotFound;
import com.artic.artic_doctors.Repository.DisponibilityRepository;
import com.artic.artic_doctors.Repository.IDoctorRepository;
import com.artic.artic_doctors.Repository.IPatientRepository;
import com.artic.artic_doctors.Repository.IRendezVousRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class IRendezVousServicesImpl implements IRendezVousServices{
    private final IRendezVousRepository rendezVousRepository;
    private final IPatientRepository patientRepository;
    private final IDoctorRepository doctorRepository;
private  final DisponibilityRepository disponibilityRepository;

    @Transactional
    @Override
    public String addRendezVous(Rendez_Vous rendezVous) {
        Patient patient = patientRepository.findById(rendezVous.getPatient().getId())
                .orElseThrow(() -> new RessourceNotFound("Aucun patient avec l'id :" + rendezVous.getPatient().getId()));

        Doctor doctor = doctorRepository.findById(rendezVous.getDoctor().getId())
                .orElseThrow(() -> new RessourceNotFound("Aucun docteur avec l'id :" + rendezVous.getDoctor().getId()));

        // Vérifier si le médecin est disponible
        boolean isDisponible = doctor.getDisponibilites().isEmpty() ||
                doctor.getDisponibilites().stream()
                        .noneMatch(disponibilite -> {
                            LocalDateTime rendezVousStartTime = rendezVous.getDate();
                            LocalDateTime rendezVousEndTime = rendezVousStartTime.plusMinutes(30); // Ajouter 30 minutes au rendez-vous

                            LocalDateTime disponibiliteStartTime = disponibilite.getStartTime();
                            LocalDateTime disponibiliteEndTime = disponibilite.getEndTime();

                            // Vérifier si le rendez-vous chevauche une disponibilité du médecin avec statut "RESERVED"
                            return disponibilite.getStatus() == Status.RESERVED &&
                                    ((rendezVousStartTime.isBefore(disponibiliteEndTime) || rendezVousStartTime.equals(disponibiliteEndTime)) &&
                                            (rendezVousEndTime.isAfter(disponibiliteStartTime) || rendezVousEndTime.equals(disponibiliteStartTime)));
                        });

        if (!isDisponible) {
            throw new DoctorNotAvailableException("Le médecin n'est pas disponible à la date spécifiée. Veuillez choisir une autre date.");
        }

        // Créer le rendez-vous
        rendezVous.setPatient(patient);
        rendezVous.setDoctor(doctor);
        patient.getRendezVous().add(rendezVous);
        doctor.getRendezVous().add(rendezVous);

        // Enregistrer le rendez-vous
        Rendez_Vous rendezvous = rendezVousRepository.save(rendezVous);

        // Créer une disponibilité avec statut "RESERVED" pour le médecin
        Disponibilite disponibilite = new Disponibilite();
        disponibilite.setDoctor(doctor);
        disponibilite.setStatus(Status.RESERVED);
        disponibilite.setStartTime(rendezVous.getDate());
        disponibilite.setEndTime(rendezVous.getDate().plusMinutes(30)); // Ajouter 30 minutes à la disponibilité

        // Enregistrer la disponibilité dans la base de données
        disponibilityRepository.save(disponibilite);

        // Enregistrer les modifications
        patientRepository.save(patient);
        doctorRepository.save(doctor);

        return rendezvous.toString();
    }

    @Override
    public void deleteRendezVous(long rendezVousId) {
        // Rechercher le rendez-vous dans la base de données
        Rendez_Vous rendezVous = rendezVousRepository.findById(rendezVousId)
                .orElseThrow(() -> new RessourceNotFound("Aucun rendez-vous avec l'id :" + rendezVousId));

        // Récupérer le patient associé au rendez-vous
        Patient patient = rendezVous.getPatient();

        // Récupérer le médecin associé au rendez-vous
        Doctor doctor = rendezVous.getDoctor();

        // Rechercher la disponibilité associée au rendez-vous
        Disponibilite disponibilite = doctor.getDisponibilites().stream()
                .filter(d -> d.getStartTime().isEqual(rendezVous.getDate()))
                .findFirst()
                .orElseThrow(() -> new RessourceNotFound("Aucune disponibilité associée à ce rendez-vous"));

        // Supprimer la disponibilité de la liste de disponibilités du médecin
        doctor.getDisponibilites().remove(disponibilite);

        // Supprimer la disponibilité de la base de données
        disponibilityRepository.delete(disponibilite);

        // Retirer le rendez-vous des listes de rendez-vous du patient et du médecin
        patient.getRendezVous().remove(rendezVous);
        doctor.getRendezVous().remove(rendezVous);

        // Enregistrer les modifications dans la base de données
        patientRepository.save(patient);
        doctorRepository.save(doctor);

        // Supprimer le rendez-vous de la base de données
     rendezVousRepository.delete(rendezVous);
    }

    @Override
    public List<Rendez_Vous> getAllRendezVousByPatientId(Long idPatient) {
        List<Rendez_Vous>listeRendezVous=rendezVousRepository.findByPatientId(idPatient);

        return listeRendezVous;
    }

    @Override
    public Rendez_Vous getRendezVousByid(Long idRendezVous) {
        Rendez_Vous rendezVous=rendezVousRepository.findById(idRendezVous).orElseThrow(()->new RessourceNotFound("Accun rendez Vous avec cet id : "+idRendezVous));
        return rendezVous;
    }


}
