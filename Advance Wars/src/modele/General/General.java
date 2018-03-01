package src.modele.general;

import src.modele.AbstractUnite;
import src.modele.Joueur;
import src.modele.interfaces.combat.Combat;
import src.modele.interfaces.deplacement.Deplacement;
import src.modele.interfaces.typeunite.TypeUnite;

abstract class General extends AbstractUnite {

  public General (String n, int pM, Combat c, Deplacement d, int dis, TypeUnite t, Joueur j) {
    super(n, pM, c, d, dis, t, j);
  }

  abstract void bonusPersonel();
}
