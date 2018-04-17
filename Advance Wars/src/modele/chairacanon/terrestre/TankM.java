package src.modele.chairacanon.terrestre;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Terrestre;
import src.modele.interfaces.combat.CombatMitrailleuse;
import src.modele.interfaces.deplacement.DeplaceAChenilles;

public class TankM extends AbstractUnite {

  /**
   * @param j Le joueur auquel appartient l'unité.
   * @param x La position de l'unité en abscisse.
   * @param y La position de l'unité en ordonnée.
   */
  public TankM (Joueur j, int x, int y) {
    super("Tank M", 99, new CombatMitrailleuse(), new DeplaceAChenilles(), 5, 1, 1, 50, 16000, new Terrestre(), j, x, y, 9);
  }
}
