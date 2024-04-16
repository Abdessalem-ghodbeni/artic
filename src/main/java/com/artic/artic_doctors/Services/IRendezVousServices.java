package com.artic.artic_doctors.Services;

import com.artic.artic_doctors.Entities.Rendez_Vous;

import java.util.List;

public interface IRendezVousServices {

  public String addRendezVous(Rendez_Vous Rendez_Vous);
  void deleteRendezVous(long rendezVousId);
  List<Rendez_Vous> getAllRendezVousByPatientId(Long idPatient);
  Rendez_Vous getRendezVousByid(Long idRendezVous);

}
