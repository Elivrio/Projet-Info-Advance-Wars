package src.controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import src.variable.Variable;
import src.vue.Vue;


public class Controleur {
  Vue vue;

  public Controleur(Vue v) {
    vue = v;
  }

  // Fonction servant à bouger la caméra, en fonction de ControleurKey et ControleurMouseMotion
  public boolean move (char c) {
    int posJ = vue.getPosJ(), posI = vue.getPosI();
    int tabJ = vue.getTabJ(), tabI = vue.getTabI();
    // Aller à droite
    if (c == 'd' && posJ >= 90) {
      if (tabJ + vue.getLargMax() >= vue.getTerrain()[0].length)
        return false;
      vue.addTabJ(1);
    }
    // Aller en bas
    else if (c == 'b' && posI >= 90) {
      if (tabI + vue.getHautMax() >= vue.getTerrain().length)
        return false;
      vue.addTabI(1);
    }
    // Aller à gauche
    else if (c == 'g' && posJ <= -100) {
      if (tabJ - 1 < 1)
        return false;
      vue.addTabJ(-1);
    }
    // Aller en haut
    else if (c == 'h' && posI <= -70) {
      if (tabI - 1 < 1)
        return false;
      vue.addTabI(-1);
    }
    else return true;
    return false;
  }
}
