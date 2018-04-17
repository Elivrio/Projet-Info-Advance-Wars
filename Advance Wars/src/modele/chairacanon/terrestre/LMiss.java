package src.modele.chairacanon.terrestre;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Terrestre;
import src.modele.interfaces.combat.CombatCouteau;
import src.modele.interfaces.deplacement.DeplaceARoues;

public class LMiss extends AbstractUnite {

  /**
   * @param j Le joueur auquel appartient l'unité.
   * @param x La position de l'unité en abscisse.
   * @param y La position de l'unité en ordonnée.
   */
  public LMiss (Joueur j, int x, int y) {
    super("L-Miss", 99, new CombatCouteau(), new DeplaceARoues(), 5, 5, 0, 0, 15000, new Terrestre(), j, x, y, 14);
  }
}
