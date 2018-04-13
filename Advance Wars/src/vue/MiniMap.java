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

// Dessine la carte en version réduite dans le carré en bas à droite de la vue.

@SuppressWarnings("serial")
public class MiniMap extends Map {

  // ***************************************************
  // *************** Variable d'instance ***************
  // ***************************************************

  // Ces entiers permettent de placer la miniMap au centre du carré de l'affichage qui lui est réservé.
  private int noirHaut, noirGauche;

  // Permet de stocker en mémoire la position sur l'écran afin de faire du déplacement en continue.
  // Ces valeurs sont comprises entre -taillePixel et taillePixel.
  // Ce sont des double dans MiniMap afin de gagner en précision lors du déplacement.
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
  public MiniMap (Plateau plateau, Jeu jeu,  int h, int l) {
    super(plateau, jeu);
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
   * Permet de déplacer la position relative le long de l'axe des ordonnées et repaint la carte.
   * @param pI Ce que l'on ajoute à la position relative.
   */
  public void addPosI (double pI) {
    posI += pI;
    repaint();
  }

  /**
   * Permet de déplacer la position relative le long de l'axe des abscisses et repaint la carte.
   * @param pJ Ce que l'on ajoute à la position relative.
   */
  public void addPosJ (double pJ) {
    posJ += pJ;
    repaint();
  }

  /**
   * Permet de remettre la position relative à zéro.
   */
  public void resetPosI() { posI = 0.0; }
  public void resetPosJ() { posJ = 0.0; }
  public void reset() { posI = 0.0; posJ = 0.0; }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * La fonction qui va dessiner la minimap dans le JPanel.
   * La fonction est appelée toute seule lors de l'exécution et à chaque appel de la fonction repaint().
   * @param g Le Graphics d'un JPanel fourni par Java.
   */
  @Override
  public void paint (Graphics g) {
    super.paintComponent(g);

    // On met à jour la vision du joueur afin de dessiner la minimap de la bonne façon.
    joueur.vision(plateau.getTerrain());

    // On veut que l'affichage se fasse sur l'intégralité du terrain.
    for (int i = 1; i < plateau.getHauteur(); i++)
      for (int j = 1; j < plateau.getLargeur(); j++) {

        // On dessine le terrain de base que le plateau contient.
        int t = plateau.getTerrain()[i][j].getType();
        BufferedImage img = Variable.tImTer[t];
        g.drawImage(img, (j - 1) * taillePixel + noirGauche, (i - 1) * taillePixel + noirHaut, this);

        // On ajoute une image qui va servir de filtre pour créer le brouillard de guerre.
        switch (joueur.getVision()[i][j]) {
          case 0 :
            // Pas de vision.
            g.drawImage(Variable.noir, (j - 1) * taillePixel + noirGauche, (i - 1) * taillePixel + noirHaut, this);
            break;

          case 1 :
            // Zone visitée mais pas directement dans la vision.
            g.drawImage(Variable.gris, (j - 1) * taillePixel + noirGauche, (i - 1) * taillePixel + noirHaut, this);
            break;

          default :
            // Si on a de la vision, on vérifie si la case contient une unitée.
            AbstractUnite unite = plateau.getUnites()[i][j];

            // Si elle contient une unitée, on la dessine un carré de la couleur du joueur qui la contrôle.
            if (unite != null) {
              g.setColor(unite.getJoueur().getColor());
              g.fillRect((j - 1) * taillePixel + noirGauche, (i - 1) * taillePixel + noirHaut, taillePixel, taillePixel);
            }
            break;
        }
      }

    // On met à jour le carré rouge qui correspond à la zone de la carte sur laquelle on se situe.
    g.setColor(Color.RED);
    g.drawRect((tabJ - 1) * taillePixel - (int)(posJ) + noirGauche, (tabI - 1) * taillePixel - (int)(posI) + noirHaut, ((int)largMax - 2) * taillePixel, ((int)hautMax - 2) * taillePixel);
  }
}
