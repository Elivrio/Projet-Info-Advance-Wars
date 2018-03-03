package src.modele.general;

import src.modele.AbstractUnite;
import src.modele.Joueur;
import src.modele.interfaces.combat.CombatCouteau;
import src.modele.interfaces.deplacement.DeplaceAPied;
import src.modele.interfaces.typeunite.Terrestre;

public class Ninja extends General {

  public Ninja (Joueur j) {
    super("Ninja", 100, new CombatCouteau(), new DeplaceAPied(), 5, 5, 100, new Terrestre(), j);
  }

  public void bonusPersonel() {}
}
