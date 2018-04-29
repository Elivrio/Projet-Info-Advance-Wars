package src.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.vue.Vue;
import src.controleur.Controleur;
import src.modele.terrain.AbstractVille;

public class ActionVille extends Controleur implements ActionListener {

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  // Contient la ville cliquee en ce moment.
  private AbstractVille ville;

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param vue     La vue du jeu.
   * @param ville   La ville cliquee.
   */
  public ActionVille (Vue vue, AbstractVille ville) {
    super(vue);
    this.ville = ville;
  }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Est appelee lors d'un clic sur le bouton "Creer une unite".
   * @param e Le clic sur le bouton tranforme en variable par Java.
   */
  @Override
  public void actionPerformed (ActionEvent e) {
    Object source = e.getSource();
    if (source == vue.getBoutonCreationUniteTerrestre()) {
      System.out.println("Terre");
      vue.afficherChoixUnites(map.getJoueur(), 0, ville);
      System.out.println("Terre 2");
    }
    else if (source == vue.getBoutonCreationUniteMaritime()) {
      System.out.println("Mer");
      vue.afficherChoixUnites(map.getJoueur(), 1, ville);
      System.out.println("Mer 2");
    }
    else if (source == vue.getBoutonCreationUniteAerienne()) {
      System.out.println("Air");
      vue.afficherChoixUnites(map.getJoueur(), 2, ville);
      System.out.println("Air 2");
    }
    map.repaint();
  }

}
