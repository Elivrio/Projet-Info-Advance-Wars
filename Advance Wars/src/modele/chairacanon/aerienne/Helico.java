package src.modele.chairacanon.aerienne;

import src.modele.AbstractUnite;
import src.modele.Joueur;
import src.modele.interfaces.combat.CombatMitrailleuse;
import src.modele.interfaces.typeunite.Aerienne;


public class Helico extends AbstractUnite {

  public Helico (Joueur j) {
    super("Hélico", 99, new CombatMitrailleuse(), null, 6, 1, 3, 99, 9000, new Aerienne(), j);
  }
}
