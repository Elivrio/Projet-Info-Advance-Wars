package src.vue;

import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import src.vue.Jeu;
import src.vue.Map;
import src.modele.Plateau;
import src.variable.Variable;
import src.modele.AbstractUnite;
import src.modele.AbstractTerrain;

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
  public double getHautMax() { return hautMax; }
  public double getLargMax() { return largMax; }
  public AbstractUnite getCliquee() { return cliquee; }

  // Setters

  public void addTabI (int tI) {
    tabI += tI;
    posI = 0;
  }

  public void addTabJ (int tJ) {
    tabJ += tJ;
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

  public void rmvUnite (AbstractUnite u) {
    p.rmvUnite(u);
  }

  public void setAttaque (boolean b) {
    attaque = b;
  }


  public void reset() {
    posJ = 0;
    posI = 0;
  }

  public void setCliquee (AbstractUnite u) { cliquee = u; }

  public void paint (Graphics g) {
    joueur.vision();

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
    BufferedImage img = chemin(i,y,x);
    // On dessine le terrain
    g.drawImage(img, (x*taillePixel) - posJ - 100, (y*taillePixel) - posI - 100, this);

    // On met à jour le brouillard de guerre
    switch (joueur.getVision()[y+tabI-1][x+tabJ-1]) {
      case 0 :
        g.drawImage(Variable.noir, (x*taillePixel) - posJ - 100, (y*taillePixel) - posI - 100, this);
        break;
      case 1 :
        g.drawImage(Variable.gris, (x*taillePixel) - posJ - 100, (y*taillePixel) - posI - 100, this);
        break;
      default :
        break;
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
      Image uni = Variable.tImUni[unite.getIndice()-1];
      g.drawImage(uni, (x*taillePixel) - posJ - 75, (y*taillePixel) - posI - 75, this);
    }
  }

  public BufferedImage chemin (int i, int x, int y) {
    //BufferedImage img = Variable.tImTer[i];
    AbstractTerrain[][] t = p.getTerrain();
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
