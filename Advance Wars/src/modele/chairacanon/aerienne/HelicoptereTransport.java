package src.modele.chairacanon.aerienne;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Aerienne;

public class HelicoptereTransport extends AbstractUnite {

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param j Le joueur auquel appartient l'unite.
   * @param x La position de l'unite en abscisse.
   * @param y La position de l'unite en ordonnee.
   */
  public HelicoptereTransport (Joueur j, int x, int y) {
    super("Helicoptere de Transport", 99, null, null, 6, 0, 1, 5000, new Aerienne(), j, x, y, 20);
  }
}
