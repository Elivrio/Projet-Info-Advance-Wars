package src.modele.chairacanon.aerienne;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Aerienne;
import src.modele.interfaces.combat.CombatMitrailleuse;

public class Helico extends AbstractUnite {

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param j Le joueur auquel appartient l'unite.
   * @param x La position de l'unite en abscisse.
   * @param y La position de l'unite en ordonnee.
   */
  public Helico (Joueur j, int x, int y) {
    super("Helico", 99, new CombatMitrailleuse(), null, 6, 1, 3, 9000, new Aerienne(), j, x, y, 19);
  }
}
