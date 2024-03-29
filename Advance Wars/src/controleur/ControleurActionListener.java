package src.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.vue.Vue;
import src.controleur.Controleur;

public class ControleurActionListener extends Controleur implements ActionListener {

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param vue La vue du jeu.
   */
  public ControleurActionListener (Vue vue) {
    super(vue);
  }

  // ***************************************************
  // *************** Fonction d'instance ***************
  // ***************************************************

  /**
   * Est appelee quand on clique sur un bouton.
   * @param e Le clic sur le bouton transforme en variable par Java.
   */
  @Override
  public void actionPerformed (ActionEvent e) {
    Object source = e.getSource();
    // Si on clique sur le bouton pour changer de joueur.
    if (source == vue.getBoutonJoueur())
      vue.finTour();
    // Si on clique sur le bouton pour attaquer,
    else if (source == vue.getBoutonAttaque()) {
      // Si la portee etait deja affichee, on l'efface.
      if (map.getAttaque())
        map.setAttaque(false);
      // Sinon, on l'affiche.
      else map.setAttaque(true);
    }
    map.repaint();
  }
}
