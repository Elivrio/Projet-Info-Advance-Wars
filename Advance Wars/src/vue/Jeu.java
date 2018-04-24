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
    vue = new Vue(plateau);
    Map map = vue.getMap();
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

  // ***************************************
  // *************** Getters ***************
  // ***************************************

  public ControleurMouse getCM () {return cM;}
}
