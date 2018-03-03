package src.modele.chairacanon.terrestre;

import src.modele.AbstractUnite;
import src.modele.Joueur;
import src.modele.interfaces.combat.CombatMitrailleuse;
import src.modele.interfaces.deplacement.DeplaceAPied;
import src.modele.interfaces.typeunite.Terrestre;


public class Bazooka extends AbstractUnite {

  public Bazooka (Joueur j) {
    super("Bazooka", 99, new CombatMitrailleuse(), new DeplaceAPied(), 2, 1, 2, 99, 3000, new Terrestre(), j);
  }
}
