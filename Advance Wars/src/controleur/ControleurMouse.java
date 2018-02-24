package src.controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import src.variable.Variable;
import src.vue.Vue;
import src.modele.Unite;
import src.modele.Terrain;
import src.vue.PanelMap;

public class ControleurMouse extends Controleur implements MouseListener {

  public ControleurMouse(Vue v) {
    super(v);
  }

  // Fonction appelée lorsqu'on clique sur une case du plateau
  public void mouseClicked(MouseEvent me) {
    // Calcul de la case cliquée... Ne pas modifier
    int x = me.getX() + map.getTabJ()*100 + map.getPosJ();
    int y = me.getY() + map.getTabI()*100 + map.getPosI();
    int taillePixel = map.getTaillePixel();
    int i = y/taillePixel;
    int j = x/taillePixel;
    Unite unite = isUnite(i, j);
    Terrain terrain = isTerrain(i, j);
    // Si la case possède une unité, on affiche ses caractéristiques
    map.setCliquee(unite);
    if (unite != null && map.getJoueur().possede(unite))
      vue.informations(unite);
    else
      vue.informations(terrain, map.getJoueur().getVision()[i][j]);
    map.repaint();
  }

  public Unite isUnite (int i, int j) {
    return map.getUnites()[i][j];
  }

  public Terrain isTerrain (int i, int j) {
    return map.getTerrain()[i][j];
  }

  public void mouseExited(MouseEvent me) {}
  public void mouseEntered(MouseEvent me) {}
  public void mouseReleased(MouseEvent me) {}
  public void mousePressed(MouseEvent me) {}
}
