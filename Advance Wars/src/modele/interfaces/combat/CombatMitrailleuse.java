package src.modele.interfaces.combat;

import src.modele.interfaces.combat.Combat;

public class CombatMitrailleuse implements Combat {

  protected int degats = 10;

  public String combat() {
    return "Se bat à la mitrailleuse";
  }

  public int getDegats() {
    return degats;
  }

}
