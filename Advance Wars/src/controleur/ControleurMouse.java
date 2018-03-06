package src.controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import src.variable.Variable;
import src.vue.Vue;
import src.modele.AbstractUnite;
import src.modele.AbstractTerrain;
import src.vue.PanelMap;

public class ControleurMouse extends Controleur implements MouseListener {

  public ControleurMouse (Vue v) {
    super(v);
  }

  // Fonction appelée lorsqu'on clique sur une case du plateau
  public void mouseClicked (MouseEvent me) {
    // Calcul de la case cliquée... Ne pas modifier
    int x = me.getX() + map.getTabJ()*100 + map.getPosJ();
    int y = me.getY() + map.getTabI()*100 + map.getPosI();
    int taillePixel = map.getTaillePixel();
    int i = y/taillePixel;
    int j = x/taillePixel;
    AbstractUnite unite = isUnite(i, j);
    AbstractTerrain terrain = isTerrain(i, j);
    boolean attaque = false;

    AbstractUnite cliquee = map.getCliquee();
    // Si les déplacements sont affichés et qu'on clique sur une case cible possible, on y va
    if (cliquee != null
        && map.getJoueur().possede(cliquee)
        && (i-1 >= 0)
        && (i < map.getTerrain().length)
        && (j-1 >= 0)
        && (j < map.getTerrain()[0].length)) {
          if (!map.getAttaque()
              && unite == null
              && cliquee.getDeplace() + Math.abs((j) - cliquee.getX()) + Math.abs((i) - cliquee.getY()) <= cliquee.getDistance()) {
                map.getPlateau().setUnites(cliquee.getX(), cliquee.getY(), j, i);
                cliquee.setDeplace(Math.abs((j) - cliquee.getX()) + Math.abs((i) - cliquee.getY()));
                cliquee.setCase(j, i);
                map.getJoueur().vision();
          }
          if (unite != null
              && !map.getJoueur().possede(unite)
              && (Math.abs((j) - cliquee.getX()) + Math.abs((i) - cliquee.getY()) <= cliquee.getPortee())
              && map.getAttaque()
              && cliquee.getAttaque() < 1) {
                cliquee.attaquer(unite);
                if (unite.getPV() <= 0)
                  mort(unite);
                vue.informations(cliquee, unite, cliquee.getDegats());
                attaque = true;
          }
    }
    if (!attaque) {
      map.setAttaque(false);
      map.setCliquee(unite);
      // Si la case possède une unité, on affiche ses caractéristiques
      if (unite != null && map.getJoueur().possede(unite))
        vue.informations(unite);
      // Sinon, on affiche les caractéristiques du terrain
      else
        vue.informations(terrain, map.getJoueur().getVision()[i][j]);
    }
    map.repaint();
  }

  public void mort (AbstractUnite u) {
    u.getJoueur().remove(u);
    map.rmvUnite(u);
  }

  public AbstractUnite isUnite (int i, int j) {
    return map.getUnites()[i][j];
  }

  public AbstractTerrain isTerrain (int i, int j) {
    return map.getTerrain()[i][j];
  }

  public void mouseExited(MouseEvent me) {}
  public void mouseEntered(MouseEvent me) {}
  public void mouseReleased(MouseEvent me) {}
  public void mousePressed(MouseEvent me) {}
}
