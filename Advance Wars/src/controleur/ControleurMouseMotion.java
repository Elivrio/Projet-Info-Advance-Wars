package src.controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import src.variable.Variable;
import src.vue.Vue;
import src.vue.PanelMap;

public class ControleurMouseMotion extends Controleur implements MouseMotionListener {

  public ControleurMouseMotion(Vue v) {
    super(v);
  }

  // Fonction appelée lorsqu'on déplace le curseur sur la fenêtre
  public void mouseMoved(MouseEvent me) {
    // On récupère la taille actuelle de la fenêtre pour utiliser la position du curseur en pourcentage
    // La position en pourcentage est utile dans le cas où on a modifié la taille de la fenêtre
    int sizeY = map.getHeight();
    int sizeX = map.getWidth();
    // Si on place le curseur à 5% du bord de la fenêtre, la caméra se déplace dans ce sens
    if (me.getX() >= (95*sizeX/100) && move('d'))
      map.addPosJ(dep);
    if (me.getY() >= (95*sizeY/100) && move('b'))
      map.addPosI(dep);
    if (me.getX() <= (5*sizeX/100) && move('g'))
      map.addPosJ(-dep);
    if (me.getY() <= (15*sizeY/100) && move('h'))
      map.addPosI(-dep);
  }

  public void mouseDragged(MouseEvent me) {}

}
