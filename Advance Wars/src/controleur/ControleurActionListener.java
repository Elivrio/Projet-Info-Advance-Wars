package src.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.vue.Vue;
import src.controleur.Controleur;

public class ControleurActionListener extends Controleur implements ActionListener {

  /**
   * @param vue La vue du jeu.
   */
  public ControleurActionListener (Vue vue) {
    super(vue);
  }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Est appelée quand on clique sur un bouton.
   * @param e Le clic sur le bouton transformé en variable par Java.
   */
  @Override
  public void actionPerformed (ActionEvent e) {
    Object source = e.getSource();
    // Si on clique sur le bouton pour changer de joueur.
    if (source == vue.getBoutonJoueur())
      vue.finTour();
    // Si on clique sur le bouton pour attaquer,
    else if (source == vue.getBoutonAttaque()) {
      // Si la portée était déjà affichée, on l'efface.
      if (map.getAttaque())
        map.setAttaque(false);
      // Sinon, on l'affiche.
      else map.setAttaque(true);
    }
    map.repaint();
  }
}
