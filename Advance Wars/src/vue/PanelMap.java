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

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  // Contient l'unité cliquée en ce moment.
  private AbstractUnite cliquee;

  // Précise si l'on doit afficher le déplacement de l'unité cliquée
  // ou la distance d'attaque de l'unité.
  private boolean attaque;

  // Permet de stocker en mémoire la position sur l'écran afin de faire du déplacement en continu.
  // Ces valeurs sont comprises entre -taillePixel et taillePixel
  protected int posI, posJ;

  // Pour savoir si on est à l'image 1 ou 2 de l'animation
  protected boolean animation;

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param plateau   Le Plateau de jeu qui va être dessiné durant la partie.
   * @param jeu       Le Jeu.
   */
  public PanelMap (Plateau plateau) {
    super(plateau);
    setFocusable(true);
    requestFocusInWindow(true);
    taillePixel = 100;
    largeur = (int)(85*largeurEcran/100);
    hauteur = (int)hauteurEcran;
    setSize(largeur, hauteur);
    animation = true;
  }

  // ***************************************
  // *************** Getters ***************
  // ***************************************

  public boolean getAttaque() { return attaque; }
  public boolean getAnimation() { return animation; }

  public int getPosI() { return posI; }
  public int getPosJ() { return posJ; }
  public int getTaillePixel() { return taillePixel; }

  public AbstractUnite getCliquee() { return cliquee; }

  // *************** Setters ***************


  public void setAttaque (boolean b) { attaque = b; }
  public void setCliquee (AbstractUnite u) { cliquee = u; }
  public void setAnimation (boolean b) { animation = b; }

  /**
   * Permet de déplacer la position relative le long de l'axe des ordonnées et repaint la carte.
   * @param pI La valeur que l'on ajoute à la position relative.
   */
  public void addPosI (int pI) {
    posI += pI;
    repaint();
  }

  /**
   * Permet de déplacer la position relative le long de l'axe des abscisses et repaint la carte.
   * @param pJ La valeur que l'on ajoute à la position relative.
   */
  public void addPosJ (int pJ) {
    posJ += pJ;
    repaint();
  }

  /**
   * Permet de remettre la position relative à zéro.
   */
  public void resetPosI() { posI = 0; }
  public void resetPosJ() { posJ = 0; }
  public void reset() { posJ = 0; posI = 0; }

  /**
   * Retire une unité du plateau d'unité.
   * @param u L'unité que l'on souhaite retirer du plateau.
   */
  public void rmvUnite (AbstractUnite u) { plateau.rmvUnite(u); }


  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  @Override
  /**
   * Permet de mettre à jour le contenu du terrain et de le dessiner dans le JPanel.
   * @param g Le contenu Graphics donné par Java.
   */
  public void paint (Graphics g) {
    // On commence par mettre à jour la vision du joueur.
    joueur.vision(plateau.getTerrain());
    // On dessine sur tout le terrain.
    for (int i = 0; i < hautMax; i++)
      for (int j = 0; j < largMax; j++) {
        // Si on sort des limites du terrain, on saute ce tour de boucle.
        if (i + tabI - 1 >= plateau.getHauteur() || j + tabJ - 1 >= plateau.getLargeur())
          continue;
        // L'unité qui se trouve sur la case.
        AbstractUnite unite = plateau.getUnites()[i + tabI - 1][j + tabJ - 1];
        // Le type du terrain.
        int t = plateau.getTerrain()[i + tabI - 1][j + tabJ - 1].getType();
        // La fonction qui permet d'afficher la case et tous ses composants.
        dessineCase(g, t, j, i, unite);
      }
  }

  /**
   * Fonction dessinant une case du plateau.
   * Elle dessine le terrain, le brouillard de guerre, les unités, l'interface de Gameplay, etc.
   * @param g     Le contenu Graphics donné par Java.
   * @param type  Le type de terrain sur lequel on se trouve (plaine, etc.).
   * @param x     La position dans le tableau en abscisse.
   * @param y     La position dans le tableau en ordonnée.
   * @param unite L'unité qui se trouve sur la case (peut valoir 'null').
   */
  //
  public void dessineCase (Graphics g, int type, int x, int y, AbstractUnite unite) {
    // On dessine le terrain correspondant à la case donnée.
    chemin(g, type, y, x);

    // On met à jour le brouillard de guerre sur cette case.
    switch (joueur.getVision()[y + tabI - 1][x + tabJ - 1]) {
      // Si la case n'a jamais été visitée.
      case 0 :
        g.drawImage(Variable.noir, (x * taillePixel) - posJ - 100, (y * taillePixel) - posI - 100, this);
        break;
      // Si la case a été visitée mais se trouve dans le brouillard de guerre.
      case 1 :
        g.drawImage(Variable.gris, (x * taillePixel) - posJ - 100, (y * taillePixel) - posI - 100, this);
        break;
      default : break;
    }

    if ((cliquee != null) && joueur.possede(cliquee)
        && (y + tabI-2 >= 0)
        && (y + tabI < plateau.getTerrain().length)
        && (x + tabJ-2 >= 0)
        && (x + tabJ < plateau.getTerrain()[0].length)) {
          // Affichage des déplacements possibles.
          if ((!(cliquee.getAttaque()) || !attaque) && Math.abs((x + tabJ - 1) - cliquee.getX()) + Math.abs((y + tabI - 1) - cliquee.getY()) <= (cliquee.getDistance() - cliquee.getDeplace()))
            g.drawImage(Variable.vert, (x * taillePixel) - posJ - 100, (y * taillePixel) - posI - 100, this);
          // Affichage de la portée.
          if (cliquee.getAttaque() && attaque && (Math.abs((x + tabJ - 1) - cliquee.getX()) + Math.abs((y + tabI - 1) - cliquee.getY()) <= cliquee.getPortee()))
            g.drawImage(Variable.rouge, (x * taillePixel) - posJ - 100, (y * taillePixel) - posI - 100, this);
    }

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
        color = (unite.getAttaque()) ? Color.GREEN : Color.RED;
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
        // On dessine les dégats si il y en a eu.
      if (unite.getAnimDegats() != 0) { // On dessine les dégats reçus si il y a eu une attaque.
        BufferedImage deg = Variable.tImDegats[unite.getAnimDegats()];
        if (unite.getAnimDegats() % 5 < 3) // Pour faire défiler les 4 images de l'animation dégats.
          unite.setAnimDegats(unite.getAnimDegats() + 1);
        else
          unite.setAnimDegats(0); // Retour à pas d'animation.
        g.drawImage(deg, (x * taillePixel) - posJ - 90, (y * taillePixel) - posI - 80, this);
      }
    }
  }

  /**
   * Fonction utilitaire pour créer des rectangles ou des ovales de couleur "color" entourés de bordure noir.
   * La String form permet de définir si la forme choisie est un rectangle ou un ovale.
   * @param g      Le contenu Graphics donné par Java.
   * @param form   Une variable statique qui définit s'il s'agit d'un rond ou d'un rectangle.
   * @param x      La position dans le tableau en abscisse.
   * @param y      La position dans le tableau en ordonnée.
   * @param modX   Les modifications locales que l'on effectue sur la position en abscisse (position relative par exemple).
   * @param modY   Les modifications locales que l'on effectue sur la position en ordonnée (position relative par exemple).
   * @param width  La largeur de la forme que l'on souhaite créer.
   * @param height La hauteur de la forme que l'on souhaite créer.
   * @param color  La couleur de la forme que l'on souhaite créer.
   */
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

  /**
   * Permet de dessiner une forme de base sur le Graphics donné.
   * @param g      Le contenu Graphics donné par Java.
   * @param form   Une variable statique qui définit s'il s'agit d'un rond ou d'un rectangle.
   * @param x      La position dans le tableau en abscisse.
   * @param y      La position dans le tableau en ordonnée.
   * @param modX   Les modifications locales que l'on effectue sur la position en abscisse (position relative par exemple).
   * @param modY   Les modifications locales que l'on effectue sur la position en ordonnée (position relative par exemple).
   * @param width  La largeur de la forme que l'on souhaite créer.
   * @param height La hauteur de la forme que l'on souhaite créer.
   * @param color  La couleur de la forme que l'on souhaite créer.
   */
  public void drawForm(Graphics g, String form, int x, int y, int modX, int modY, int width, int height, Color color) {
    g.setColor(color);
    switch (form) {
      case doval : g.drawOval((x * taillePixel) - modX, (y * taillePixel) - modY, width, height); break;
      case foval : g.fillOval((x * taillePixel) - modX, (y * taillePixel) - modY, width, height); break;
      case drect : g.drawRect((x * taillePixel) - modX, (y * taillePixel) - modY, width, height); break;
      case frect : g.fillRect((x * taillePixel) - modX, (y * taillePixel) - modY, width, height); break;
    }
  }

  /**
   * Utile pour la barre de points de vie, la première largeur représente les contours noirs.
   * La deuxième représente la forme que l'on souhaite créer de la couleur souhaitée.
   * @param g           Le contenu Graphics donné par Java.
   * @param form        Une variable statique qui définit s'il s'agit d'un rond ou d'un rectangle.
   * @param x           La position dans le tableau en abscisse.
   * @param y           La position dans le tableau en ordonnée.
   * @param modX        Les modifications locales que l'on effectue sur la position en abscisse (position relative par exemple).
   * @param modY        Les modifications locales que l'on effectue sur la position en ordonnée (position relative par exemple).
   * @param width       La largeur de la bordure qui entoure la forme que l'on souhaite créer.
   * @param secondWidth La largeur de la forme que l'on souhaite créer.
   * @param height      La hauteur de la forme que l'on souhaite créer.
   * @param color       La couleur de la forme que l'on souhaite créer.
   */
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

  /**
   * Permet d'obtenir l'image adaptée en fonction du terrain et des cases adjcentes.
   * @param g    Le contenu Graphics donné par Java.
   * @param type le type du terrain considéré.
   * @param x    La position dans le tableau en abscisse.
   * @param y    La position dans le tableau en ordonnée.
   */
  public void chemin (Graphics g, int type, int x, int y) {
    // les différentes variables de la fonction.
    // Le terrain est nécessaire afin de calculer les terrains adjacents à la case observée.
    AbstractTerrain[][] t = plateau.getTerrain();
    // Contiendra l'image renvoyée par la fonction.
    BufferedImage img = Variable.tImTer[type];
    // Le chemin spécifique menant à l'image dans l'arborescence de fichier.
    String chemin;
    // Les 4 entiers suivants représentent une chaîne binaire.
    // Celle-ci permet de déterminer le nombre de côtés en contact avec un terrain d'un autre type.
    int a = check((y + tabJ > 1), x, y, -1, -2, t);
    int b = check((x + tabI > 1), x, y, -2, -1, t);
    int c = check((y + tabJ < plateau.getLargeur() - 1), x, y, -1, 0, t);
    int d = check((x + tabI < plateau.getHauteur() - 1), x, y, 0, -1, t);
    int place;
    int j;
    // En fonction du type de terrain sur lequel on se trouve, on ne charge pas les mêmes éléments.
    switch (type) {
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
    // On dessine l'image.
    g.drawImage(img, (y * taillePixel) - posJ - 100, (x * taillePixel) - posI - 100, this);
    // Dans le cas d'une ville, il faut vérifier que celle-ci appartient à un joueur et afficher sa couleur si nécessaire.
    if (x + tabI > 1
        && y + tabJ > 1
        && x + tabI < plateau.getHauteur() - 1
        &&  y + tabJ < plateau.getLargeur() - 1
        && t[x + tabI - 1][y + tabJ - 1] instanceof AbstractVille) {
      // On est certain que le terrain considéré est de type Ville.
      AbstractVille ville = ((AbstractVille)t[x + tabI - 1][y + tabJ - 1]);
      // Si cette ville appartient à un joueur, on dessine un carré de la couleur du joueur en bas à droite.
      if (ville.getJoueur() != null) {
        Color color = ville.getJoueur().getColor();
        // Si la ville a déjà créé une unité ce tour-ci, la couleur du carré est obscurcie.
        if (ville.getJoueur() == this.joueur && ville.getAchete())
          color = color.darker();
        makeForm(g, rect, y, x, posJ + 95, posI + 15, 10, 10, color);
      }
    }
  }

  /**
   * Transforme une chaîne binaire en sa valeur entière.
   * @param  s La chaîne binaire que l'on souhaite transformer.
   * @return   Renvoie la valeur entière correspondant à la chaîne binaire.
   */
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

  /**
   * Permet de vérifier le type du terrain adjacent à la plaine choisie.
   * @param  tab Les types des quatre cases adjacentes à la case choisie.
   * @return     Renvoie le type du terrain adjacent à la plaine choisie.
   */
  public int chercherTerrain(int[] tab) {
    for (int n : tab)
      if (n != 1 && n != 4)
        return n;
    return 1;
  }

  /**
   * Si i == a, renvoie 1. sinon, renvoie 0.
   * @param  i Un premier type de terrain.
   * @param  a Un deuxième type de terrain.
   * @return   0 ou 1
   */
  public int indice(int i, int a) {
    return ((i == a)? 1 :0);
  }

  /**
   * Fonction pour réduire de code.
   * Permet de récupérer le type d'un terrain adjacent à la case.
   * @param  a    Un boolean qui permet de définir une valeur de
   * @param  x    La position dans le tableau en abscisse.
   * @param  y    La position dans le tableau en ordonnée.
   * @param  modI Une modification locale dans le tableau des abscisses.
   * @param  modJ Une modification locale dans le tableau des ordonnées.
   * @param  t    Le tableau de terrain
   * @return      Renvoie un entier qui correspond soit au type de la case adjacente, soit à 1.
   */
   public int check(boolean a, int x, int y, int modI, int modJ, AbstractTerrain[][] terrain) {
    return ((a) ? (terrain[x + tabI + modI][y + tabJ + modJ].getType()) : 1);
  }

  /**
   * Change le joueur, s'assure que la carte ne garde pas une unité cliquée en mémoire et  met l'affichage à jour.
   */
  public void newTurn() {
    this.setJoueur(1);
    this.setCliquee(null);
    this.repaint();
  }

}
