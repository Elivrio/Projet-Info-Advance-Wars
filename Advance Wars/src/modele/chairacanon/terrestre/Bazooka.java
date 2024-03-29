package src.modele.chairacanon.terrestre;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Terrestre;
import src.modele.interfaces.deplacement.DeplaceAPied;
import src.modele.interfaces.combat.CombatMitrailleuse;

public class Bazooka extends AbstractUnite {

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param j Le joueur auquel appartient l'unite.
   * @param x La position de l'unite en abscisse.
   * @param y La position de l'unite en ordonnee.
   */
  public Bazooka (Joueur j, int x, int y) {
    super("Bazooka", 99, new CombatMitrailleuse(), new DeplaceAPied(), 2, 1, 2, 3000, new Terrestre(), j, x, y, 8);
  }
}
