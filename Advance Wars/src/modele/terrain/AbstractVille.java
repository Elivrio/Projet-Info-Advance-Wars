package src.modele.terrain;

import src.modele.Joueur;
import src.modele.AbstractTerrain;

public abstract class AbstractVille extends AbstractTerrain {

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  // Joueur possedant la ville.
  protected Joueur joueur;

  // Position en abscisse et ordonnee de la ville.
  protected int x, y;

  // Indique si la ville a deja cree une unite ce tour-ci.
  protected boolean achete;

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

/**
 * @param s Nom du terrain.
 * @param n Type de terrain.
 * @param l Type de terrain pour les liaisons.
 * @param x Position du terrain en abscisse.
 * @param y Position du terrain en ordonnee.
 */
  public AbstractVille (String s, int n, int l, int x, int y) {
    super(s, n, l);
    joueur = null;
    this.x = x;
    this.y = y;
  }

  // ***************************************
  // *************** Getters ***************
  // ***************************************

  public boolean getAchete() { return achete; }

  public int getX() { return x; }

  public int getY() { return y; }

  public Joueur getJoueur() { return joueur; }

  // ***************************************
  // *************** Setters ***************
  // ***************************************

  public void setAchete (boolean b) {
    achete = b;
  }

  public void setJoueur (Joueur j) {
    joueur = j;
  }

}
