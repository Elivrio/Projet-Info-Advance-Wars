package src.modele.chairacanon.terrestre;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Terrestre;
import src.modele.interfaces.combat.CombatCouteau;

public class AAir extends AbstractUnite {

  /**
   * @param j Le joueur auquel appartient l'unité.
   * @param x La position de l'unité en abscisse.
   * @param y La position de l'unité en ordonnée.
   */
  public AAir (Joueur j, int x, int y) {
    super("A-Air", 99, new CombatCouteau(), null, 4, 5, 0, 0, 12000, new Terrestre(), j, x, y, 12);
  }
}
