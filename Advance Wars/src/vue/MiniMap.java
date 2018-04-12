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

  public MiniMap (Plateau plat, Jeu j,  int h, int l) {
    super(plat, j);
    haut = h;
    larg = l;
    taillePixel = larg/(p.getLargeur()+3);
    noirHaut = (haut - taillePixel * (plat.getHauteur() + 2)) / 2;
    noirGauche = (larg - taillePixel * (plat.getLargeur() + 6)) / 2;
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

  // Permet de déplacer la position relative le long de l'axe des ordonnées et repaint la carte.
  public void addPosI (double pI) {
    posI += pI;
    repaint();
  }

  // Permet de déplacer la position relative le long de l'axe des abscisses et repaint la carte.
  public void addPosJ (double pJ) {
    posJ += pJ;
    repaint();
  }

  public void resetPosI() { posI = 0.0; }
  public void resetPosJ() { posJ = 0.0; }
  public void reset() { posI = 0.0; posJ = 0.0; }

  // *****************************************
  // *************** Fonctions ***************
  // *****************************************

  @Override
  public void paint (Graphics g) {
    super.paintComponent(g);

    // On met à jour la vision du joueur afin de dessiner la minimap de la bonne façon.
    joueur.vision(p.getTerrain());

    // On veut que l'affichage se fasse sur l'intégralité du terrain.
    for (int i = 1; i < p.getHauteur(); i++)
      for (int j = 1; j < p.getLargeur(); j++) {

        // On dessine le terrain de base que le plateau contient.
        int t = p.getTerrain()[i][j].getType();
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
            AbstractUnite unite = p.getUnites()[i][j];

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
