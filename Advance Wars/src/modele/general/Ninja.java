package src.modele.general;

import src.modele.AbstractUnite;
import src.modele.Joueur;
import src.modele.interfaces.combat.CombatCouteau;
import src.modele.interfaces.deplacement.DeplaceAPied;
import src.modele.interfaces.typeunite.Terrestre;

public class Ninja extends General {

  public Ninja (Joueur j, int x, int y) {
    super("Ninja", 100, new CombatCouteau(), new DeplaceAPied(), 5, 5, 100, new Terrestre(), j, x, y, 2);
  }

  public void bonusPersonel() {}
}
