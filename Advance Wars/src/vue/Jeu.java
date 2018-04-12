package src.vue;

import java.util.LinkedList;

import src.vue.Vue;
import src.vue.MiniMap;
import src.vue.PanelMap;
import src.modele.Plateau;
import src.modele.AbstractUnite;
import src.controleur.MiniMapMouse;
import src.controleur.ControleurKey;
import src.controleur.ControleurMouse;
import src.modele.terrain.AbstractVille;
import src.controleur.ControleurMouseMotion;

public class Jeu {

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  // La JFrame qui va contenir toutes les données du jeu.
  private Vue v;

  // Le plateau qui contient le terrain et les unités.
  private Plateau p;

  // Le JPanel qui contient le dessin du terrain.
  private PanelMap map;

  // Le contrôleur qui va vérifier les actions sur le clavier sur la Vue.
  private ControleurKey cK;

  // Le contrôleur qui va vérifier les actions à la souris sur la MiniMap.
  private MiniMapMouse mMM;

  // Le contrôleur qui va vérifier les actions à la souris sur la Vue.
  private ControleurMouse cM;

  // Le contrôleur qui va vérifier les déplacements de la souris sur la Vue.
  private ControleurMouseMotion cMM;

  public Jeu (Plateau plat) {
    p = plat;
    map = new PanelMap(p, this);
    v = new Vue(map);
    MiniMap miniMap = v.getMiniMap();
    cK = new ControleurKey(v);
    mMM = new MiniMapMouse(v);
    cM = new ControleurMouse(v);
    cMM = new ControleurMouseMotion(v);
    map.addKeyListener(cK);
    map.addMouseListener(cM);
    miniMap.addMouseListener(mMM);
    map.addMouseMotionListener(cMM);
    map.addMouseMotionListener(cM);
  }

  // *****************************************
  // *************** Fonctions ***************
  // *****************************************

  // On vérifie la mort de l'unité cible. Si cette unité est morte,
  // le joueur qui contrôle l'unité attaquant à l'origine de la mort gagne de l'argent
  public void mort (AbstractUnite attaquant, AbstractUnite cible) {
    // Si la cible est morte,
    if (cible.getPV() <= 0) {

      // On retire l'unité partout.
      cible.getJoueur().remove(cible);
      map.rmvUnite(cible);

      // On augmente l'argent du joueur qui contrôle l'unité.
      attaquant.getJoueur().setArgent(cible.getGainMort());

      // On met à jour les informations du joueur qui vient de tuer une unité.
      v.informations(map.getJoueur());
    }
  }

  // Permet de gérer la fin d'un tour de jeu.
  public void finTour () {
    // On vérifie si les villes sur la carte change de propriétaire.
    villesPrises(map);

    // On change de joueur et on met la vue à jour
    v.newTurn();
  }

  public void villesPrises (PanelMap map) {
    AbstractUnite[][] unites = map.getUnites();
    LinkedList<AbstractVille> villes = map.getVilles();
    for (int i = 0; i < villes.size(); i++) {
      AbstractVille ville = villes.get(i);
      AbstractUnite unite = unites[ville.getY()][ville.getX()];
      if (unite != null)
        ville.setJoueur(unite.getJoueur());
    }
  }

}
