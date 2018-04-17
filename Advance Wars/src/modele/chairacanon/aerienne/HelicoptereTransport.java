package src.modele.chairacanon.aerienne;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Aerienne;

public class HelicoptereTransport extends AbstractUnite {

  /**
   * @param j Le joueur auquel appartient l'unité.
   * @param x La position de l'unité en abscisse.
   * @param y La position de l'unité en ordonnée.
   */
  public HelicoptereTransport (Joueur j, int x, int y) {
    super("Hélicoptère de Transport", 99, null, null, 6, 0, 1, 99, 5000, new Aerienne(), j, x, y, 5);
  }
}
