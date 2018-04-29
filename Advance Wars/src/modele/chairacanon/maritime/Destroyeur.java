package src.modele.chairacanon.maritime;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Maritime;
import src.modele.interfaces.combat.CombatCouteau;

public class Destroyeur extends AbstractUnite {

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param j Le joueur auquel appartient l'unite.
   * @param x La position de l'unite en abscisse.
   * @param y La position de l'unite en ordonnee.
   */
  public Destroyeur (Joueur j, int x, int y) {
    super("Destroyeur (Croiseur/Cruiser)", 99, new CombatCouteau(), null, 6, 1, 3, 99, 18000, new Maritime(), j, x, y, 17);
  }
}
