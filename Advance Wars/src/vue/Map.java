package src.vue;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.util.LinkedList;

import src.modele.Joueur;
import src.modele.Plateau;
import src.modele.AbstractUnite;
import src.modele.AbstractTerrain;
import src.modele.terrain.AbstractVille;

// Les classes PanelMap et Minimap ayant beaucoup de variable en commun, nous avons préféré créer
// cette classe mère qui permet de regrouper les variables et les fonctions communes.

@SuppressWarnings("serial")
abstract public class Map extends JPanel {

  // ***************************************************
  // *************** Variable d'instance ***************
  // ***************************************************

  // Le Plateau de jeu
  protected Plateau plateau;

  // Permet de définir la place occuper par la carte sur la fenêtre.
  protected int hauteur, largeur;

  // Permet de se déplacer dans le tableau de terrain afin d'afficher les bonnes cases.
  protected int tabI, tabJ;

  // Permet de stocker la hauteur et la largeur de l'écran sur lequel on travaille.
  protected final double largeurEcran, hauteurEcran;

  // Ces entiers sont des bornes permettant de savoir combien de cases doivent être affichées à l'écran
  // (et surtout sur les bords invisibles).
  protected double hautMax, largMax;

  // Contient la taille des cases qui s'afficheront sur le plateau sur la map créée.
  protected int taillePixel;

  // La liste de Joueur.
  protected Joueur[] joueurs;

  // Le joueur jouant en ce moment.
  protected Joueur joueur;

  // L'indice du joueur dans le tableau de Joueur.
  protected int indiceJoueur;

  // **************************************************
  // *************** Variable de Classe ***************
  // **************************************************

  // Des variables qui permettent d'automatiser la création des formes simples
  // telles que les rectangles et les cercles.
  protected final static String oval = "oval";
  protected final static String rect = "rect";
  protected final static String doval = "doval";
  protected final static String foval = "foval";
  protected final static String drect = "drect";
  protected final static String frect = "frect";

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param plateau Le plateau de jeu
   * @param jeu     Le jeu dans lequel on joue.
   */
  public Map (Plateau plateau) {
    this.plateau = plateau;
    joueurs = plateau.getJoueurs();
    indiceJoueur = 0;
    joueur = joueurs[0];
    tabI = 1;
    tabJ = 1;
    reset();
    Dimension dimensionEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    largeurEcran = dimensionEcran.getWidth();
    hauteurEcran = dimensionEcran.getHeight();
    largMax = (85*largeurEcran/100 + 3)/100;
    hautMax = hauteurEcran/100 + 2;
    setBackground(Color.BLACK);
  }

  // ***************************************
  // *************** Getters ***************
  // ***************************************

  public double getHautMax() { return hautMax; }
  public double getLargMax() { return largMax; }

  public int getTabI() { return tabI; }
  public int getTabJ() { return tabJ; }
  public int getHauteur() { return plateau.getHauteur(); }
  public int getLargeur() { return plateau.getLargeur(); }
  public int getTaillePixel() { return taillePixel; }

  public AbstractTerrain[][] getTerrain() { return plateau.getTerrain(); }

  public AbstractUnite[][] getUnites() { return plateau.getUnites(); }

  public Joueur getJoueur() { return joueur; }
  public Joueur[] getJoueurs() { return joueurs; }

  public LinkedList<AbstractVille> getVilles() { return plateau.getVilles(); }

  public Plateau getPlateau() { return plateau; }

  // ***************************************
  // *************** Setters ***************
  // ***************************************

  // Les variables permettant de stocker la position relative et qui permettent d'afficher le déplacement continu
  // ont une forme différente en fonction de la Map considérée. Ainsi, dans miniMap, posI et posJ sont des doubles
  // tandis que dans PanelMap, ce sont des entiers. Pour éviter un nombre infini de cast de double into int, j'ai
  // préféré utiliser des types différents selon les classes filles. Cela implique des fonctions abstraites pour
  // remettre à zéro les valeurs posI et posJ;

  /**
   * Définie la position dans le tableau de terrain sur l'axe des ordonnées
   * et remet la position relative nécessaire au déplacement continue sur l'axe des ordonnées à zéro.
   * @param tI Le déplacement que l'on va faire dans le tableau.
   */
  public void setTabI (int tI) {
    tabI = tI;
    resetPosI();
  }

  /**
   * Définie la position dans le tableau de terrain sur l'axe des abscisses
   * et remet la position relative nécessaire au déplacement continue sur l'axe des abscisses à zéro.
   * @param tJ Le déplacement que l'on va faire dans le tableau.
   */
  public void setTabJ (int tJ) {
    tabJ = tJ;
    resetPosJ();
  }

  /**
   * Permet de se déplacer dans le tableau de terrain et remet la position relative
   * nécessaire au déplacement continue sur l'axe des ordonnées à zéro.
   * @param tI Le déplacement que l'on va faire dans le tableau.
   */
  public void addTabI (int tI) {
    tabI += tI;
    resetPosI();
  }

  /**
   * Permet de se déplacer dans le tableau de terrain et remet la position relative
   * nécessaire au déplacement continue sur l'axe des abscisses à zéro.
   * @param tJ Le déplacement que l'on va effectuer.
   */
  public void addTabJ (int tJ) {
    tabJ += tJ;
    resetPosJ();
  }

  /**
   * Permet de remettre la position relative à zéro.
   */
  abstract void resetPosI();
  abstract void resetPosJ();
  abstract void reset();


  /**
   * Permet de changer de joueur à la fin d'un tour. Cette fonction remet toutes les variables de tour à zéro
   * (déplacement, unité cliquée, etc.) et fait un tour dans la liste de joueurs.
   * @param i Le nombre d'éléments que l'on saute dans la liste de joueurs.
   */
  public void setJoueur (int i) {
    // On redonne la possibilité à toutes les unités de faire des actions.
    joueur.reset();
    // On redonne la possibilité à toutes les villes de produire.
    plateau.reset();
    // On change l'indice du joueur en cours en ce moment, puis on change le joueur.
    if (indiceJoueur+i < joueurs.length)
      indiceJoueur += i;
    else indiceJoueur = 0;
    joueur = joueurs[indiceJoueur];
  }

  /**
   * Change le joueur et met l'affichage à jour.
   */
  public void newTurn() {
    // On change le joueur.
    this.setJoueur(1);
    // On repeint la carte.
    this.repaint();
  }

}
