package src.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import src.modele.*;
import src.variable.Variable;

public class PanelMap extends JPanel {

  private final int larg, haut;
  private Plateau p;
  private int posI, posJ;
  private int tabI, tabJ;
  private int taillePixel;
  private Dimension dimensionEcran;
  private final double largeurEcran, hauteurEcran;
  private final double hautMax, largMax;
  private boolean caseCliquee;

  public PanelMap (Plateau plat) {
    dimensionEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    largeurEcran = dimensionEcran.getWidth();
    hauteurEcran = dimensionEcran.getHeight();
    setFocusable(true);
    requestFocusInWindow(true);
    p = plat;
    taillePixel = 100;
    reset();
    tabI = 1;
    tabJ = 1;
    larg = (int)(85*largeurEcran/100);
    haut = (int)hauteurEcran;
    setSize(larg, haut);
    largMax = (85*largeurEcran/100)/100;
    hautMax = hauteurEcran/100 + 1;
  }

  // Getters

  public int getPosI() { return posI; }
  public int getPosJ() { return posJ; }
  public int getTabI() { return tabI; }
  public int getTabJ() { return tabJ; }
  public boolean caseCliquee() { return caseCliquee; }

  public Plateau getPlateau() { return p; }

  public int getLarg() { return larg; }
  public double getHautMax() { return hautMax; }
  public double getLargMax() { return largMax; }
  public int getHauteur() { return p.getHauteur(); }
  public int getLargeur() { return p.getLargeur(); }

  public int getTaillePixel() { return taillePixel; }

  public Unite[][] getUnites() { return p.getUnites(); }
  public Terrain[][] getTerrain() { return p.getTerrain(); }

  // Setters

  public void setCaseCliquee (boolean b) {
    caseCliquee = b;
  }

  public void addPosI (int pI) {
    posI += pI;
    repaint();
  }

  public void addPosJ (int pJ) {
    posJ += pJ;
    repaint();
  }

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

  public void paint (Graphics g) {
    for (int i = 0; i < hautMax; i++)
      for (int j = 0; j < largMax; j++)
        createRect(g,
        p.getTerrain()[i+tabI-1][j+tabJ-1].getType(),
        j,
        i,
        p.getUnites()[i+tabI-1][j+tabJ-1]);
  }

  public void deplacementsPossibles (int indice, Graphics g, int x, int y, Unite unite) {
    g.setColor(Color.GREEN);
    if (indice < unite.getDistance())
      for (int i = -1; i <= 1; i++)
        for (int j = -1; j <= 1; j++)
          if (i != j && i != -j
          && (y+i+tabI-1) >= 0 && (y+i+tabI-1) < p.getTerrain().length
          && (x+j+tabJ-1) >= 0 && (x+j+tabJ-1) < p.getTerrain()[0].length) {
            g.drawRect((x+j)*taillePixel - posJ - 100, (y+i)*taillePixel - posI - 100, taillePixel, taillePixel);
            deplacementsPossibles(indice+1, g, j+x, y+i, unite);
          }
  }

  // Fonction dessinant le plateau et les unités sur la fenêtre
  public void createRect (Graphics g, int i, int x, int y, Unite unite) {
    BufferedImage img = Variable.tImTer[i];
    // On dessine le terrain
    g.drawImage(img, (x*taillePixel) - posJ - 100, (y*taillePixel) - posI - 100, this);
    g.setColor(Color.BLACK);
    // On l'encadre en noir (purement esthétique)
    g.drawRect((x*taillePixel) - posJ - 100, (y*taillePixel) - posI - 100, taillePixel, taillePixel);
    // On dessine l'unité si elle est présente
    if (unite != null) {
      Image uni = Variable.tImUni[unite.getType()-1];
      g.drawImage(uni, (x*taillePixel) - posJ - 75, (y*taillePixel) - posI - 75, this);
      if (caseCliquee)
        deplacementsPossibles(0, g, x, y, unite);
      unite = null;
    }
  }

}
