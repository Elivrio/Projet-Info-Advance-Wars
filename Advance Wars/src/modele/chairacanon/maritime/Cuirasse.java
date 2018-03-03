package src.modele.chairacanon.maritime;

import src.modele.AbstractUnite;
import src.modele.Joueur;
import src.modele.interfaces.combat.CombatCouteau;
import src.modele.interfaces.typeunite.Maritime;


public class Cuirasse extends AbstractUnite {

  public Cuirasse (Joueur j, int x, int y) {
    super("Cuirassé (Destroyer)", 99, new CombatCouteau(), null, 5, 6, 2, 99, 28000, new Maritime(), j, x, y);
  }
}
