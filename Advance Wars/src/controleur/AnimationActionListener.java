package src.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.vue.Vue;

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
  }

}
