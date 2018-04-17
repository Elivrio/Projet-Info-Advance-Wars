package src.modele.general;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.combat.Combat;
import src.modele.interfaces.typeunite.TypeUnite;
import src.modele.interfaces.deplacement.Deplacement;

public abstract class General extends AbstractUnite {

  /**
   * @param n   Nom du général.
   * @param pM  Points de vie maximum.
   * @param c   Type de combat.
   * @param d   Type de déplacement.
   * @param dis Distance possible de déplacements par tour.
   * @param vis Nombre de cases de visibilité du général.
   * @param ess [description]
   * @param t   Type de l'unité.
   * @param j   Joueur possédant l'unité.
   * @param x   Position de l'unité en abscisse.
   * @param y   Position de l'unité en ordonnée.
   * @param i   L'indice de l'unité dans le tableau du type d'unité.
   */
  public General (String n, int pM, Combat c, Deplacement d, int dis, int vis, int ess, TypeUnite t, Joueur j, int x, int y, int i) {
    super(n, pM, c, d, dis, 3, vis, ess, 30000, t, j, x, y, i);
  }

  abstract void bonusPersonel();
}
