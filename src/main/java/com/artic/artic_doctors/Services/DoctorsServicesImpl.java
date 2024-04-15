package com.artic.artic_doctors.Services;

import com.artic.artic_doctors.Entities.Doctor;
import com.artic.artic_doctors.Repository.IDoctorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorsServicesImpl implements IdoctorService{
    private final IDoctorRepository doctorRepository;
    @Override
    public List<Doctor> recupreAllDoctors() {
       List<Doctor>doctors=doctorRepository.findAll();
       return doctors;
    }
}
