package src.modele.general;

import src.modele.AbstractUnite;
import src.modele.Joueur;
import src.modele.interfaces.combat.Combat;
import src.modele.interfaces.deplacement.Deplacement;
import src.modele.interfaces.typeunite.TypeUnite;

abstract class General extends AbstractUnite {

  public General (String n, int pM, Combat c, Deplacement d, int dis, int vis, int ess, TypeUnite t, Joueur j, int x, int y) {
    super(n, pM, c, d, dis, 3, vis, ess, 0, t, j, x, y);
  }

  abstract void bonusPersonel();
}
