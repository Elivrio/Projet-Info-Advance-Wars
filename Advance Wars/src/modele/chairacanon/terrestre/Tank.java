package src.modele.chairacanon.terrestre;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Terrestre;
import src.modele.interfaces.combat.CombatMitrailleuse;
import src.modele.interfaces.deplacement.DeplaceAChenilles;

public class Tank extends AbstractUnite {

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param j Le joueur auquel appartient l'unite.
   * @param x La position de l'unite en abscisse.
   * @param y La position de l'unite en ordonnee.
   */
  public Tank (Joueur j, int x, int y) {
    super("Tank", 99, new CombatMitrailleuse(), new DeplaceAChenilles(), 6, 1, 3, 60, 7000, new Terrestre(), j, x, y, 5);
  }
}
