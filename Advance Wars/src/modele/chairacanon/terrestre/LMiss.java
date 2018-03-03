package src.modele.chairacanon.terrestre;

import src.modele.AbstractUnite;
import src.modele.Joueur;
import src.modele.interfaces.combat.CombatCouteau;
import src.modele.interfaces.deplacement.DeplaceARoues;
import src.modele.interfaces.typeunite.Terrestre;


public class LMiss extends AbstractUnite {

  public LMiss (Joueur j) {
    super("L-Miss", 99, new CombatCouteau(), new DeplaceARoues(), 5, 5, 0, 0, 15000, new Terrestre(), j);
  }
}
