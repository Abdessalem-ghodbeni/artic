package com.artic.artic_doctors.Services;

import com.artic.artic_doctors.Entities.*;

public interface IAuthenticationServices {

  Patient registerPatient(Patient patient);
  public  AuthenticationResponse login(String email, String password);
  public  AuthenticationResponse refreshToken(RefreshTokenRequest refreshToken);
  public  Admin  registerAdmin(Admin admin);
public Doctor registerDoctor (Doctor doctor);

}
