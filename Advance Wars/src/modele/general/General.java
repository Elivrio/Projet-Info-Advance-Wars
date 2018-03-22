package src.modele.general;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.combat.Combat;
import src.modele.interfaces.typeunite.TypeUnite;
import src.modele.interfaces.deplacement.Deplacement;

public abstract class General extends AbstractUnite {

  public General (String n, int pM, Combat c, Deplacement d, int dis, int vis, int ess, TypeUnite t, Joueur j, int x, int y, int i) {
    super(n, pM, c, d, dis, 3, vis, ess, 30000, t, j, x, y, i);
  }

  abstract void bonusPersonel();
}
