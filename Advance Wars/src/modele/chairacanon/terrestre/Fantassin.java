package src.modele.chairacanon.terrestre;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Terrestre;
import src.modele.interfaces.deplacement.DeplaceAPied;
import src.modele.interfaces.combat.CombatMitrailleuse;

public class Fantassin extends AbstractUnite {

  /**
   * @param j Le joueur auquel appartient l'unité.
   * @param x La position de l'unité en abscisse.
   * @param y La position de l'unité en ordonnée.
   */
  public Fantassin (Joueur j, int x, int y) {
    super("Fantassin", 99, new CombatMitrailleuse(), new DeplaceAPied(), 3, 1, 2, 99, 1000, new Terrestre(), j, x, y, 6);
  }
}
