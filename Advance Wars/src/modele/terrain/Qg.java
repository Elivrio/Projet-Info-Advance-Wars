package src.modele.terrain;

import src.modele.Joueur;
import src.modele.AbstractTerrain;

public class Qg extends AbstractVille {

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param x Position du terrain en abscisse.
   * @param y Position du terrain en ordonnee.
   */
  public Qg (int x, int y) {
    super("QG", 9, 1, x, y);
  }
}
