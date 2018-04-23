package src.vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import src.vue.Jeu;
import src.vue.Map;
import src.modele.Plateau;
import src.variable.Variable;
import src.modele.AbstractUnite;

// Dessine la carte en version reduite dans le carre en bas a droite de la vue.

@SuppressWarnings("serial")
public class MiniMap extends Map {

  // ***************************************************
  // *************** Variable d'instance ***************
  // ***************************************************

  // Ces entiers permettent de placer la miniMap au centre du carre de l'affichage qui lui est reserve.
  private int noirHaut, noirGauche;

  // Permet de stocker en memoire la position sur l'ecran afin de faire du deplacement en continue.
  // Ces valeurs sont comprises entre -taillePixel et taillePixel.
  // Ce sont des double dans MiniMap afin de gagner en precision lors du deplacement.
  private double posI, posJ;

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param plateau Le plateau de jeu de la map.
   * @param jeu     Le jeu.
   * @param h       La hauteur que va avoir la minimap dans la vue.
   * @param l       La largeur que va avoir la minimap dans la vue.
   */
  public MiniMap (Plateau plateau,  int h, int l) {
    super(plateau);
    hauteur = h;
    largeur = l;
    taillePixel = largeur/(plateau.getLargeur()+3);
    noirHaut = (hauteur - taillePixel * (plateau.getHauteur() + 2)) / 2;
    noirGauche = (largeur - taillePixel * (plateau.getLargeur() + 6)) / 2;
  }

  // ***************************************
  // *************** Getters ***************
  // ***************************************

  public double getPosI() { return posI; }
  public double getPosJ() { return posJ; }

  public int getNoirHaut() { return noirHaut; }
  public int getNoirGauche() { return noirGauche; }

  // ***************************************
  // *************** Setters ***************
  // ***************************************

  /**
   * Permet de deplacer la position relative le long de l'axe des ordonnees et repaint la carte.
   * @param pI Ce que l'on ajoute a la position relative.
   */
  public void addPosI (double pI) {
    posI += pI;
    repaint();
  }

  /**
   * Permet de deplacer la position relative le long de l'axe des abscisses et repaint la carte.
   * @param pJ Ce que l'on ajoute a la position relative.
   */
  public void addPosJ (double pJ) {
    posJ += pJ;
    repaint();
  }

  /**
   * Permet de remettre la position relative a zero.
   */
  public void resetPosI() { posI = 0.0; }
  public void resetPosJ() { posJ = 0.0; }
  public void reset() { posI = 0.0; posJ = 0.0; }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * La fonction qui va dessiner la minimap dans le JPanel.
   * La fonction est appelee toute seule lors de l'execution et a chaque appel de la fonction repaint().
   * @param g Le Graphics d'un JPanel fourni par Java.
   */
  @Override
  public void paint (Graphics g) {
    super.paintComponent(g);

    // On met a jour la vision du joueur afin de dessiner la minimap de la bonne façon.
    joueur.vision(plateau.getTerrain());

    // On veut que l'affichage se fasse sur l'integralite du terrain.
    for (int i = 1; i < plateau.getHauteur(); i++)
      for (int j = 1; j < plateau.getLargeur(); j++) {

        // On dessine le terrain de base que le plateau contient.
        int t = plateau.getTerrain()[i][j].getType();
        BufferedImage img = Variable.tImTer[t];
        g.drawImage(img, (j - 1) * taillePixel + noirGauche, (i - 1) * taillePixel + noirHaut, this);

        // On ajoute une image qui va servir de filtre pour creer le brouillard de guerre.
        switch (joueur.getVision()[i][j]) {
          case 0 :
            // Pas de vision.
            g.drawImage(Variable.noir, (j - 1) * taillePixel + noirGauche, (i - 1) * taillePixel + noirHaut, this);
            break;

          case 1 :
            // Zone visitee mais pas directement dans la vision.
            g.drawImage(Variable.gris, (j - 1) * taillePixel + noirGauche, (i - 1) * taillePixel + noirHaut, this);
            break;

          default :
            // Si on a de la vision, on verifie si la case contient une unitee.
            AbstractUnite unite = plateau.getUnites()[i][j];

            // Si elle contient une unitee, on la dessine un carre de la couleur du joueur qui la contrôle.
            if (unite != null) {
              g.setColor(unite.getJoueur().getColor());
              g.fillRect((j - 1) * taillePixel + noirGauche, (i - 1) * taillePixel + noirHaut, taillePixel, taillePixel);
            }
            break;
        }
      }

    // On met a jour le carre rouge qui correspond a la zone de la carte sur laquelle on se situe.
    g.setColor(Color.RED);
    g.drawRect((tabJ - 1) * taillePixel - (int)(posJ) + noirGauche, (tabI - 1) * taillePixel - (int)(posI) + noirHaut, ((int)largMax - 2) * taillePixel, ((int)hautMax - 2) * taillePixel);
  }
}
