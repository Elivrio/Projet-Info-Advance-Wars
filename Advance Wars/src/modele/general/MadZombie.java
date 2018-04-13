package src.modele.general;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Terrestre;
import src.modele.interfaces.combat.CombatCouteau;
import src.modele.interfaces.deplacement.DeplaceAPied;

public class MadZombie extends General {

  public MadZombie (Joueur j, int x, int y) {
    super("MadZombie", 100, new CombatCouteau(), new DeplaceAPied(), 5, 2, 100, new Terrestre(), j, x, y, 1);
  }

  public MadZombie(Joueur j) {
    this(j, 0, 0);
  }

  public void bonusPersonel() {}
}
