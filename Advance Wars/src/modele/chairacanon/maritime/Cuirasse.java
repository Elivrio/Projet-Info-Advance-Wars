package src.modele.chairacanon.maritime;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Maritime;
import src.modele.interfaces.combat.CombatCouteau;

public class Cuirasse extends AbstractUnite {

  /**
   * @param j Le joueur auquel appartient l'unité.
   * @param x La position de l'unité en abscisse.
   * @param y La position de l'unité en ordonnée.
   */
  public Cuirasse (Joueur j, int x, int y) {
    super("Cuirassé (Destroyer)", 99, new CombatCouteau(), null, 5, 6, 2, 99, 28000, new Maritime(), j, x, y, 5);
  }
}
