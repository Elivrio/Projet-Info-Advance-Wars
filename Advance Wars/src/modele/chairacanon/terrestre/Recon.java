package src.modele.chairacanon.terrestre;

import src.modele.AbstractUnite;
import src.modele.Joueur;
import src.modele.interfaces.combat.CombatMitrailleuse;
import src.modele.interfaces.deplacement.DeplaceARoues;
import src.modele.interfaces.typeunite.Terrestre;


public class Recon extends AbstractUnite {

  public Recon (Joueur j, int x, int y) {
    super("Recon", 99, new CombatMitrailleuse(), new DeplaceARoues(), 8, 1, 5, 80, 4000, new Terrestre(), j, x, y, 0);
  }
}
