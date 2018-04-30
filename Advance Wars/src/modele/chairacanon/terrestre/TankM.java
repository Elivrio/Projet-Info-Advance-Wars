package src.modele.chairacanon.terrestre;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Terrestre;
import src.modele.interfaces.combat.CombatMitrailleuse;
import src.modele.interfaces.deplacement.DeplaceAChenilles;

public class TankM extends AbstractUnite {

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param j Le joueur auquel appartient l'unite.
   * @param x La position de l'unite en abscisse.
   * @param y La position de l'unite en ordonnee.
   */
  public TankM (Joueur j, int x, int y) {
    super("Tank M", 99, new CombatMitrailleuse(), new DeplaceAChenilles(), 5, 1, 1, 16000, new Terrestre(), j, x, y, 9);
  }
}
