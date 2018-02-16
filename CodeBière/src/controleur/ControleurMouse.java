package src.controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import src.variable.Variable;
import src.modele.Unite;
import src.vue.Vue;

public class ControleurMouse extends Controleur implements MouseListener {

  public ControleurMouse(Vue v) {
    super(v);
  }

  // Fonction appelée lorsqu'on clique sur une case du plateau
  public void mouseClicked(MouseEvent me) {
    // Calcul de la case cliquée... Ne pas modifier
    int x = me.getX() + vue.getTabJ()*100 + vue.getPosJ();
    int y = me.getY() + vue.getTabI()*100 + vue.getPosI() - 30;
    Unite unite = caseCliquee(x, y);
    // Si la case possède une unité, on affiche ses caractéristiques
    if (unite != null)
      vue.informations(unite);
  }

  public Unite caseCliquee (int x, int y) {
    int taillePixel = vue.getTaillePixel();
    int i = y/taillePixel;
    int j = x/taillePixel;
    return vue.getUnites()[i][j];
  }

  public void mouseExited(MouseEvent me) {}
  public void mouseEntered(MouseEvent me) {}
  public void mouseReleased(MouseEvent me) {}
  public void mousePressed(MouseEvent me) {}
}
