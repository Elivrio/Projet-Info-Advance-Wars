package src.modele.chairacanon.terrestre;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Terrestre;
import src.modele.interfaces.deplacement.DeplaceARoues;
import src.modele.interfaces.combat.CombatMitrailleuse;

public class Recon extends AbstractUnite {

  /**
   * @param j Le joueur auquel appartient l'unité.
   * @param x La position de l'unité en abscisse.
   * @param y La position de l'unité en ordonnée.
   */
  public Recon (Joueur j, int x, int y) {
    super("Recon", 99, new CombatMitrailleuse(), new DeplaceARoues(), 8, 1, 5, 80, 4000, new Terrestre(), j, x, y, 7);
  }
}
