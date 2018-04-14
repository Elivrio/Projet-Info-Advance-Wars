package src.modele.general;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Terrestre;
import src.modele.interfaces.combat.CombatCouteau;
import src.modele.interfaces.deplacement.DeplaceAPied;

public class MagicalGirl extends General {

  public MagicalGirl (Joueur j, int x, int y) {
    super("Magical Girl", 100, new CombatCouteau(), new DeplaceAPied(), 3, 5, 100, new Terrestre(), j, x, y, 4);
  }

  public MagicalGirl(Joueur j) {
    this(j, 0, 0);
  }

  public void bonusPersonel() {}
}
