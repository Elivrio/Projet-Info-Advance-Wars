package src.modele;

import src.modele.AbstractUnite;
import src.modele.interfaces.combat.CombatCouteau;
import src.modele.interfaces.deplacement.DeplaceAPied;
import src.modele.interfaces.typeunite.Terrestre;


public class Soldat extends AbstractUnite {

  public Soldat() {
    super("Soldat", 15, new CombatCouteau(), new DeplaceAPied(), new Terrestre(), new Joueur("Nosaure", 10, 10));
  }

}
