package src.modele.terrain;

import src.modele.terrain.AbstractVille;

public class Usine extends AbstractVille {

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param x Position du terrain en abscisse.
   * @param y Position du terrain en ordonnee.
   */
  public Usine (int x, int y) {
    super("Usine", 5, 1, x, y);
  }
}
