package src.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;

import src.modele.Joueur;
import src.modele.Plateau;
import src.modele.AbstractUnite;
import src.modele.AbstractTerrain;

public class Map extends JPanel {

  protected Plateau p;
  //protected double posI, posJ;
  protected int haut, larg;
  protected int tabI, tabJ;
  //protected int taillePixel;
  protected Dimension dimensionEcran;
  protected final double largeurEcran, hauteurEcran;
  protected double hautMax, largMax;
  protected Joueur[] joueurs;
  protected Joueur joueur;
  protected int indiceJoueur;

  public Map (Plateau plat) {
    p = plat;
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

  public int getLarg() { return larg; }
  public int getTabI() { return tabI; }
  public int getTabJ() { return tabJ; }
  public int getHauteur() { return p.getHauteur(); }
  public int getLargeur() { return p.getLargeur(); }
  //public int getTaillePixel() { return taillePixel; }

  public Plateau getPlateau() { return p; }
  public Joueur getJoueur() { return joueur; }
  public Joueur[] getJoueurs() { return joueurs; }

  public AbstractUnite[][] getUnites() { return p.getUnites(); }
  public AbstractTerrain[][] getTerrain() { return p.getTerrain(); }

  public void setJoueur (int i) {
    joueur.reset();
    if (indiceJoueur+i < joueurs.length)
      indiceJoueur += i;
    else indiceJoueur = 0;
    joueur = joueurs[indiceJoueur];
  }

}
