package src.modele;

import java.awt.Color;
import java.util.LinkedList;

import src.variable.MyColor;
import src.modele.terrain.Foret;
import src.modele.AbstractUnite;
import src.modele.general.General;
import src.modele.terrain.Montagne;
import src.modele.terrain.AbstractVille;

public class Joueur {

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  // Le nom du joueur.
  private final String nom;

  // La liste de ses unites.
  private LinkedList<AbstractUnite> unites;

  // La vision du joueur en fonction de ses unites :
  //  0 --> Pas de vision (noir),
  //  1 --> Devoilee mais pas visible (gris),
  //  2 --> Vision directe.
  private int[][] vision;

  // L'argent que possede le joueur.
  private int argent;

  // La couleur du joueur.
  private MyColor couleur;

  // Le nombre de mines possedees par le joueur.
  private int mines;

  // *********************************************
  // **************** Constructeur ***************
  // *********************************************

  /**
   * @param n Nom du joueur.
   * @param x Largeur du terrain.
   * @param y Hauteur du terrain.
   * @param c Couleur choisie par le joueur.
   */
  public Joueur (String n, int x, int y, MyColor c) {
    nom = n;
    unites = new LinkedList<AbstractUnite>();
    vision = new int[y][x];
    couleur = c;
    argent = 10000;
    mines = 0;
  }

  // ***************************************
  // *************** Getters ***************
  // ***************************************

  public int getMines() { return mines; }
  public int getArgent() { return argent; }
  public int getNbUnites() { return unites.size(); }

  public int[][] getVision() { return vision; }

  public String getNom() { return nom; }

  public LinkedList<AbstractUnite> getUnites() { return unites; }

  public Color getColor() { return couleur; }

  // ***************************************
  // *************** Setters ***************
  // ***************************************

  /**
   * Modifie l'argent du joueur.
   * @param n Somme d'argent a ajouter.
   */
  public void setArgent (int n) {
    argent += n;
  }

  /**
   * Ajoute ou retire des mines au joueur.
   * @param n Quantite de mines a ajouter ou retirer.
   */
  public void addMine (int n) {
    mines += n;
  }

  /**
   * Permet à chaque unite de se deplacer et d'attaquer pour ce tour-ci.
   */
  public void reset() {
    for (int i = 0; i < unites.size(); i++)
    unites.get(i).reset();
  }

  /**
   * Retire une unite au joueur.
   * @param u Unite a retirer.
   */
  public void remove (AbstractUnite u) {
    unites.remove(u);
  }

  /**
   * Ajoute une unite au joueur et le fait payer si besoin.
   * @param u Unite a ajouter.
   * @param b Booleen permettant de savoir si le joueur doit payer son unite.
   */
  public void add (AbstractUnite u, boolean b) {
    if (b)
      argent -= u.getCout();
    unites.add(u);
  }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Permet de savoir si le joueur possede une unite.
   * @param  u Unite a verifier.
   * @return   Retourne un boleean.
   */
  public boolean possede (AbstractUnite u) {
    return unites.contains(u);
  }

  /**
   * Permet de mettre a jour la vision du joueur.
   * @param terrain Le terrain du jeu.
   */
  public void vision (AbstractTerrain[][] terrain) {
    // On passe toutes les cases de visibilite totale en brouillard gris.
    for (int i = 0; i < vision.length; i++)
      for (int j = 0; j < vision[0].length; j++) {
        if (vision[i][j] == 2 || vision[i][j] == 1)
          vision[i][j] = 1;
        // On garde la visibilite d'une eventuelle ville appartenant au joueur.
        if (terrain[i][j] instanceof AbstractVille
            && terrain[i][j].getJoueur() == this)
          vision[i][j] = 2;
      }
    // On met a jour la visibilite pour chaque unite.
    for (int i = 0; i < unites.size(); i++) {
      AbstractUnite unite = unites.get(i);
      vision(0, unite, unite.getX(), unite.getY(), terrain, unite.getVision());
    }
  }

  /**
   * Est appelee par la precedente fonction vision.
   * @param indice  Nombre de fois que la fonction a deja ete appelee.
   * @param unite   Unite dont on s'occupe.
   * @param x       Position de l'unite en abscisse.
   * @param y       Position de l'unite en ordonnee.
   * @param terrain Terrain du jeu.
   * @param n       Vision de l'unite.
   */
  public void vision (int indice, AbstractUnite unite, int x, int y, AbstractTerrain[][] terrain, int n) {
    // On passe la visibilite a la case sur laquelle on est.
    vision[y][x] = 2;
    // Si on est sur une montagne, on peut voir deux cases plus loin.
    if (terrain[y][x] instanceof Montagne && indice == 0)
      n += 2;
    // On passe la visibilite tout autour de la case si l'indice est inferieur a la visibilite.
    if (indice < n)
      for (int i = -1; i <= 1; i++)
        for (int j = -1; j <= 1; j++)
          if (i != j && i != -j
          && y+i >= 1 && y+i < vision.length - 1
          && x+j >= 1 && x+j < vision[0].length - 1) {
            vision[y+i][x+j] = 2;
            // Si la case autour valide l'une de ces conditions :
            // Appartient a la foret dans laquelle l'unite se trouve,
            // N'est pas de la foret,
            // Appartient a la chaine de montagnes dans laquelle l'unite se trouve,
            if ((!(terrain[y+i][x+j] instanceof Foret)
                || terrain[y][x] instanceof Foret)
                && (!(terrain[y+i][x+j] instanceof Montagne)
                || terrain[y][x] instanceof Montagne))
                // On rappelle la fonction sur cette case.
                vision(indice+1, unite, x+j, y+i, terrain, n);
          }
  }

  /**
   * On verifie si le joueur a perdu son general.
   * @return On retourne un booleen (true si le general est mort, false sinon).
   */
  public boolean generalMort() {
    return (unites.size() == 0 || (unites.size() != 0 && !(unites.get(0) instanceof General)));
  }
}
