package src.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.vue.Vue;
import src.controleur.Controleur;

public class ControleurActionListener extends Controleur implements ActionListener {

  public ControleurActionListener (Vue v) {
    super(v);
  }

  public void actionPerformed (ActionEvent e) {
    Object source = e.getSource();
    // Si on clique sur le bouton pour changer de joueur
    if (source == vue.getBoutonJoueur())
      jeu.finTour(map, vue, miniMap);
    // Si on clique sur le bouton pour attaquer
    else if (source == vue.getBoutonAttaque()) {
      if (map.getAttaque())
      map.setAttaque(false);
      else map.setAttaque(true);
    }
    else if (source == vue.getBoutonCreationUniteTerrestre())
      vue.afficherChoixUnites(map.getJoueur(), 0);
    else if (source == vue.getBoutonCreationUniteMaritime())
      vue.afficherChoixUnites(map.getJoueur(), 1);
    else if (source == vue.getBoutonCreationUniteAerienne())
      vue.afficherChoixUnites(map.getJoueur(), 2);

    map.repaint();
  }
}
