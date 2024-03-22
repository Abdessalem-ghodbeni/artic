package com.artic.artic_doctors.Repository;

import com.artic.artic_doctors.Entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminRepository extends JpaRepository<Admin, Long> {
}
