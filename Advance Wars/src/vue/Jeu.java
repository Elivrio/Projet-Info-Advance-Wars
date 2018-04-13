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
  private Vue vue;

  // Le plateau qui contient le terrain et les unités.
  private Plateau plateau;

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

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param plat Le plateau qui défini le jeu.
   */
  public Jeu (Plateau plateau) {
    this.plateau = plateau;
    map = new PanelMap(plateau, this);
    vue = new Vue(map);
    MiniMap miniMap = vue.getMiniMap();
    cK = new ControleurKey(vue);
    mMM = new MiniMapMouse(vue);
    cM = new ControleurMouse(vue);
    cMM = new ControleurMouseMotion(vue);
    map.addKeyListener(cK);
    map.addMouseListener(cM);
    miniMap.addMouseListener(mMM);
    map.addMouseMotionListener(cMM);
    map.addMouseMotionListener(cM);
  }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * On vérifie la mort de l'unité cible. Si cette unité est morte,
   * le joueur qui contrôle l'unité attaquant à l'origine de la mort gagne de l'argent
   * @param attaquant L'unité qui attaque.
   * @param cible     L'unité attaqué.
   */
  public void mort (AbstractUnite attaquant, AbstractUnite cible) {
    // Si la cible est morte,
    if (cible.getPV() <= 0) {

      // On retire l'unité partout.
      cible.getJoueur().remove(cible);
      map.rmvUnite(cible);

      // On augmente l'argent du joueur qui contrôle l'unité.
      attaquant.getJoueur().setArgent(cible.getGainMort());

      // On met à jour les informations du joueur qui vient de tuer une unité.
      vue.informations(map.getJoueur());
    }
  }

  /**
   * Permet de gérer la fin d'un tour de jeu.
   */
  public void finTour () {
    // On vérifie si les villes sur la carte change de propriétaire.
    villesPrises();

    // On change de joueur et on met la vue à jour
    vue.newTurn();
  }

  /**
   * Vérifie si des villes sont en prises par des joueurs et change la possession des villes si nécessaire.
   */
  public void villesPrises () {
    // L'ensemble des unités en jeu.
    AbstractUnite[][] unites = map.getUnites();
    // L'ensemble des villes du plateau.
    LinkedList<AbstractVille> villes = map.getVilles();

    // On prend les villes une par une.
    for (int i = 0; i < villes.size(); i++) {
      AbstractVille ville = villes.get(i);

      // On regarde l'unité sur la case de la ville.
      AbstractUnite unite = unites[ville.getY()][ville.getX()];

      // Si l'unité n'est pas nulle, on change le propriétaire de la ville.
      if (unite != null)
        ville.setJoueur(unite.getJoueur());
    }
  }

}
