package src.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.vue.Menu;
import src.variable.MyColor;
import src.vue.ComboColorChooser;

public class MenuActionListener implements ActionListener {

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  // Le menu de demarrage du jeu.
  private Menu menu;

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param menu Le menu du jeu.
   */
  public MenuActionListener (Menu menu) {
    this.menu = menu;
  }

  /**
   * Est appelee lors d'un clic sur un bouton du menu.
   * @param e [description]
   */
  @Override
  public void actionPerformed (ActionEvent e) {
    menu.repaint();
    Object source = e.getSource();
    // Si on clique sur le bouton "C'est parti !", on lance le jeu.
    if (source == menu.getBoutonGo()) {
      try {
        menu.lancerJeu();
        menu.dispose();
      } catch (Exception evt){
        evt.printStackTrace();
        System.out.println(evt);
        System.exit(1);
      }
    }
    // Si on choisit un nombre de joueurs, on affiche les choix en consequence.
    else if (source == menu.getChoixNbJoueurs())
      menu.afficherChoixNoms();
    /*for (ComboColorChooser<MyColor> m : menu.getChoixCouleurs())
      if (source == m) {
        menu.estCouleurPossible(m.getSelectedItem());
      }*/
  }
}
