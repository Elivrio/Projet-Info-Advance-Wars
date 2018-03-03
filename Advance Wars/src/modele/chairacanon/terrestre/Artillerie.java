package src.modele.chairacanon.terrestre;

import src.modele.AbstractUnite;
import src.modele.Joueur;
import src.modele.interfaces.combat.CombatCouteau;
import src.modele.interfaces.deplacement.DeplaceAChenilles;
import src.modele.interfaces.typeunite.Terrestre;


public class Artillerie extends AbstractUnite {

  public Artillerie (Joueur j) {
    super("Artillerie", 99, new CombatCouteau(), new DeplaceAChenilles(), 5, 3, 1, 50, 6000, new Terrestre(), j);
  }
}
