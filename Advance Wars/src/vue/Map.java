package src.vue;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.util.LinkedList;

import src.vue.Jeu;
import src.modele.Joueur;
import src.modele.Plateau;
import src.modele.AbstractUnite;
import src.modele.AbstractTerrain;
import src.modele.terrain.AbstractVille;

public class Map extends JPanel {

  protected Plateau p;
  protected int haut, larg;
  protected int tabI, tabJ;
  protected Dimension dimensionEcran;
  protected final double largeurEcran, hauteurEcran;
  protected double hautMax, largMax;
  protected Joueur[] joueurs;
  protected Joueur joueur;
  protected int indiceJoueur;
  protected Jeu jeu;

  public Map (Plateau plat, Jeu j) {
    p = plat;
    jeu = j;
    joueurs = plat.getJoueurs();
    indiceJoueur = 0;
    joueur = joueurs[indiceJoueur];
    tabI = 1;
    tabJ = 1;
    dimensionEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    largeurEcran = dimensionEcran.getWidth();
    hauteurEcran = dimensionEcran.getHeight();
    largMax = (85*largeurEcran/100 + 3)/100;
    hautMax = hauteurEcran/100 + 2;
    setBackground(Color.BLACK);
  }

  public Jeu getJeu() { return jeu; }
  public int getLarg() { return larg; }
  public int getTabI() { return tabI; }
  public int getTabJ() { return tabJ; }
  public int getHauteur() { return p.getHauteur(); }
  public int getLargeur() { return p.getLargeur(); }
  //public int getTaillePixel() { return taillePixel; }

  public Plateau getPlateau() { return p; }
  public Joueur getJoueur() { return joueur; }
  public Joueur[] getJoueurs() { return joueurs; }
  public LinkedList<AbstractVille> getVilles() { return p.getVilles(); }

  public AbstractUnite[][] getUnites() { return p.getUnites(); }
  public AbstractTerrain[][] getTerrain() { return p.getTerrain(); }

  public void setJoueur (int i) {
    joueur.reset();
    p.reset();
    if (indiceJoueur+i < joueurs.length)
      indiceJoueur += i;
    else indiceJoueur = 0;
    joueur = joueurs[indiceJoueur];
  }

}
