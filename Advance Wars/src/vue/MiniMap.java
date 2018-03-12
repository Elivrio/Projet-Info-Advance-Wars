package src.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import java.awt.geom.Rectangle2D;

import src.Jeu;
import src.vue.Map;
import src.modele.Plateau;
import src.variable.Variable;
import src.modele.AbstractUnite;

public class MiniMap extends Map {

  private double taillePixel;
  private double posI, posJ;
  private int noirHaut, noirGauche;

  public MiniMap (Plateau plat, Jeu j,  int h, int l) {
    super(plat, j);
    haut = h;
    larg = l;
    taillePixel = larg/(p.getLargeur()+3);
    noirHaut = (int)(haut - taillePixel*(plat.getHauteur() + 2)) / 2;
    noirGauche = (int)(larg - taillePixel*(plat.getLargeur() + 6)) / 2;
  }

  public double getTaillePixel() {
    return taillePixel;
  }

  public void addTabI (int tI) {
    tabI += tI;
    posI = 0;
  }

  public void addTabJ (int tJ) {
    tabJ += tJ;
    posJ = 0;
  }

  public void addPosI (double pI) {
    posI += pI;
    repaint();
  }

  public void addPosJ (double pJ) {
    posJ += pJ;
    repaint();
  }

  public void paint (Graphics g) {
    super.paintComponent(g);

    joueur.vision();

    Graphics2D g2 = (Graphics2D) g;
    for (int i = 1; i < p.getHauteur(); i++)
      for (int j = 1; j < p.getLargeur(); j++) {

        // On dessine le terrain
        int t = p.getTerrain()[i][j].getType();
        BufferedImage img = Variable.tImTer[t];
        g.drawImage(img, (int)((j-1)*taillePixel) + noirGauche, (int)((i-1)*taillePixel) + noirHaut, this);

        // On met à jour le brouillard de guerre et les unités
        switch (joueur.getVision()[i][j]) {
          case 0 :
            g.drawImage(Variable.noir, (int)((j-1)*taillePixel) + noirGauche, (int)((i-1)*taillePixel) + noirHaut, this);
            break;
          case 1 :
            g.drawImage(Variable.gris, (int)((j-1)*taillePixel) + noirGauche, (int)((i-1)*taillePixel) + noirHaut, this);
            break;
          default :
            AbstractUnite unite = p.getUnites()[i][j];
            g.setColor(Color.BLUE);
            if (unite != null)
              if (joueur.possede(unite))
                g.drawImage(Variable.bleu, (int)((j-1)*taillePixel) + noirGauche, (int)((i-1)*taillePixel) + noirHaut, this);
              else
                g.drawImage(Variable.rouge, (int)((j-1)*taillePixel) + noirGauche, (int)((i-1)*taillePixel) + noirHaut, this);
            break;
        }

      }
    // On met à jour la vue actuelle
    g.setColor(Color.RED);
    Rectangle2D rect = new Rectangle2D.Double((tabJ-1)*taillePixel - posJ + noirGauche, (tabI-1)*taillePixel - posI + noirHaut, (largMax-2)*taillePixel, (hautMax-2)*taillePixel);
    g2.draw(rect);
  }

}