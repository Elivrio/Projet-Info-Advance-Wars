package src.modele.chairacanon.terrestre;

import src.modele.AbstractUnite;
import src.modele.Joueur;
import src.modele.interfaces.combat.CombatMitrailleuse;
import src.modele.interfaces.deplacement.DeplaceAChenilles;
import src.modele.interfaces.typeunite.Terrestre;


public class TankM extends AbstractUnite {

  public TankM (Joueur j) {
    super("Tank M", 99, new CombatMitrailleuse(), new DeplaceAChenilles(), 5, 1, 1, 50, 16000, new Terrestre(), j);
  }
}
