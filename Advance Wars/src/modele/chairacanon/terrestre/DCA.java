package src.modele.chairacanon.terrestre;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Terrestre;
import src.modele.interfaces.combat.CombatCouteau;
import src.modele.interfaces.deplacement.DeplaceAChenilles;

public class DCA extends AbstractUnite {

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param j Le joueur auquel appartient l'unite.
   * @param x La position de l'unite en abscisse.
   * @param y La position de l'unite en ordonnee.
   */
  public DCA (Joueur j, int x, int y) {
    super("DCA", 99, new CombatCouteau(), new DeplaceAChenilles(), 6, 1, 2, 60, 8000, new Terrestre(), j, x, y, 13);
  }
}
