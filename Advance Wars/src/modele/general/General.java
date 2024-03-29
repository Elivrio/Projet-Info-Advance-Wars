package src.modele.general;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.combat.Combat;
import src.modele.interfaces.typeunite.TypeUnite;
import src.modele.interfaces.deplacement.Deplacement;

public abstract class General extends AbstractUnite {

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param n   Nom du general.
   * @param pM  Points de vie maximum.
   * @param c   Type de combat.
   * @param d   Type de deplacement.
   * @param dis Distance possible de deplacements par tour.
   * @param por Portee du joueur.
   * @param vis Nombre de cases de visibilite du general.
   * @param t   Type de l'unite.
   * @param j   Joueur possedant l'unite.
   * @param x   Position de l'unite en abscisse.
   * @param y   Position de l'unite en ordonnee.
   * @param i   L'indice de l'unite dans le tableau du type d'unite.
   */
  public General (String n, int pM, Combat c, Deplacement d, int dis, int por, int vis, TypeUnite t, Joueur j, int x, int y, int i) {
    super(n, pM, c, d, dis, por, vis, 30000, t, j, x, y, i);
  }
}
