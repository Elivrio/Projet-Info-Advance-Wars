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

  // Contient la ville cliquée en ce moment.
  private AbstractVille ville;

  /**
   * @param vue     La vue du jeu.
   * @param ville   La ville cliquée.
   */
  public ActionVille (Vue vue, AbstractVille ville) {
    super(vue);
    this.ville = ville;
  }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Est appelée lors d'un clic sur le bouton "Créer une unité".
   * @param e Le clic sur le bouton tranformé en variable par Java.
   */
  @Override
  public void actionPerformed (ActionEvent e) {
    Object source = e.getSource();
    if (source == vue.getBoutonCreationUniteTerrestre())
      vue.afficherChoixUnites(map.getJoueur(), 0, ville);
    else if (source == vue.getBoutonCreationUniteMaritime())
      vue.afficherChoixUnites(map.getJoueur(), 1, ville);
    else if (source == vue.getBoutonCreationUniteAerienne())
      vue.afficherChoixUnites(map.getJoueur(), 2, ville);
    map.repaint();
  }

}
