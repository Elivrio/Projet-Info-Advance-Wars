package src.modele.chairacanon.terrestre;

import src.modele.AbstractUnite;
import src.modele.Joueur;
import src.modele.interfaces.combat.CombatMitrailleuse;
import src.modele.interfaces.deplacement.DeplaceAChenilles;
import src.modele.interfaces.typeunite.Terrestre;


public class Tank extends AbstractUnite {

  public Tank (Joueur j, int x, int y) {
    super("Tank", 99, new CombatMitrailleuse(), new DeplaceAChenilles(), 6, 1, 3, 60, 7000, new Terrestre(), j, x, y, 0);
  }
}
