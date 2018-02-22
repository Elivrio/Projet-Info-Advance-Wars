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
  private Unite cliquee;

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
    largMax = (85*largeurEcran/100)/100 + 2;
    hautMax = hauteurEcran/100 + 2;
  }

  // Getters

  public int getPosI() { return posI; }
  public int getPosJ() { return posJ; }
  public int getTabI() { return tabI; }
  public int getTabJ() { return tabJ; }

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

  public void setCliquee(Unite u) { cliquee = u; }

  public void paint(Graphics g) {
    // On utilise b si on veut que l'unité cliquée ne soit pas gardée en mémoire à la sortie de l'écran.
    //boolean b = true;
    boolean c = false;
    int x = 0, y = 0;
    for (int i = 0; i < hautMax; i++)
      for (int j = 0; j < largMax; j++) {
        if (i + tabI - 1 >= p.getHauteur() || j + tabJ - 1 >= p.getLargeur())
          continue;
        Unite unite = p.getUnites()[i+tabI-1][j+tabJ-1];
        int t = p.getTerrain()[i+tabI-1][j+tabJ-1].getType();
        createRect(g, t, j, i, unite);
        if (cliquee != null && unite != null && unite == cliquee) {
          c = true;
          x = j; y = i;
          //b = false;
        }
      }
    if (c)
      deplacementsPossibles(0, g, x, y, cliquee);
    /*if (b)
      cliquee = null; */
  }

  public void deplacementsPossibles (int indice, Graphics g, int x, int y, Unite unite) {
    g.setColor(Color.GREEN);
    if (indice < unite.getDistance())
      for (int i = -1; i <= 1; i++)
        for (int j = -1; j <= 1; j++)
          if (i != j && i != -j
          && (y+i+tabI-2) >= 0 && (y+i+tabI) < p.getTerrain().length
          && (x+j+tabJ-2) >= 0 && (x+j+tabJ) < p.getTerrain()[0].length) {
            g.drawRect((x+j)*taillePixel - posJ - 100, (y+i)*taillePixel - posI - 100, taillePixel, taillePixel);
            deplacementsPossibles(indice+1, g, j+x, y+i, unite);
          }
  }

  // Fonction dessinant le plateau et les unités sur la fenêtre
  public void createRect (Graphics g, int i, int x, int y, Unite unite) {
    BufferedImage img = chemin(i,y,x);
    // On dessine le terrain
    g.drawImage(img, (x*taillePixel) - posJ - 100, (y*taillePixel) - posI - 100, this);
    g.drawImage(Variable.gris, (x*taillePixel) - posJ - 100, (y*taillePixel) - posI - 100, this);
    g.setColor(Color.BLACK);
    // On l'encadre en noir (purement esthétique)
    g.drawRect((x*taillePixel) - posJ - 100, (y*taillePixel) - posI - 100, taillePixel, taillePixel);
    // On dessine l'unité si elle est présente
    if (unite != null) {
      Image uni = Variable.tImUni[unite.getType()-1];
      g.drawImage(uni, (x*taillePixel) - posJ - 75, (y*taillePixel) - posI - 75, this);
    }
  }

  public BufferedImage chemin(int i, int x, int y) {
    //BufferedImage img = Variable.tImTer[i];
    Terrain[][] t = p.getTerrain();
    int a = 1;
    int b = 1;
    int c = 1;
    int d = 1;
    // pour rester sur le terrain
    if ( (x>=1 && y>=1) && (x + tabI < p.getHauteur()-1 &&  y + tabJ <p.getLargeur()-1) ){
      a = t[x+tabI-1][y+tabJ-2].getType();
      b = t[x+tabI-2][y+tabJ-1].getType();
      c = t[x+tabI-1][y+tabJ].getType();
      d = t[x+tabI][y+tabJ-1].getType();
    }
    if (i==1){
      int[] tab = {a, b, c, d};
      int j = chercherTerrain(tab);
      String chemin = indice(j,a) +""+ indice(j,b) +""+ indice(j,c) +""+ indice(j,d);
      int place = stringBinaryToInt(chemin);
      if (j==0)
        return Variable.tImPlaineForet[place];
      if (j==2)
        return Variable.tImPlaineEau[place];
    }

    if (i==2){
      String chemin = (a-1)+""+(b-1)+""+(c-1)+""+(d-1);
      //System.out.println(chemin);
      int place = stringBinaryToInt(chemin);
      if (a+b+c+d == 8){
        if (t[x+tabI][y+tabJ-2].getType()==1)
          return Variable.tImEauPlageCoin[1];
        if (t[x+tabI-2][y+tabJ-2].getType()==1)
          return Variable.tImEauPlageCoin[2];
        if (t[x+tabI-2][y+tabJ].getType()==1)
          return Variable.tImEauPlageCoin[3];
        if (t[x+tabI][y+tabJ].getType()==1)
          return Variable.tImEauPlageCoin[4];
      }
      return Variable.tImEauPlage[place];

    }
    BufferedImage img = Variable.tImTer[i];
    return img;
  }

  public int stringBinaryToInt(String s) {
    int res = 0;
    int bin = 1;
    for (int i =0; i<s.length(); i++){
      int b = s.codePointAt(s.length()-i-1)-48;
      res += b*bin;
      bin =bin*2;
    }
    return res;
  }

  public int chercherTerrain(int[] tab){
    for (int i=0; i<tab.length; i++)
      if (tab[i] != 1)
        return tab[i];
    return 1;
  }

  public int indice(int i, int a){
    return ((i==a)? 1 :0);
  }

}
