package com.artic.artic_doctors.Repository;

import com.artic.artic_doctors.Entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDoctorRepository extends JpaRepository<Doctor,Long> {

}
