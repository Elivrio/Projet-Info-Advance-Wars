package src.modele.chairacanon.aerienne;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Aerienne;
import src.modele.interfaces.combat.CombatCouteau;

public class Chasseur extends AbstractUnite {

  public Chasseur (Joueur j, int x, int y) {
    super("Chasseur", 99, new CombatCouteau(), null, 9, 1, 2, 99, 20000, new Aerienne(), j, x, y, 5);
  }
}
