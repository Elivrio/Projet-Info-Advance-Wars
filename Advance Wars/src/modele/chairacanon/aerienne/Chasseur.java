package src.modele.chairacanon.aerienne;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Aerienne;
import src.modele.interfaces.combat.CombatCouteau;

public class Chasseur extends AbstractUnite {

  /**
   * @param j Le joueur auquel appartient l'unité.
   * @param x La position de l'unité en abscisse.
   * @param y La position de l'unité en ordonnée.
   */
  public Chasseur (Joueur j, int x, int y) {
    super("Chasseur", 99, new CombatCouteau(), null, 9, 1, 2, 99, 20000, new Aerienne(), j, x, y, 5);
  }
}
