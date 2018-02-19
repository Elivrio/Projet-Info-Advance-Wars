package src.controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import src.variable.Variable;
import src.vue.Vue;
import src.vue.PanelMap;


public class Controleur {

  protected final static int dep = 10;
  protected Vue vue;
  protected PanelMap map;

  public Controleur(Vue v) {
    vue = v;
    map = vue.getMap();
  }

  public Controleur(PanelMap m) {
    map = m;
  }

  // Fonction servant à bouger la caméra, en fonction de ControleurKey et ControleurMouseMotion
  public boolean move (char c) {
    int posJ = map.getPosJ(), posI = map.getPosI();
    int tabJ = map.getTabJ(), tabI = map.getTabI();
    // Aller à droite
    if (c == 'd' && posJ >= 90) {
      if (tabJ + map.getLargMax() >= map.getTerrain()[0].length)
        return false;
      map.addTabJ(1);
    }
    // Aller en bas
    else if (c == 'b' && posI >= 90) {
      if (tabI + map.getHautMax() >= map.getTerrain().length)
        return false;
      map.addTabI(1);
    }
    // Aller à gauche
    else if (c == 'g' && posJ <= -100) {
      if (tabJ - 1 < 1)
        return false;
      map.addTabJ(-1);
    }
    // Aller en haut
    else if (c == 'h' && posI <= -100) {
      if (tabI - 1 < 1)
        return false;
      map.addTabI(-1);
    }
    else return true;
    return false;
  }
}
