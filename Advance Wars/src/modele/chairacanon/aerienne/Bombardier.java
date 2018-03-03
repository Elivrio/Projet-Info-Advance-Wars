package src.modele.chairacanon.aerienne;

import src.modele.AbstractUnite;
import src.modele.Joueur;
import src.modele.interfaces.combat.CombatCouteau;
import src.modele.interfaces.typeunite.Aerienne;


public class Bombardier extends AbstractUnite {

  public Bombardier (Joueur j) {
    super("Bombardier", 99, new CombatCouteau(), null, 8, 1, 2, 99, 22000, new Aerienne(), j);
  }
}
