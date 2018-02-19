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
    map.setCaseCliquee(false);
    map.repaint();
    // Calcul de la case cliquée... Ne pas modifier
    int x = me.getX() + map.getTabJ()*100 + map.getPosJ();
    int y = me.getY() + map.getTabI()*100 + map.getPosI() - 30;
    Unite unite = isUnite(x, y);
    Terrain terrain = isTerrain(x, y);
    // Si la case possède une unité, on affiche ses caractéristiques
    if (unite != null) {
      vue.informations(unite);
      map.setCaseCliquee(true);
      map.repaint();
    }
    else vue.informations(terrain);
  }

  public Unite isUnite (int x, int y) {
    int taillePixel = map.getTaillePixel();
    int i = y/taillePixel;
    int j = x/taillePixel;
    return map.getUnites()[i][j];
  }

  public Terrain isTerrain (int x, int y) {
    int taillePixel = map.getTaillePixel();
    int i = y/taillePixel;
    int j = x/taillePixel;
    return map.getTerrain()[i][j];
  }

  public void mouseExited(MouseEvent me) {}
  public void mouseEntered(MouseEvent me) {}
  public void mouseReleased(MouseEvent me) {}
  public void mousePressed(MouseEvent me) {}
}