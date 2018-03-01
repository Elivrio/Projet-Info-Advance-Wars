package src.modele.general;

import src.modele.AbstractUnite;
import src.modele.Joueur;
import src.modele.interfaces.combat.CombatCouteau;
import src.modele.interfaces.deplacement.DeplaceAPied;
import src.modele.interfaces.typeunite.Terrestre;

public class MadZombie extends General{

  public MadZombie(){
    super("Soldat", 100, new CombatCouteau(), new DeplaceAPied(), 5, new Terrestre(), new Joueur("Nosaure", 10, 10));
  }

  public void bonusPersonel(){
  }
}
