package src.modele.chairacanon.terrestre;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Terrestre;
import src.modele.interfaces.deplacement.DeplaceAPied;
import src.modele.interfaces.combat.CombatMitrailleuse;

public class Bazooka extends AbstractUnite {

  /**
   * @param j Le joueur auquel appartient l'unité.
   * @param x La position de l'unité en abscisse.
   * @param y La position de l'unité en ordonnée.
   */
  public Bazooka (Joueur j, int x, int y) {
    super("Bazooka", 99, new CombatMitrailleuse(), new DeplaceAPied(), 2, 1, 2, 99, 3000, new Terrestre(), j, x, y, 8);
  }
}
