package src.modele.chairacanon.terrestre;

import src.modele.AbstractUnite;
import src.modele.Joueur;
import src.modele.interfaces.combat.CombatCouteau;
import src.modele.interfaces.deplacement.DeplaceAChenilles;
import src.modele.interfaces.typeunite.Terrestre;


public class DCA extends AbstractUnite {

  public DCA (Joueur j, int x, int y) {
    super("DCA", 99, new CombatCouteau(), new DeplaceAChenilles(), 6, 1, 2, 60, 8000, new Terrestre(), j, x, y, 0);
  }
}