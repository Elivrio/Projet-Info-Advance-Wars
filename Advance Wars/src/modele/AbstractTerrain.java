package src.modele;

import src.modele.Joueur;
public abstract class AbstractTerrain {

  // ***************************************************
  // *************** Variable d'instance ***************
  // ***************************************************

  protected final String nom;
  protected final int type;

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param s Le nom du terrain.
   * @param t Le type du terrain.
   */
  public AbstractTerrain (String s, int t) {
    nom = s;
    type = t;
  }

  // ***************************************
  // *************** Getters ***************
  // ***************************************

  public int getType() { return type; }

  public Joueur getJoueur() { return null; }

  public String getNom() { return nom; }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Permet d'afficher l'objet sous forme d'une chaîne de caratère.
   * @return La chaîne de caractère qui va décrire l'objet.
   */
  @Override
  public String toString() {
    return ("" + type);
  }


}
