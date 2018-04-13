package src.controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import src.vue.Vue;
import src.vue.PanelMap;
import src.variable.Variable;

public class ControleurMouseMotion extends Controleur implements MouseMotionListener {

  /**
   * @param vue La vue du jeu.
   */
  public ControleurMouseMotion (Vue vue) {
    super(vue);
  }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Fonction appelée lorsqu'on déplace le curseur sur la fenêtre
   * @param me Mouvement de la souris transformé en variable par Java.
   */
  @Override
  public void mouseMoved (MouseEvent me) {
    // On récupère la taille actuelle de la map pour utiliser la position du curseur en pourcentage
    // La position en pourcentage est utile dans le cas où on a modifié la taille de la fenêtre
    int sizeY = map.getHeight();
    int sizeX = map.getWidth();
    // Si on place le curseur à 5% d'un bord de la map, la caméra se déplace dans ce sens
    if (me.getX() >= (95*sizeX/100) && move('d')) {
      map.addPosJ(dep);
      miniMap.addPosJ(-dep2);
    }
    if (me.getY() >= (95*sizeY/100) && move('b')) {
      map.addPosI(dep);
      miniMap.addPosI(-dep2);
    }
    if (me.getX() <= (5*sizeX/100) && move('g')) {
      map.addPosJ(-dep);
      miniMap.addPosJ(dep2);
    }
    if (me.getY() <= (15*sizeY/100) && move('h')) {
      map.addPosI(-dep);
      miniMap.addPosI(dep2);
    }
  }

  // Fonction de l'interface non utilisée.
  @Override
  public void mouseDragged(MouseEvent me) {}

}
