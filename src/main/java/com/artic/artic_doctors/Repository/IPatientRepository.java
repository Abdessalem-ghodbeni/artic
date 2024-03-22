package com.artic.artic_doctors.Repository;

import com.artic.artic_doctors.Entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPatientRepository extends JpaRepository<Patient,Long> {
}
