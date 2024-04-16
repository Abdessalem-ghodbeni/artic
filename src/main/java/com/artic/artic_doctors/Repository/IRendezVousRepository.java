package com.artic.artic_doctors.Repository;

import com.artic.artic_doctors.Entities.Rendez_Vous;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRendezVousRepository extends JpaRepository<Rendez_Vous,Long> {

    List<Rendez_Vous>findByPatientId(Long patientId);
}
