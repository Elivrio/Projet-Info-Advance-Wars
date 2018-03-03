package src.modele.chairacanon.terrestre;

import src.modele.AbstractUnite;
import src.modele.Joueur;
import src.modele.interfaces.combat.CombatMitrailleuse;
import src.modele.interfaces.deplacement.DeplaceAPied;
import src.modele.interfaces.typeunite.Terrestre;


public class Fantassin extends AbstractUnite {

  public Fantassin (Joueur j, int x, int y) {
    super("Fantassin", 99, new CombatMitrailleuse(), new DeplaceAPied(), 3, 1, 2, 99, 1000, new Terrestre(), j, x, y);
  }
}
