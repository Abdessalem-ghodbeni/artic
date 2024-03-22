package com.artic.artic_doctors.Repository;

import com.artic.artic_doctors.Entities.Rendez_Vous;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRendezVousRepository extends JpaRepository<Rendez_Vous,Long> {
}
