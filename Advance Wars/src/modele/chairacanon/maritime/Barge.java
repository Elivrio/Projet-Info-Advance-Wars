package src.modele.chairacanon.maritime;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Maritime;

public class Barge extends AbstractUnite {

  /**
   * @param j Le joueur auquel appartient l'unité.
   * @param x La position de l'unité en abscisse.
   * @param y La position de l'unité en ordonnée.
   */
  public Barge (Joueur j, int x, int y) {
    super("Barge", 99, null, null, 6, 0, 1, 99, 12000, new Maritime(), j, x, y, 5);
  }
}
