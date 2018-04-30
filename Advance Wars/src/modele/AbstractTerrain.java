package src.modele;

import src.modele.Joueur;
public abstract class AbstractTerrain {

  // ***************************************************
  // *************** Variable d'instance ***************
  // ***************************************************

  protected final String nom;
  protected final int type;
  protected final int typeLiaison;

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param s Le nom du terrain.
   * @param t Le type du terrain.
   * @param l le type du terrain pour les liaisons.
   */
  public AbstractTerrain (String s, int t, int l) {
    nom = s;
    type = t;
    typeLiaison = l;
  }

  // ***************************************
  // *************** Getters ***************
  // ***************************************

  public int getType() { return type; }

  public Joueur getJoueur() { return null; }

  public String getNom() { return nom; }

  public int getTypeLiaison() { return typeLiaison; }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Permet d'afficher l'objet sous forme d'une chaîne de caratere.
   * @return La chaîne de caractere qui va decrire l'objet.
   */
  @Override
  public String toString() {
    return ("" + type);
  }


}
