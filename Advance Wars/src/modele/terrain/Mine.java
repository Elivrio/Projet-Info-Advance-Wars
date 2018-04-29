package src.modele.terrain;

import src.modele.Joueur;
import src.modele.AbstractTerrain;

public class Mine extends AbstractVille {

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param x Position du terrain en abscisse.
   * @param y Position du terrain en ordonnee.
   */
  public Mine (int x, int y) {
    super("Mine", 8, 1, x, y);
  }
}
