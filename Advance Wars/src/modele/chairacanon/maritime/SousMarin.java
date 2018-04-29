package src.modele.chairacanon.maritime;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Maritime;
import src.modele.interfaces.combat.CombatCouteau;

public class SousMarin extends AbstractUnite {

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param j Le joueur auquel appartient l'unite.
   * @param x La position de l'unite en abscisse.
   * @param y La position de l'unite en ordonnee.
   */
  public SousMarin (Joueur j, int x, int y) {
    super("Sous-Marin", 99, new CombatCouteau(), null, 5, 1, 5, 60, 20000, new Maritime(), j, x, y, 16);
  }
}
