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

@SuppressWarnings("serial")
public class PanelMap extends Map {

  // *************** Variable d'instance ***************

  // Contient l'unité cliquée en ce moment.
  private AbstractUnite cliquee;

  // Précise si l'on doit afficher le déplacement de l'unité cliquée
  // ou la distance d'attaque de l'unité.
  private boolean attaque;

  // Permet de stocker en mémoire la position sur l'écran afin de faire du déplacement en continue.
  // Ces valeurs sont comprises entre -taillePixel et taillePixel
  protected int posI, posJ;

  // Pour savoir si on est à l'image 1 ou 2 de l'animation
  protected boolean animation;

  // *************** Constructeur ***************

  public PanelMap (Plateau plat, Jeu j) {
    super(plat, j);
    setFocusable(true);
    requestFocusInWindow(true);
    taillePixel = 100;
    larg = (int)(85*largeurEcran/100);
    haut = (int)hauteurEcran;
    setSize(larg, haut);
    animation = true;
  }

  // *************** Getters ***************

  public int getPosI() { return posI; }
  public int getPosJ() { return posJ; }
  public int getTaillePixel() { return taillePixel; }
  public boolean getAttaque() { return attaque; }
  public AbstractUnite getCliquee() { return cliquee; }
  public boolean getAnimation() { return animation; }

  // *************** Setters ***************

  public void setAttaque (boolean b) { attaque = b; }
  public void setCliquee (AbstractUnite u) { cliquee = u; }

  // Permet de changer le boolean de l'animation.
  public void setAnimation (boolean b) { animation = b; }

  // Permet de déplacer la position relative le long de l'axe des ordonnées et repaint la carte.
  public void addPosI (int pI) {
    posI += pI;
    repaint();
  }

  // Permet de déplacer la position relative le long de l'axe des abscisses et repaint la carte.
  public void addPosJ (int pJ) {
    posJ += pJ;
    repaint();
  }

  // Permet de remettre la position relative à zéro.
  public void resetPosI() { posI = 0; }
  // Permet de remettre la position relative à zéro.
  public void resetPosJ() { posJ = 0; }
  // Permet de remettre la position relative à zéro.
  public void reset() { posJ = 0; posI = 0; }

  // retire une unité du plateau d'unité.
  public void rmvUnite (AbstractUnite u) { p.rmvUnite(u); }

  // *************** Fonctions de classe ***************

  @Override
  // Permet de mettre à jour le contenu du terrain.
  public void paint (Graphics g) {
    // On commence par mettre à jour la vision du joueur.
    joueur.vision(p.getTerrain());
    // On dessine sur tout le terrain.
    for (int i = 0; i < hautMax; i++)
      for (int j = 0; j < largMax; j++) {
        // Si on sort des limites du terrain, on saute ce tour de boucle.
        if (i + tabI - 1 >= p.getHauteur() || j + tabJ - 1 >= p.getLargeur())
          continue;
        // L'unité qui se trouve sur la case.
        AbstractUnite unite = p.getUnites()[i + tabI - 1][j + tabJ - 1];
        // Le type du terrain.
        int t = p.getTerrain()[i + tabI - 1][j + tabJ - 1].getType();
        // La fonction qui permet d'afficher la case et tous ses composents.
        dessineCase(g, t, j, i, unite);
      }
  }

  // Fonction dessinant le plateau et les unités sur la fenêtre
  public void dessineCase (Graphics g, int i, int x, int y, AbstractUnite unite) {
    // On dessine le terrain correspondant à la case donnée.
    chemin(i, y, x, g);

    // On met à jour le brouillard de guerre sur cette case.
    switch (joueur.getVision()[y + tabI - 1][x + tabJ - 1]) {
      // Si la case n'a jamais été visitée.
      case 0 :
        g.drawImage(Variable.noir, (x * taillePixel) - posJ - 100, (y * taillePixel) - posI - 100, this);
        break;
      // Si la case a été visité mais se trouve dans le brouillard de guerre.
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
      BufferedImage uni;
      // choix de l'image de l'unité suivant le moment de l'animation
      if (animation) {
        uni = Variable.tImUni1[unite.getIndice()-1];
      }
      else {
        uni = Variable.tImUni2[unite.getIndice()-1];
      }
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
        makeForm(g, oval, x, y, posJ + 75, posI + 45, 50, 20, color);
        // Une unité.
        g.drawImage(uni, (x * taillePixel) - posJ - 70, (y * taillePixel) - posI - 70, this);
      }
    }
  }

  // Fonction utilitaire pour créer des rectangles ou des ovales de couleur "color" entourés de bordure noirs.
  // La String form permet de définir si la forme choisie est un rectangle ou un oval.
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

  // Permet de dessiner une forme de base sur le Graphics donné.
  public void drawForm(Graphics g, String form, int x, int y, int modX, int modY, int width, int height, Color color) {
    g.setColor(color);
    switch (form) {
      case doval : g.drawOval((x * taillePixel) - modX, (y * taillePixel) - modY, width, height); break;
      case foval : g.fillOval((x * taillePixel) - modX, (y * taillePixel) - modY, width, height); break;
      case drect : g.drawRect((x * taillePixel) - modX, (y * taillePixel) - modY, width, height); break;
      case frect : g.fillRect((x * taillePixel) - modX, (y * taillePixel) - modY, width, height); break;
    }
  }

  // Utile pour la barre de point, la première forme noir représente les contours alors que la deuxième
  // Peut varier sur la largeur.
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

  // Permet d'obtenir l'image adaptée en fonction du terrain et des cases adjcentes.
  public void chemin (int i, int x, int y, Graphics g) {
    // les différentes variables de la fonction.
    // Le terrain est nécessaire afin de calculer les terrains adjacents à la case observée.
    AbstractTerrain[][] t = p.getTerrain();
    // Contiendra l'image renvoyée par la fonction.
    BufferedImage img = Variable.tImTer[i];
    // Le chemin spécifique menant à l'image dans l'arborescence de fichier.
    String chemin;
    // Les 4 entiers suivants représentent une chaîne binaire.
    // Celle-ci permet de déterminer le nombre de côté en contact avec un terrain d'un autre type.
    int a = 1;
    int b = 1;
    int c = 1;
    int d = 1;
    int place;
    int j;
    // Pour rester sur le terrain
    if (x + tabI >= 0
        && y + tabJ >= 0
        && x + tabI <= p.getHauteur() - 1
        &&  y + tabJ <= p.getLargeur() - 1) {

      a = check((y + tabJ > 1), x, y, -1, -2, t);
      b = check((x + tabI > 1), x, y, -2, -1, t);
      c = check((y + tabJ < p.getLargeur() - 1), x, y, -1, 0, t);
      d = check((x + tabI < p.getHauteur() - 1), x, y, 0, -1, t);

    }
    // En fonction du type de terrain sur lequel on se trouve, on ne charge pas les mêmes éléments.
    switch (i) {
      // Liaisons plaines-terrainDeTypeJ.
      case 1 :
        int[] tab = {a, b, c, d};
        // On calcule le type du terrain adjacent.
        j = chercherTerrain(tab);
        // On calcule la chaîne binaire qui sera en préfixe de l'image.
        chemin = indice(j, a) + "" + indice(j, b) + "" + indice(j, c) + "" + indice(j, d);
        // A partir de la chaîne binaire, on calcule la position cible dans le tableau d'image.
        place = stringBinaryToInt(chemin);
        // En fonction du terrain, on a chercher l'image adéquate.
        switch (j) {
          case 0 : img = Variable.tImPlaineForet[place]; break;
          case 2 : img = Variable.tImPlaineEau[place]; break;
        }
        break;
      // Liaisons eau-plage, la plage étant également de l'eau mais qui doit être gérée séparément.
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
    // On dessine l'imagine.
    g.drawImage(img, (y * taillePixel) - posJ - 100, (x * taillePixel) - posI - 100, this);
    // Dans le cas d'une ville, il faut vérifier que celle-ci appartient à un joueur et afficher sa couleur si nécessaire.
    if (x + tabI > 1
        && y + tabJ > 1
        && x + tabI < p.getHauteur() - 1
        &&  y + tabJ < p.getLargeur() - 1
        && t[x + tabI - 1][y + tabJ - 1] instanceof AbstractVille) {
      // On est certain que le terrain considéré est de type Ville.
      AbstractVille ville = ((AbstractVille)t[x + tabI - 1][y + tabJ - 1]);
      // Si cette ville appartient à un joueur, on dessine un carré de la couleur du joueur en bas à droite.
      if (ville.getJoueur() != null) {
        Color color = ville.getJoueur().getColor();
        makeForm(g, rect, y, x, posJ + 95, posI + 15, 10, 10, color);
      }
    }
  }

  // transforme une chaîne binaire en sa valeur entière.
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

  // permet de vérifier le type du terrain adjacent à la plaine.
  public int chercherTerrain(int[] tab) {
    for (int n : tab)
      if (n != 1 && n != 4)
        return n;
    return 1;
  }

  // Si i == a, renvoie 1. sinon, renvoie 0.
  public int indice(int i, int a) {
    return ((i == a)? 1 :0);
  }

  // Fonction pour réduire de code.
  // Permet de récupérer le type d'un terrain adjacent à la case.
  public int check(boolean a, int x, int y, int modI, int modJ, AbstractTerrain[][] t) {
    if (a)
      return t[x + tabI + modI][y + tabJ + modJ].getType();
    return 1;
  }

}
