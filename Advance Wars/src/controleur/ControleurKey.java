package src.controleur;

import java.awt.Event;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import src.vue.Vue;
import src.vue.PanelMap;
import src.variable.Variable;

public class ControleurKey extends Controleur implements KeyListener {

  /**
   * @param vue La vue du jeu.
   */
  public ControleurKey (Vue vue) {
    super(vue);
  }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Fonction appelée lorsqu'une touche est pressée
   * @param e Touche pressée transformée en variable par Java.
   */
  @Override
  public void keyPressed (KeyEvent e) {
    // Clic sur la flèche droite ou sur la touche D.
    if ((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd') && move('d')) {
      // Déplacement de la zone visible sur la map et accord de la mini map.
      map.addPosJ(dep);
      miniMap.addPosJ(-dep2);
    }
    // Clic sur la flèche bas ou sur la touche S.
    else if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == 's') && move('b')) {
      // Déplacement de la zone visible sur la map et accord de la mini map.
      map.addPosI(dep);
      miniMap.addPosI(-dep2);
    }
    // Clic sur la flèche gauche ou sur la touche Q.
    else if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'q') && move('g')) {
      // Déplacement de la zone visible sur la map et accord de la mini map.
      map.addPosJ(-dep);
      miniMap.addPosJ(dep2);
    }
    // Clic sur la flèche haut ou sur la touche Z.
    else if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == 'z') && move('h')) {
      // Déplacement de la zone visible sur la map et accord de la mini map.
      map.addPosI(-dep);
      miniMap.addPosI(dep2);
    }
    // Clic sur la touche Entrée ou J pour changer de joueur.
    else if (e.getKeyChar() == Event.ENTER || e.getKeyChar() == 'j') {
      vue.finTour();
    }
    // Clic sur la touche A pour attaquer.
    else if (e.getKeyChar() == 'a' && map.getCliquee() != null) {
      // Si la portée est déjà affichée, on l'efface.
      if (map.getAttaque())
        map.setAttaque(false);
      // Sinon, on l'affiche.
      else map.setAttaque(true);
      map.repaint();
    }
    // Clic sur Echap pour effacer le déplacement et la portée.
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      map.setCliquee(null);
      map.repaint();
    }
  }

  // Fonctions de l'interface non utilisées.
  @Override
  public void keyReleased (KeyEvent e) {}
  public void keyTyped (KeyEvent evt) {}
}
