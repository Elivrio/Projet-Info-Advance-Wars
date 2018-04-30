package src.vue;

import java.lang.Math;

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
import src.modele.terrain.Qg;

import java.net.URL;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.net.MalformedURLException;
import java.io.IOException;
import java.lang.IllegalArgumentException;

@SuppressWarnings("serial")
public class PanelMap extends Map {

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  // Contient l'unite cliquee en ce moment.
  private AbstractUnite cliquee;

  // Contient une unite presente sur le plateau
  private AbstractUnite pion;

  // Precise si l'on doit afficher le deplacement de l'unite cliquee
  // ou la distance d'attaque de l'unite.
  private boolean attaque;

  // Permet de stocker en memoire la position sur l'ecran afin de faire du deplacement en continu.
  // Ces valeurs sont comprises entre -taillePixel et taillePixel
  protected int posI, posJ;

  // Pour savoir si on est a l'image 1 ou 2 de l'animation
  protected boolean animation;

  // Pour savoir si une unite peut se deplacer
  protected boolean bouge;

  // les cases qu'il ne faut pas paint;
  protected int m, n;



  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param plateau   Le Plateau de jeu qui va etre dessine durant la partie.
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
    m = -1;
    n = -1;
    Clip son; // clip du son
    File song; // fichier son
    song = new File("src/variable/son/bg.wav");
    try{
      URL url = song.toURI().toURL();
      // System.out.println(url); // test pour verifier si l'url est correct
      son = AudioSystem.getClip();
      son.open(AudioSystem.getAudioInputStream(url));
      son.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (LineUnavailableException|UnsupportedAudioFileException|IllegalArgumentException|IOException e) {}
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
  public AbstractUnite getPion() {return pion;}

  public boolean getBouge() { return bouge; }

  // ***************************************
  // *************** Setters ***************
  // ***************************************

  public void setAttaque (boolean b) { attaque = b; }
  public void setCliquee (AbstractUnite u) { cliquee = u; }
  public void setAnimation (boolean b) { animation = b; }

  public void setBouge(boolean b) { bouge = b; }

  /**
   * Permet de deplacer la position relative le long de l'axe des ordonnees et repaint la carte.
   * @param pI La valeur que l'on ajoute a la position relative.
   */
  public void addPosI (int pI) {
    posI += pI;
    repaint();
  }

  /**
   * Permet de deplacer la position relative le long de l'axe des abscisses et repaint la carte.
   * @param pJ La valeur que l'on ajoute a la position relative.
   */
  public void addPosJ (int pJ) {
    posJ += pJ;
    repaint();
  }

  /**
   * Permet de remettre la position relative a zero.
   */
  public void resetPosI() { posI = 0; }
  public void resetPosJ() { posJ = 0; }
  public void reset() { posJ = 0; posI = 0; }

  /**
   * Retire une unite du plateau d'unite.
   * @param u L'unite que l'on souhaite retirer du plateau.
   */
  public void rmvUnite (AbstractUnite u) { plateau.rmvUnite(u); }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  @Override
  /**
   * Permet de mettre a jour le contenu du terrain et de le dessiner dans le JPanel.
   * @param g Le contenu Graphics donne par Java.
   */
  public void paint (Graphics g) {
    // On commence par mettre a jour la vision du joueur.
    joueur.vision(plateau.getTerrain());
    // On dessine sur tout le terrain.
    for (int i = 0; i < hautMax; i++)
      for (int j = 0; j < largMax; j++) {
        // Si on sort des limites du terrain, on saute ce tour de boucle.
        if (i + tabI - 1 >= plateau.getHauteur() || j + tabJ - 1 >= plateau.getLargeur())
          continue;
        // L'unite qui se trouve sur la case.
        AbstractUnite unite = plateau.getUnites()[i + tabI - 1][j + tabJ - 1];
        // Le terrain de la case
        AbstractTerrain ter = plateau.getTerrain()[i + tabI - 1][j + tabJ - 1];
        // Le type du terrain.
        int t = plateau.getTerrain()[i + tabI - 1][j + tabJ - 1].getType();
        // La fonction qui permet d'afficher la case et tous ses composants.
        if (i==m && j==n){
          dessineChemin(g, ter, j, i, unite);
        }
        else {
          dessineCase(g, ter, j, i, unite);
          dessineChemin(g, ter, j, i, unite);
        }
      }
  }

  /**
   * Fonction dessinant une case du plateau.
   * Elle dessine le terrain, le brouillard de guerre, les unites, l'interface de Gameplay, etc.
   * @param g     Le contenu Graphics donne par Java.
   * @param terrain  Le terrain sur lequel on se trouve (plaine, etc.).
   * @param x     La position dans le tableau en abscisse.
   * @param y     La position dans le tableau en ordonnee.
   * @param unite L'unite qui se trouve sur la case (peut valoir 'null').
   */


  public void dessineCase (Graphics g, AbstractTerrain terrain, int x, int y, AbstractUnite unite) {
    // On dessine le terrain correspondant a la case donnee.
    chemin(g, terrain, y, x);

    // On met a jour le brouillard de guerre sur cette case.
    switch (joueur.getVision()[y + tabI - 1][x + tabJ - 1]) {
      // Si la case n'a jamais ete visitee.
      case 0 :
        g.drawImage(Variable.noir, (x * taillePixel) - posJ - 100, (y * taillePixel) - posI - 100, this);
        break;
      // Si la case a ete visitee mais se trouve dans le brouillard de guerre.
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
          int a = (x * taillePixel) - posJ - 100;
          int b = (y * taillePixel) - posI - 100;
          // Affichage des déplacements possibles.

          if ((!(cliquee.getAttaque()) || !attaque) && Math.abs((x + tabJ - 1) - cliquee.getX()) + Math.abs((y + tabI - 1) - cliquee.getY()) <= (cliquee.getDistance() - cliquee.getDeplace())){
            // Affichage des deplacements possibles.
            g.drawImage(Variable.vert, a, b, this);
            // Affichage du chemin
            int[][] circuit = cliquee.getChemin();
            if (isin(circuit, y+tabI-1, x+tabJ-1))
              g.drawImage(Variable.point, a, b, this);
          }
          // Affichage de la portee.
          if (cliquee.getAttaque() && attaque && (Math.abs((x + tabJ - 1) - cliquee.getX()) + Math.abs((y + tabI - 1) - cliquee.getY()) <= cliquee.getPortee()))
            g.drawImage(Variable.rouge, a, b, this);
    }
    // On dessine l'unite si elle est presente.
    if (unite != null && joueur.getVision()[y + tabI - 1][x + tabJ - 1] == 2) {
      if (!unite.getMouvement()){
        // On commence par recuperer l'unite concernee.
        BufferedImage uni;
        // choix de l'image de l'unite suivant le moment de l'animation
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

        // Si le joueur actuel possede l'unite, il faut afficher des informations en plus.
        if (joueur.possede(unite)) {
          // On affiche un rond pour preciser si l'unite selectionnee peut attaquer ou non.
          color = (unite.getAttaque()) ? Color.GREEN : Color.RED;
          makeForm(g, oval, x, y, posJ + 90, posI + 83, 5, 5, color);

          // On affiche plusieurs ronds pour montrer la distance que peut encore parcourir l'unite.
          for (int dis = 0; dis < unite.getDistance(); dis++) {
            color = Color.GREEN;
            if (dis < unite.getDeplace())
              color = Color.RED;
            makeForm(g, oval, x, y, posJ + 10, posI + 10 + (dis*6), 5, 5, color);
          }
        }

        // On recupere la couleur du joueur qui possede l'unite.
        // L'utilisation de MyColor a pour but d'ajouter de la transparence a la couleur.
        color = new MyColor(unite.getJoueur().getColor().getRGB(), 150, "");

        // Il y a distinction entre un general et une unite normale, les generaux sont plus grands.

        if (unite instanceof General) {
          // Le socle d'un general.
          makeForm(g, oval, x, y, posJ + 80, posI + 35, 60, 20, color);
          // Un general.
          g.drawImage(uni, (x * taillePixel) - posJ - 80, (y * taillePixel) - posI - 80, this);
        }
        else {
          // Le socle d'une unite.
          makeForm(g, oval, x, y, posJ + 75, posI + 45, 50, 20, color);
          // Une unite.
          g.drawImage(uni, (x * taillePixel) - posJ - 70, (y * taillePixel) - posI - 70, this);
        }
      }

        // On dessine les degats si il y en a eu.
      if (unite.getAnimDegats() != 0) { // On dessine les degats reçus si il y a eu une attaque.
        BufferedImage deg = Variable.tImDegats[unite.getAnimDegats()];
        if (unite.getAnimDegats() % 5 < 3) // Pour faire defiler les 4 images de l'animation degats.
          unite.setAnimDegats(unite.getAnimDegats() + 1);
        else
          unite.setAnimDegats(0); // Retour a pas d'animation.
        g.drawImage(deg, (x * taillePixel) - posJ - 90, (y * taillePixel) - posI - 80, this);
      }
    }
  }

  /**
   * Fonction dessinant l'animation du deplacement
   *
   * @param g     Le contenu Graphics donne par Java.
   * @param type  Le type de terrain sur lequel on se trouve (plaine, etc.).
   * @param x     La position dans le tableau en abscisse.
   * @param y     La position dans le tableau en ordonnee.
   * @param unite L'unite qui se trouve sur la case (peut valoir 'null').
   */
  public void dessineChemin(Graphics g, AbstractTerrain terrain, int x, int y, AbstractUnite unite) {
    if (unite != null){
      pion = unite;
      int etape = unite.getStatusChemin();
      if (etape+1 < unite.getChemin().length){
        if (bouge){

          // la couleur et l'image necessaire
          Color color = new MyColor(unite.getJoueur().getColor().getRGB(), 150, "");
          BufferedImage uni;
          if (animation) {
            uni = Variable.tImUni1[unite.getIndice()-1];
          }
          else {
            uni = Variable.tImUni2[unite.getIndice()-1];
          }

          // abscisse de la case actuelle et de la suivante
          int a1 = unite.getChemin()[etape][0];
          int a2 = unite.getChemin()[etape+1][0];
          // ordonnees de la case actuelle et de la suivante
          int b1 = unite.getChemin()[etape][1];
          int b2 = unite.getChemin()[etape +1][1];

          int a3 = Math.max(a1, a2);
          int b3 = Math.max(b1, b2);

          int status = unite.getStatusMouv();
          if (status < 3)
            unite.setStatusMouv(status+1);
          else {
            unite.setStatusMouv(0);
            unite.setStatusChemin(etape+1);
          }
          int pas = (taillePixel/4) * status;

          // Egal a 1, 0 ou -1 pour connaitre la direction
          int orientationX = ((a1 - a2)<=1) ? (a1 - a2): 0;
          int orientationY = ((b1 - b2)<=1) ? (b1 - b2): 0;

          if (a3 !=0 && b3 != 0){
            n = b3 - tabJ+1;
            m = a3 - tabI+1;

            chemin(g, terrain, m, n);

            if (unite instanceof General){
              makeForm(g, oval, n, m, posJ+80+(pas*orientationY), posI+35+(pas*orientationX), 60, 20, color);
              g.drawImage(uni,(n * taillePixel)- posJ - 80 -(pas*orientationY), (m*taillePixel)-posI-80-(pas*orientationX), this);
            }
            else {
              makeForm(g, oval, n, m, posJ+75+(pas*orientationY), posI+45+(pas*orientationX),50,20, color);
              g.drawImage(uni,(n*taillePixel)-posJ-70-(pas*orientationY), (m*taillePixel)-posI-70-(pas*orientationX), this);
            }
          }
          if (a3 == 0 && b3 ==0){
            m = -1;
            n = -1;
          }
          bouge = false;
        }
      }
      else {
        pion = null;
        unite.setStatusChemin(0);
        unite.setMouvement(false);
        unite.setChemin(new int[0][0]);
        m = -1;
        n = -1;
      }
    }

  }

  /**
   * Fonction utilitaire pour creer des rectangles ou des ovales de couleur "color" entoures de bordure noir.
   * La String form permet de definir si la forme choisie est un rectangle ou un ovale.
   * @param g      Le contenu Graphics donne par Java.
   * @param form   Une variable statique qui definit s'il s'agit d'un rond ou d'un rectangle.
   * @param x      La position dans le tableau en abscisse.
   * @param y      La position dans le tableau en ordonnee.
   * @param modX   Les modifications locales que l'on effectue sur la position en abscisse (position relative par exemple).
   * @param modY   Les modifications locales que l'on effectue sur la position en ordonnee (position relative par exemple).
   * @param width  La largeur de la forme que l'on souhaite creer.
   * @param height La hauteur de la forme que l'on souhaite creer.
   * @param color  La couleur de la forme que l'on souhaite creer.
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
   * Permet de dessiner une forme de base sur le Graphics donne.
   * @param g      Le contenu Graphics donne par Java.
   * @param form   Une variable statique qui definit s'il s'agit d'un rond ou d'un rectangle.
   * @param x      La position dans le tableau en abscisse.
   * @param y      La position dans le tableau en ordonnee.
   * @param modX   Les modifications locales que l'on effectue sur la position en abscisse (position relative par exemple).
   * @param modY   Les modifications locales que l'on effectue sur la position en ordonnee (position relative par exemple).
   * @param width  La largeur de la forme que l'on souhaite creer.
   * @param height La hauteur de la forme que l'on souhaite creer.
   * @param color  La couleur de la forme que l'on souhaite creer.
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
   * Utile pour la barre de points de vie, la premiere largeur represente les contours noirs.
   * La deuxieme represente la forme que l'on souhaite creer de la couleur souhaitee.
   * @param g           Le contenu Graphics donne par Java.
   * @param form        Une variable statique qui definit s'il s'agit d'un rond ou d'un rectangle.
   * @param x           La position dans le tableau en abscisse.
   * @param y           La position dans le tableau en ordonnee.
   * @param modX        Les modifications locales que l'on effectue sur la position en abscisse (position relative par exemple).
   * @param modY        Les modifications locales que l'on effectue sur la position en ordonnee (position relative par exemple).
   * @param width       La largeur de la bordure qui entoure la forme que l'on souhaite creer.
   * @param secondWidth La largeur de la forme que l'on souhaite creer.
   * @param height      La hauteur de la forme que l'on souhaite creer.
   * @param color       La couleur de la forme que l'on souhaite creer.
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

  /*public void animDeplacement(Graphics g,String form ,int x, int y, int modX, int ModY, int width,int secondWidth, int height, Color color){

  }*/

  /**
   * Permet d'obtenir l'image adaptee en fonction du terrain et des cases adjcentes.
   * @param g    Le contenu Graphics donne par Java.
   * @param terrain le terrain considere.
   * @param x    La position dans le tableau en abscisse.
   * @param y    La position dans le tableau en ordonnee.
   */
  public void chemin (Graphics g, AbstractTerrain terrain, int x, int y) {
    // les differentes variables de la fonction.
    // Le terrain est necessaire afin de calculer les terrains adjacents a la case observee.
    int type = terrain.getType();
    AbstractTerrain[][] t = plateau.getTerrain();
    // Contiendra l'image renvoyee par la fonction.
    BufferedImage img = Variable.tImTer[type];
    // Le chemin specifique menant a l'image dans l'arborescence de fichier.
    String chemin;
    // Les 4 entiers suivants representent une chaîne binaire.
    // Celle-ci permet de determiner le nombre de côtes en contact avec un terrain d'un autre type.
    int a = check((y + tabJ > 1), x, y, -1, -2, t);
    int b = check((x + tabI > 1), x, y, -2, -1, t);
    int c = check((y + tabJ < plateau.getLargeur() - 1), x, y, -1, 0, t);
    int d = check((x + tabI < plateau.getHauteur() - 1), x, y, 0, -1, t);
    int place;
    int j;
    // En fonction du type de terrain sur lequel on se trouve, on ne charge pas les memes elements.
    switch (type) {
      // Liaisons plaines-terrainDeTypeJ.
      case 1 :
        int[] tab = {a, b, c, d};
        // On calcule le type du terrain adjacent.
        j = chercherTerrain(tab);
        // On calcule la chaine binaire qui sera en prefixe de l'image.
        chemin = indice(j, a) + "" + indice(j, b) + "" + indice(j, c) + "" + indice(j, d);
        // A partir de la chaine binaire, on calcule la position cible dans le tableau d'image.
        place = stringBinaryToInt(chemin);
        // En fonction du terrain, on a cherche l'image adequate.
        switch (j) {
          case 0 : img = Variable.tImPlaineForet[place]; break;
          case 2 : img = Variable.tImPlaineEau[place]; break;
        }
        break;
      // Liaisons eau-plage, la plage etant egalement de l'eau mais qui doit etre geree separement.
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
        }else{
          img = Variable.tImEauPlage[place];
        }
        break;
      // QG personnalise avec le general
      case 9 :
        if (terrain instanceof Qg) {
          Qg qg = (Qg) terrain;
          img = Variable.tImTer[type + qg.getJoueur().getUnites().get(0).getIndice() - 1];
        }
    }
    // On dessine l'image.
    g.drawImage(img, (y * taillePixel) - posJ - 100, (x * taillePixel) - posI - 100, this);
    // Dans le cas d'une ville, il faut verifier que celle-ci appartient a un joueur et afficher sa couleur si necessaire.
    if (x + tabI > 1
        && y + tabJ > 1
        && x + tabI < plateau.getHauteur() - 1
        &&  y + tabJ < plateau.getLargeur() - 1
        && t[x + tabI - 1][y + tabJ - 1] instanceof AbstractVille) {
      // On est certain que le terrain considere est de type Ville.
      AbstractVille ville = ((AbstractVille)t[x + tabI - 1][y + tabJ - 1]);
      // Si cette ville appartient a un joueur, on dessine un carre de la couleur du joueur en bas a droite.
      if (ville.getJoueur() != null) {
        Color color = ville.getJoueur().getColor();
        // Si la ville a deja cree une unite ce tour-ci, la couleur du carre est obscurcie.
        if (ville.getJoueur() == this.joueur && ville.getAchete())
          color = color.darker();
        makeForm(g, rect, y, x, posJ + 95, posI + 15, 10, 10, color);
      }
    }
  }

  /**
   * Fonction qui change l'unite de place
   * @param unite   l'unite qui bouge
   * @param chemin  le chemin qu'elle prend
   */
  public void mouvement(AbstractUnite unite, int[][] chemin){
    for (int k = 0; k < chemin.length; k++){
      int x = chemin[k][0];
      int y = chemin[k][1];
      if ((x==-1 && y==-1) ||(k >= 1 && plateau.getUnites()[x][y] != null))
        break;
      if (x != 0 && y != 0) {
        plateau.setUnites(unite.getX(), unite.getY(), y, x);
        unite.setDeplace(Math.abs((y) - unite.getX()) + Math.abs((x) - unite.getY()));
        unite.setCase(y, x);
        joueur.vision(plateau.getTerrain());
      }
    }
  }


  /**
   * Transforme une chaîne binaire en sa valeur entiere.
   * @param  s La chaîne binaire que l'on souhaite transformer.
   * @return   Renvoie la valeur entiere correspondant a la chaîne binaire.
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
   * Permet de verifier le type du terrain adjacent a la plaine choisie.
   * @param  tab Les types des quatre cases adjacentes a la case choisie.
   * @return     Renvoie le type du terrain adjacent a la plaine choisie.
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
   * @param  a Un deuxieme type de terrain.
   * @return   0 ou 1
   */
  public int indice(int i, int a) {
    return ((i == a)? 1 :0);
  }

  /**
   * Fonction pour reduire de code.
   * Permet de recuperer le type d'un terrain adjacent a la case.
   * @param  a    Un boolean qui permet de definir une valeur de
   * @param  x    La position dans le tableau en abscisse.
   * @param  y    La position dans le tableau en ordonnee.
   * @param  modI Une modification locale dans le tableau des abscisses.
   * @param  modJ Une modification locale dans le tableau des ordonnees.
   * @param  t    Le tableau de terrain
   * @return      Renvoie un entier qui correspond soit au type de la case adjacente, soit a 1.
   */
   public int check(boolean a, int x, int y, int modI, int modJ, AbstractTerrain[][] terrain) {
    return ((a) ? (terrain[x + tabI + modI][y + tabJ + modJ].getTypeLiaison()) : 1);
  }

  /**
   * Permet de savoir si un couple d'entier appartient a un tableau
   * @param intTab le tableau
   * @param a le premier element du couple
   * @parma b le second element du couple
   * @return true si (a,b) appartient au tableau
   */
   public boolean isin (int[][] intTab, int a, int b){
    for (int i=1; i<intTab.length; i++){
      if (intTab[i][0] == a && intTab[i][1] == b)
        return true;
    }
    return false;
  }

  /**
   * Change le joueur, s'assure que la carte ne garde pas une unité cliquée en mémoire et  met l'affichage à jour.
   */
  public void nouveauTour() {
    this.setJoueur(1);
    this.setCliquee(null);
    this.repaint();
  }



}
