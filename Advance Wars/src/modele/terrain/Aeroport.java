package src.modele.terrain;

import src.modele.terrain.AbstractVille;

public class Aeroport extends AbstractVille {

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param x Position du terrain en abscisse.
   * @param y Position du terrain en ordonnee.
   */
  public Aeroport (int x, int y) {
    super("Aeroport", 7, x, y);
  }
}
