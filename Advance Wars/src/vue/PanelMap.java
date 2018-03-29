package src.vue;

import java.awt.Color;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.image.ColorModel;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import src.vue.Jeu;
import src.vue.Map;
import src.modele.Plateau;
import src.variable.Variable;
import src.modele.AbstractUnite;
import src.modele.general.General;
import src.modele.AbstractTerrain;
import src.modele.terrain.AbstractVille;

public class PanelMap extends Map {

  private AbstractUnite cliquee;
  private boolean attaque;
  private int taillePixel;
  private int posI, posJ;

  public PanelMap (Plateau plat, Jeu j) {
    super(plat, j);
    setFocusable(true);
    requestFocusInWindow(true);
    taillePixel = 100;
    reset();
    larg = (int)(85*largeurEcran/100);
    haut = (int)hauteurEcran;
    setSize(larg, haut);
  }

  // Getters

  public int getPosI() { return posI; }
  public int getPosJ() { return posJ; }
  public int getTaillePixel() { return taillePixel; }
  public boolean getAttaque() { return attaque; }
  public AbstractUnite getCliquee() { return cliquee; }

  // Setters

  public void rmvUnite (AbstractUnite u) { p.rmvUnite(u); }
  public void setAttaque (boolean b) { attaque = b; }
  public void setCliquee (AbstractUnite u) { cliquee = u; }


  public void addTabI (int tI) {
    tabI += tI;
    posI = 0;
  }

  public void addTabJ (int tJ) {
    tabJ += tJ;
    posJ = 0;
  }

  public void setTabI (int tI) {
    tabI = tI;
    posI = 0;
  }

  public void setTabJ (int tJ) {
    tabJ = tJ;
    posJ = 0;
  }

  public void addPosI (int pI) {
    posI += pI;
    repaint();
  }

  public void addPosJ (int pJ) {
    posJ += pJ;
    repaint();
  }

  public void reset() {
    posJ = 0;
    posI = 0;
  }


  @Override
  public void paint (Graphics g) {
    joueur.vision(p.getTerrain());

    int x = 0, y = 0;
    for (int i = 0; i < hautMax; i++)
      for (int j = 0; j < largMax; j++) {
        if (i + tabI - 1 >= p.getHauteur() || j + tabJ - 1 >= p.getLargeur())
          continue;
        AbstractUnite unite = p.getUnites()[i+tabI-1][j+tabJ-1];
        int t = p.getTerrain()[i+tabI-1][j+tabJ-1].getType();
        createRect(g, t, j, i, unite);
      }
  }

  // Fonction dessinant le plateau et les unités sur la fenêtre
  public void createRect (Graphics g, int i, int x, int y, AbstractUnite unite) {
    // On dessine le terrain
    chemin(i, y, x, g);

    // On met à jour le brouillard de guerre
    switch (joueur.getVision()[y+tabI-1][x+tabJ-1]) {
      case 0 :
        g.drawImage(Variable.noir, (x*taillePixel) - posJ - 100, (y*taillePixel) - posI - 100, this);
        break;
      case 1 :
        g.drawImage(Variable.gris, (x*taillePixel) - posJ - 100, (y*taillePixel) - posI - 100, this);
        break;
      default : break;
    }

    if ((cliquee != null) && joueur.possede(cliquee)
        && (y+tabI-2 >= 0)
        && (y+tabI < p.getTerrain().length)
        && (x+tabJ-2 >= 0)
        && (x+tabJ < p.getTerrain()[0].length)) {
          // Affichage des déplacements possibles
          if ((cliquee.getAttaque() >= 1 || !attaque) && Math.abs((x+tabJ-1) - cliquee.getX()) + Math.abs((y+tabI-1) - cliquee.getY()) <= (cliquee.getDistance() - cliquee.getDeplace()))
            g.drawImage(Variable.vert, (x*taillePixel) - posJ - 100, (y*taillePixel) - posI - 100, this);
          // Affichage de la portée
          if (cliquee.getAttaque() < 1 && attaque && (Math.abs((x+tabJ-1) - cliquee.getX()) + Math.abs((y+tabI-1) - cliquee.getY()) <= cliquee.getPortee()))
            g.drawImage(Variable.rouge, (x*taillePixel) - posJ - 100, (y*taillePixel) - posI - 100, this);
    }

    g.setColor(Color.BLACK);
    // On l'encadre en noir (purement esthétique)
    g.drawRect((x*taillePixel) - posJ - 100, (y*taillePixel) - posI - 100, taillePixel, taillePixel);
    // On dessine l'unité si elle est présente
    if (unite != null && joueur.getVision()[y+tabI-1][x+tabJ-1] == 2) {
      BufferedImage uni = Variable.tImUni[unite.getIndice()-1];
      if (unite instanceof General)
        g.drawImage(uni, (x*taillePixel) - posJ - 80, (y*taillePixel) - posI - 80, this);
      else
        g.drawImage(uni, (x*taillePixel) - posJ - 70, (y*taillePixel) - posI - 70, this);
      Graphics2D g2 = (Graphics2D) g;
      g2.setStroke(new BasicStroke(2));
      g2.setColor(unite.getJoueur().getColor());
      g2.drawRect((x*taillePixel) - posJ - 85, (y*taillePixel) - posI - 85, 70, 70);
      g2.setStroke(new BasicStroke(1));
    }
  }

  public void chemin (int i, int x, int y, Graphics g) {
    //BufferedImage img = Variable.tImTer[i];
    AbstractTerrain[][] t = p.getTerrain();
    BufferedImage img = null;
    String chemin;
    int a = 1;
    int b = 1;
    int c = 1;
    int d = 1;
    int place;
    int j;
    // pour rester sur le terrain
    if (x + tabI > 1
      && y + tabJ > 1
      && x + tabI < p.getHauteur() - 1
      &&  y + tabJ < p.getLargeur() - 1 ) {
      a = t[x + tabI - 1][y + tabJ - 2].getType();
      b = t[x + tabI - 2][y + tabJ - 1].getType();
      c = t[x + tabI - 1][y + tabJ].getType();
      d = t[x + tabI][y + tabJ - 1].getType();
    }
    int[] tab = {a, b, c, d};
    switch (i) {
      case 1 :
        j = chercherTerrain(tab);
        chemin = indice(j, a) + "" + indice(j, b) + "" + indice(j, c) + "" + indice(j, d);
        place = stringBinaryToInt(chemin);
        if (j == 0)
          img =Variable.tImPlaineForet[place];
        if (j == 2)
          img = Variable.tImPlaineEau[place];
        break;
      case 2 :
        chemin = (a - 1) + "" + (b - 1) + "" + (c - 1) + "" + ( d - 1);
        place = stringBinaryToInt(chemin);
        if (a + b + c + d == 8) {
          if (t[x + tabI][y + tabJ - 2].getType() == 1)
            img = Variable.tImEauPlageCoin[1];
          else if (t[x + tabI - 2][y + tabJ - 2].getType() == 1)
            img = Variable.tImEauPlageCoin[2];
          else if (t[x + tabI - 2][y + tabJ].getType() == 1)
            img = Variable.tImEauPlageCoin[3];
          else if (t[x + tabI][y + tabJ].getType() == 1)
            img = Variable.tImEauPlageCoin[4];
          break;
        }
        img = Variable.tImEauPlage[place];
    }
    if (img == null)
      img = Variable.tImTer[i];
    g.drawImage(img, (y * taillePixel) - posJ - 100, (x * taillePixel) - posI - 100, this);
    if (x + tabI > 1
        && y + tabJ > 1
        && x + tabI < p.getHauteur() - 1
        &&  y + tabJ < p.getLargeur() - 1
        && t[x + tabI - 1][y + tabJ - 1] instanceof AbstractVille) {
      AbstractVille ville = ((AbstractVille)t[x + tabI - 1][y + tabJ - 1]);
      if (ville.getJoueur() != null) {
        Color color = ville.getJoueur().getColor();
        g.setColor(color);
        g.fillRect((y * taillePixel) - posJ - 15, (x * taillePixel) - posI - 15, 10, 10);
      }
    }

  }

  public int stringBinaryToInt(String s) {
    int res = 0;
    int bin = 1;
    for (int i = 0; i < s.length(); i++) {
      int b = s.codePointAt(s.length() - i - 1) - 48;
      res += b * bin;
      bin = bin * 2;
    }
    return res;
  }

  public int chercherTerrain(int[] tab) {
    for (int i = 0; i < tab.length; i++)
      if (tab[i] != 1)
        return tab[i];
    return 1;
  }

  public int indice(int i, int a) {
    return ((i==a)? 1 :0);
  }
}
