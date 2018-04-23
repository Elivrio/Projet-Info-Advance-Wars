package src.modele.terrain;

import src.modele.terrain.AbstractVille;

public class Port extends AbstractVille {

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param x Position du terrain en abscisse.
   * @param y Position du terrain en ordonnee.
   */
  public Port (int x, int y) {
    super("Port", 6, x, y);
  }
}
