package src.modele.chairacanon.terrestre;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Terrestre;
import src.modele.interfaces.deplacement.DeplaceAChenilles;

public class VTB extends AbstractUnite {

  /**
   * @param j Le joueur auquel appartient l'unité.
   * @param x La position de l'unité en abscisse.
   * @param y La position de l'unité en ordonnée.
   */
  public VTB (Joueur j, int x, int y) {
    super("VTB", 99, null, new DeplaceAChenilles(), 6, 0, 1, 70, 5000, new Terrestre(), j, x, y, 11);
  }
}
