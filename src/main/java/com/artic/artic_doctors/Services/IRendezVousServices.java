package com.artic.artic_doctors.Services;

import com.artic.artic_doctors.Entities.Rendez_Vous;

public interface IRendezVousServices {

  public String addRendezVous(Rendez_Vous Rendez_Vous);
  void deleteRendezVous(long rendezVousId);

}
