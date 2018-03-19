package src.controleur;

import src.vue.Jeu;
import src.vue.Vue;
import src.vue.MiniMap;
import src.vue.PanelMap;
import src.variable.Variable;

public class Controleur {

  protected Vue vue;
  protected Jeu jeu;
  protected double dep2;
  protected PanelMap map;
  protected MiniMap miniMap;
  protected final static int dep = 10;

  public Controleur (Vue v) {
    vue = v;
    map = vue.getMap();
    jeu = map.getJeu();
    miniMap = vue.getMiniMap();
    dep2 = miniMap.getTaillePixel() / 10;
  }

  public Controleur (PanelMap m) {
    map = m;
  }

  // Fonction servant à bouger la caméra, en fonction de ControleurKey et ControleurMouseMotion
  public boolean move (char c) {
    int posJ = map.getPosJ(), posI = map.getPosI();
    int tabJ = map.getTabJ(), tabI = map.getTabI();
    // Aller à droite
    if (c == 'd' && posJ >= 90) {
      if (tabJ + map.getLargMax() > map.getPlateau().getLargeur())
        return false;
      map.addTabJ(1);
      miniMap.addTabJ(1);
    }
    // Aller en bas
    else if (c == 'b' && posI >= 90) {
      if (tabI + map.getHautMax() > map.getPlateau().getHauteur())
        return false;
      map.addTabI(1);
      miniMap.addTabI(1);
    }
    // Aller à gauche
    else if (c == 'g' && posJ <= -100) {
      if (tabJ - 1 < 1)
        return false;
      map.addTabJ(-1);
      miniMap.addTabJ(-1);
    }
    // Aller en haut
    else if (c == 'h' && posI <= -100) {
      if (tabI - 1 < 1)
        return false;
      map.addTabI(-1);
      miniMap.addTabI(-1);
    }
    else return true;
    return false;
  }
}
