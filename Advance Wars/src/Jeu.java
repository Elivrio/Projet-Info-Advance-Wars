package src;

import javax.swing.*;

import src.controleur.Controleur;
import src.controleur.ControleurKey;
import src.controleur.ControleurMouseMotion;
import src.controleur.ControleurMouse;
import src.modele.Plateau;
import src.vue.Vue;
import src.vue.PanelMap;
import src.modele.CarteScanner;

public class Jeu {
  private Vue v;
  private Plateau p;
  private PanelMap map;
  private ControleurKey cK;
  private ControleurMouse cM;
  private ControleurMouseMotion cMM;

  public Jeu (Plateau plat) {
    p = plat;
    map = new PanelMap(p);
    v = new Vue(map);
    cK = new ControleurKey(v);
    cM = new ControleurMouse(v);
    cMM = new ControleurMouseMotion(v);
    map.addKeyListener(cK);
    map.addMouseListener(cM);
    map.addMouseMotionListener(cMM);
  }

  public static void main(String[] args) {
    CarteScanner test = new CarteScanner("src/variable/cartes/carteTest2.txt");
    Plateau p = test.plateau();
    Jeu jeu = new Jeu (p);
	}
}
