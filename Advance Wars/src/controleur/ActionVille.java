package src.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.vue.Vue;
import src.controleur.Controleur;
import src.modele.terrain.AbstractVille;

public class ActionVille extends Controleur implements ActionListener {

  private AbstractVille ville;

  public ActionVille (Vue v, AbstractVille vi) {
    super(v);
    ville = vi;
  }

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
