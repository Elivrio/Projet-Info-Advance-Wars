package src.modele.chairacanon.aerienne;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Aerienne;
import src.modele.interfaces.combat.CombatCouteau;

public class Bombardier extends AbstractUnite {

  public Bombardier (Joueur j, int x, int y) {
    super("Bombardier", 99, new CombatCouteau(), null, 8, 1, 2, 99, 22000, new Aerienne(), j, x, y, 5);
  }
}
