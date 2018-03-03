package src.modele.chairacanon.maritime;

import src.modele.AbstractUnite;
import src.modele.Joueur;
import src.modele.interfaces.combat.CombatCouteau;
import src.modele.interfaces.typeunite.Maritime;


public class SousMarin extends AbstractUnite {

  public SousMarin (Joueur j) {
    super("Sous-Marin", 99, new CombatCouteau(), null, 5, 1, 5, 60, 20000, new Maritime(), j);
  }
}
