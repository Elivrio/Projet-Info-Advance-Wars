package src.modele.chairacanon.aerienne;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Aerienne;
import src.modele.interfaces.combat.CombatMitrailleuse;

public class Helico extends AbstractUnite {

  /**
   * @param j Le joueur auquel appartient l'unité.
   * @param x La position de l'unité en abscisse.
   * @param y La position de l'unité en ordonnée.
   */
  public Helico (Joueur j, int x, int y) {
    super("Hélico", 99, new CombatMitrailleuse(), null, 6, 1, 3, 99, 9000, new Aerienne(), j, x, y, 5);
  }
}
