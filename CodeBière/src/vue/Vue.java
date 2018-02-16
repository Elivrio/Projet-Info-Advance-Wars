package src.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import src.modele.*;
import src.variable.Variable;

public class Vue extends JFrame {

  // Attributs et constructeurs

  final static long serialVersionUID = 0; // Pour ne pas avoir une erreur jaune à la compilation
  private final int larg;
  private final int haut;

  private Plateau p;
  private int posI, posJ;
  private int tabI, tabJ;
  private int taillePixel;
  private final double hautMax, largMax;

  public Vue (Plateau plat) {
    p = plat;
    taillePixel = 100;
    reset();
    tabI = 1; tabJ = 1;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    largMax = screenSize.getWidth()/100 + 1;
    hautMax = screenSize.getHeight()/100 + 1;
    larg = (int)(screenSize.getWidth());
    haut = (int)(screenSize.getHeight());
    setSize(larg, haut);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public Vue() {
    this(new Plateau(10,10));
  }

  // Getters et setters pourris

  public Plateau getPlateau() { return p; }

  public int getPosI() { return posI; }
  public int getPosJ() { return posJ; }
  public void addPosI (int pI) { posI += pI; }
  public void addPosJ (int pJ) { posJ += pJ; }

  public int getTabI() { return tabI; }
  public int getTabJ() { return tabJ; }

  public double getHautMax() { return hautMax; }
  public double getLargMax() { return largMax; }

  public int getTaillePixel() { return taillePixel; }

  public int getHauteur() { return p.getHauteur(); }
  public int getLargeur() { return p.getLargeur(); }

  public Unite[][] getUnites() { return p.getUnites(); }
  public int[][] getTerrain() { return p.getTerrain(); }


  // Méthodes utiles

  public void addTabI (int tI) {
    tabI += tI;
    posI = 0;
  }
  public void addTabJ (int tJ) {
    tabJ += tJ;
    posJ = 0;
  }

  public void reset() {
    posJ = 0;
    posI = 0;
  }

  // Fonction appelée automatiquement ou avec repaint(), sert à dessiner
  public void paint (Graphics g) {
    for (int i = 0; i < hautMax; i++)
      for (int j = 0; j < largMax; j++)
        createRect(g,
        p.getTerrain()[i+tabI-1][j+tabJ-1],
        j,
        i,
        p.getUnites()[i+tabI-1][j+tabJ-1]);
  }

  // Fonction dessinant le plateau et les unités sur la fenêtre
  public void createRect (Graphics g, int i, int x, int y, Unite unite) {
    BufferedImage img = Variable.tImTer[i];
    // On dessine le terrain
    g.drawImage(img, (x*taillePixel) - posJ - 100, (y*taillePixel) - posI - 70, this);
    g.setColor(Color.BLACK);
    // On l'encadre en noir (purement esthétique)
    g.drawRect((x*taillePixel) - posJ - 100, (y*taillePixel) - posI - 70, taillePixel, taillePixel);
    // On dessine l'unité si elle est présente
    if (unite != null) {
      Image uni = Variable.tImUni[unite.getType()-1];
      g.drawImage(uni, (x*taillePixel) - posJ - 75, (y*taillePixel) - posI - 45, this);
    }
  }

  // Fonction affichant le pop-up avec les caractéristiques de l'unité cliquée
  public void informations (Unite unite) {
    String str = "Unité de type " + unite.getNom();
    str += "\nPossède " + unite.getPV() + " points de vie";
    JOptionPane.showMessageDialog(this, str, "Informations", JOptionPane.PLAIN_MESSAGE);
  }
}
