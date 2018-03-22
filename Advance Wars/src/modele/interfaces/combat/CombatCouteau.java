package src.modele.interfaces.combat;

import src.modele.interfaces.combat.Combat;

public class CombatCouteau implements Combat {

  protected int degats = 5;

  public String combat() {
    return "Se bat au couteau";
  }

  public int getDegats() {
    return degats;
  }
  
}
