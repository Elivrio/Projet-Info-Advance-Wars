package src.vue;

import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.ColorModel;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import src.vue.Jeu;
import src.vue.Map;
import src.modele.Plateau;
import src.variable.MyColor;
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

  public final static String oval = "oval";
  public final static String rect = "rect";
  public final static String doval = "doval";
  public final static String foval = "foval";
  public final static String drect = "drect";
  public final static String frect = "frect";

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
        AbstractUnite unite = p.getUnites()[i + tabI - 1][j + tabJ - 1];
        int t = p.getTerrain()[i + tabI - 1][j + tabJ - 1].getType();
        createRect(g, t, j, i, unite);
      }
  }

  // Fonction dessinant le plateau et les unités sur la fenêtre
  public void createRect (Graphics g, int i, int x, int y, AbstractUnite unite) {
    // On dessine le terrain correspondant à la case donnée.
    chemin(i, y, x, g);

    // On met à jour le brouillard de guerre sur cette case.
    switch (joueur.getVision()[y + tabI - 1][x + tabJ - 1]) {
      case 0 :
        g.drawImage(Variable.noir, (x * taillePixel) - posJ - 100, (y * taillePixel) - posI - 100, this);
        break;
      case 1 :
        g.drawImage(Variable.gris, (x * taillePixel) - posJ - 100, (y * taillePixel) - posI - 100, this);
        break;
      default : break;
    }

    if ((cliquee != null) && joueur.possede(cliquee)
        && (y + tabI-2 >= 0)
        && (y + tabI < p.getTerrain().length)
        && (x + tabJ-2 >= 0)
        && (x + tabJ < p.getTerrain()[0].length)) {
          // Affichage des déplacements possibles.
          if ((cliquee.getAttaque() >= 1 || !attaque) && Math.abs((x + tabJ - 1) - cliquee.getX()) + Math.abs((y + tabI - 1) - cliquee.getY()) <= (cliquee.getDistance() - cliquee.getDeplace()))
            g.drawImage(Variable.vert, (x * taillePixel) - posJ - 100, (y * taillePixel) - posI - 100, this);
          // Affichage de la portée.
          if (cliquee.getAttaque() < 1 && attaque && (Math.abs((x + tabJ - 1) - cliquee.getX()) + Math.abs((y + tabI - 1) - cliquee.getY()) <= cliquee.getPortee()))
            g.drawImage(Variable.rouge, (x * taillePixel) - posJ - 100, (y * taillePixel) - posI - 100, this);
    }

    // On encadre le terrain en noir (purement esthétique).
    g.setColor(Color.BLACK);
    g.drawRect((x * taillePixel) - posJ - 100, (y * taillePixel) - posI - 100, taillePixel, taillePixel);


    // On dessine l'unité si elle est présente.
    if (unite != null && joueur.getVision()[y + tabI - 1][x + tabJ - 1] == 2) {
      // On commence par récupérer l'unité concernée.
      BufferedImage uni = Variable.tImUni[unite.getIndice()-1];
      // Cette variable sert pour faire varier la couleur dans tout le reste de la fonction.
      Color color = Color.GREEN.darker();

      // On dessine sa barre de vie.
      makeForm(g, rect, x, y, posJ + 90, posI + 90, 80, 80 * unite.getPV() / unite.getPVMax(), 5, color);

      // Si le joueur actuel possède l'unité, il faut afficher des informations en plus.
      if (joueur.possede(unite)) {

        // On affiche un rond pour préciser si l'unité sélectionnée peut attaquer ou non.
        color = Color.GREEN;
        if (unite.getAttaque() >= 1)
          color = Color.RED;
        makeForm(g, oval, x, y, posJ + 90, posI + 83, 5, 5, color);

        // On affiche plusieurs ronds pour montrer la distance que peut encore parcourir l'unité.
        for (int dis = 0; dis < unite.getDistance(); dis++) {
          color = Color.GREEN;
          if (dis < unite.getDeplace())
            color = Color.RED;
          makeForm(g, oval, x, y, posJ + 10, posI + 10 + (dis*6), 5, 5, color);
        }
      }

      // On récupére la couleur du joueur qui possède l'unité.
      // L'utilisation de MyColor a pour but d'ajouter de la transparence à la couleur.
      color = new MyColor(unite.getJoueur().getColor().getRGB(), 150, "");

      // Il y a distinction entre un général et une unité normale, les généraux sont plus grands.
      if (unite instanceof General) {
        // Le socle d'un général.
        makeForm(g, oval, x, y, posJ + 80, posI + 35, 60, 20, color);
        // Un général.
        g.drawImage(uni, (x * taillePixel) - posJ - 80, (y * taillePixel) - posI - 80, this);
      }
      else {
        // Le socle d'une unité.
        makeForm(g, oval, x, y, posJ + 80, posI + 45, 60, 20, color);
        // Une unité.
        g.drawImage(uni, (x * taillePixel) - posJ - 70, (y * taillePixel) - posI - 70, this);
      }
    }
  }

  public void makeForm(Graphics g, String form, int x, int y, int modX, int modY, int width, int height, Color color) {
    switch (form) {
      case rect :
        drawForm(g, drect, x, y, modX, modY, width, height, Color.BLACK);
        drawForm(g, frect, x, y, modX, modY, width, height, color);
        break;
      case oval :
        drawForm(g, doval, x, y, modX, modY, width, height, Color.BLACK);
        drawForm(g, foval, x, y, modX, modY, width, height, color);
        break;
    }
  }

  public void makeForm(Graphics g, String form, int x, int y, int modX, int modY, int width, int secondWidth, int height, Color color) {
    switch (form) {
      case rect :
        drawForm(g, drect, x, y, modX, modY, width, height, Color.BLACK);
        drawForm(g, frect, x, y, modX, modY, secondWidth, height, color);
        break;
      case oval :
        drawForm(g, doval, x, y, modX, modY, width, height, Color.BLACK);
        drawForm(g, foval, x, y, modX, modY, secondWidth, height, color);
        break;
    }
  }

  public void drawForm(Graphics g, String form, int x, int y, int modX, int modY, int width, int height, Color color) {
    g.setColor(color);
    switch (form) {
      case doval : g.drawOval((x * taillePixel) - modX, (y * taillePixel) - modY, width, height); break;
      case foval : g.fillOval((x * taillePixel) - modX, (y * taillePixel) - modY, width, height); break;
      case drect : g.drawRect((x * taillePixel) - modX, (y * taillePixel) - modY, width, height); break;
      case frect : g.fillRect((x * taillePixel) - modX, (y * taillePixel) - modY, width, height); break;
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
    if (x + tabI >= 0
        && y + tabJ >= 0
        && x + tabI <= p.getHauteur() - 1
        &&  y + tabJ <= p.getLargeur() - 1) {

      a = check((y + tabJ > 1), x, y, -1, -2, t);
      b = check((x + tabI > 1), x, y, -2, -1, t);
      c = check((y + tabJ < p.getLargeur() - 1), x, y, -1, 0, t);
      d = check((x + tabI < p.getHauteur() - 1), x, y, 0, -1, t);

    }
    switch (i) {
      case 1 :
        int[] tab = {a, b, c, d};
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
        g.drawRect((y * taillePixel) - posJ - 15, (x * taillePixel) - posI - 15, 10, 10);
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
    for (int n : tab)
      if (n != 1 && n != 4)
        return n;
    return 1;
  }

  public int indice(int i, int a) {
    return ((i == a)? 1 :0);
  }

  public int check(boolean a, int x, int y, int modI, int modJ, AbstractTerrain[][] t) {
    if (a)
      return t[x + tabI + modI][y + tabJ + modJ].getType();
    else
      return 1;
  }
}
