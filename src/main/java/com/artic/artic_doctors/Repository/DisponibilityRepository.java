package com.artic.artic_doctors.Repository;

import com.artic.artic_doctors.Entities.Disponibilite;
import com.artic.artic_doctors.Entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DisponibilityRepository extends JpaRepository<Disponibilite,Long> {
    Optional<Disponibilite> findByDoctorAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(Doctor doctor, LocalDateTime startTime, LocalDateTime endTime);
Disponibilite findByDoctor(Doctor doctor);}