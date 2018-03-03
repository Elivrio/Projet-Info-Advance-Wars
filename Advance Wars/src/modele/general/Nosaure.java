package src.modele.general;

import src.modele.AbstractUnite;
import src.modele.Joueur;
import src.modele.interfaces.combat.CombatCouteau;
import src.modele.interfaces.deplacement.DeplaceAPied;
import src.modele.interfaces.typeunite.Terrestre;

public class Nosaure extends General {

  public Nosaure (Joueur j) {
    super("Nosaure", 100, new CombatCouteau(), new DeplaceAPied(), 7, 2, 100, new Terrestre(), j);
  }

  public void bonusPersonel() {}
}
