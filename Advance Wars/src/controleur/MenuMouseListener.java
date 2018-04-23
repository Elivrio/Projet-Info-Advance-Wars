package src.controleur;

import javax.swing.JTextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import src.vue.Menu;

public class MenuMouseListener implements MouseListener {

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  // Le menu du jeu.
  private Menu menu;

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param menu Le menu du jeu.
   */
  public MenuMouseListener (Menu menu) {
    this.menu = menu;
  }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Est appelee quand on fait un clic sur la map.
   * @param e Clic de la souris transforme en variable par Java.
   */
  @Override
  public void mousePressed (MouseEvent e) {
    // Si on clique sur un champ de texte, on efface son contenu.
    JTextField source = (JTextField) e.getSource();
    source.setText("");
  }

  // Fonctions de l'interface non utilisees.
  @Override
  public void mouseExited (MouseEvent e) {}
  public void mouseEntered (MouseEvent e) {}
  public void mouseClicked (MouseEvent e) {}
  public void mouseReleased (MouseEvent e) {}

}
