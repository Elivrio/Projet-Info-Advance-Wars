package src.controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import src.variable.Variable;
import src.vue.Vue;
import src.vue.PanelMap;

public class ControleurKey extends Controleur implements KeyListener {

  public ControleurKey(Vue v) {
    super(v);
  }

  // Fonction appelée lorsqu'une touche est pressée
  public void keyPressed(KeyEvent e) {
    // Clic sur la flèche droite ou sur la touche D
    if ((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd') && move('d'))
      map.addPosJ(dep);
    // Clic sur la flèche bas ou sur la touche S
    else if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == 's') && move('b'))
      map.addPosI(dep);
    // Clic sur la flèche gauche ou sur la touche Q
    else if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'q') && move('g'))
      map.addPosJ(-dep);
    // Clic sur la flèche haut ou sur la touche Z
    else if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == 'z') && move('h'))
      map.addPosI(-dep);
    else if (e.getKeyChar() == 'j') {
      map.setJoueur(1);
      vue.informations(map.getJoueur());
      map.repaint();
    }

  }

  public void keyReleased(KeyEvent e) {}
  public void keyTyped(KeyEvent evt) {}
}
