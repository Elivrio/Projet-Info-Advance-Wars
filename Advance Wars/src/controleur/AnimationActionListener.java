package src.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.vue.Vue;
import src.modele.AbstractUnite;

public class AnimationActionListener implements ActionListener {

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  // La vue du jeu.
  private Vue vue;


  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param vue La vue du jeu.
   */
  public AnimationActionListener (Vue vue) {
    this.vue = vue;
  }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Est appelee en continu par le timer.
   * @param e L'action donnee par Java.
   */
  public void actionPerformed (ActionEvent e) {
    vue.animationStatus(!vue.getAnimationStatus());
    vue.repaint();
    AbstractUnite unite = vue.getMap().getPion();
    //System.out.println("null ? " + (unite == null));
    if (unite != null) {
      //System.out.println("mouv ? " + (unite.getMouvement()));
      if (unite.getMouvement())
        vue.getMap().setBouge(true);
      else
        vue.getMap().setBouge(false);
    }
  }
}
