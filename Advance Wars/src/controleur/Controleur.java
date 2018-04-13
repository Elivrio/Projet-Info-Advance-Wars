package src.controleur;

import src.vue.Vue;
import src.vue.MiniMap;
import src.vue.PanelMap;
import src.variable.Variable;

public class Controleur {

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  // La vue du jeu.
  protected Vue vue;

  // Le pas de mouvement, pour accorder la "zone visible" sur la mini map.
  protected double dep2;

  // La map du jeu.
  protected PanelMap map;

  // La mini map correspondante à la map.
  protected MiniMap miniMap;

  // Le pas de mouvement, pour éviter que la caméra ne bouge trop vite.
  protected final static int dep = 10;

  /**
   * @param vue La vue du jeu.
   */
  public Controleur (Vue vue) {
    this.vue = vue;
    map = vue.getMap();
    miniMap = vue.getMiniMap();
    dep2 = miniMap.getTaillePixel() / 10.0;
  }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Fonction servant à bouger la caméra
   * en fonction de ControleurKey et ControleurMouseMotion
   * @param  c Le caractère désignant la direction.
   * @return   Booléen permettant de savoir si on se déplace.
   */
  public boolean move (char c) {
    // On récupère la position actuelle de la caméra.
    int posJ = map.getPosJ(), posI = map.getPosI();
    int tabJ = map.getTabJ(), tabI = map.getTabI();

    // Aller à droite.
    if (c == 'd' && posJ >= 90) {
      if (tabJ + map.getLargMax() > map.getPlateau().getLargeur())
        return false;
      // On met à jour la map et la mini map.
      map.addTabJ(1);
      miniMap.addTabJ(1);
    }
    // Aller en bas.
    else if (c == 'b' && posI >= 90) {
      if (tabI + map.getHautMax() > map.getPlateau().getHauteur())
        return false;
      // On met à jour la map et la mini map.
      map.addTabI(1);
      miniMap.addTabI(1);
    }
    // Aller à gauche.
    else if (c == 'g' && posJ <= -100) {
      if (tabJ - 1 < 1)
        return false;
      // On met à jour la map et la mini map.
      map.addTabJ(-1);
      miniMap.addTabJ(-1);
    }
    // Aller en haut.
    else if (c == 'h' && posI <= -100) {
      if (tabI - 1 < 1)
        return false;
      // On met à jour la map et la mini map.
      map.addTabI(-1);
      miniMap.addTabI(-1);
    }
    else return true;
    return false;
  }
}
