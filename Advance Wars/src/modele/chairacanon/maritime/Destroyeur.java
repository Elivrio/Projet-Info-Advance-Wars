package src.modele.chairacanon.maritime;

import src.modele.AbstractUnite;
import src.modele.Joueur;
import src.modele.interfaces.combat.CombatCouteau;
import src.modele.interfaces.typeunite.Maritime;


public class Destroyeur extends AbstractUnite {

  public Destroyeur (Joueur j) {
    super("Destroyeur (Croiseur/Cruiser)", 99, new CombatCouteau(), null, 6, 1, 3, 99, 18000, new Maritime(), j);
  }
}
