package src;

import javax.swing.*;

import src.controleur.Controleur;
import src.controleur.ControleurKey;
import src.controleur.ControleurMouseMotion;
import src.controleur.ControleurMouse;
import src.controleur.ControleurActionListener;
import src.modele.Plateau;
import src.vue.Vue;
import src.vue.PanelMap;
import src.modele.CarteScanner;
import src.modele.Joueur;
import src.modele.general.General;
import src.modele.general.Nosaure;
import src.modele.general.Ninja;
import src.modele.general.MadZombie;
import src.modele.general.MagicalGirl;

public class Jeu {
  private Vue v;
  private Plateau p;
  private PanelMap map;
  private ControleurKey cK;
  private ControleurMouse cM;
  private ControleurMouseMotion cMM;
  private ControleurActionListener cAL;

  public Jeu (Plateau plat) {
    p = plat;
    map = new PanelMap(p);
    v = new Vue(map);
    cK = new ControleurKey(v);
    cM = new ControleurMouse(v);
    cMM = new ControleurMouseMotion(v);
    cAL = new ControleurActionListener(v);
    map.addKeyListener(cK);
    map.addMouseListener(cM);
    map.addMouseMotionListener(cMM);
    v.getBoutonJoueur().addActionListener(cAL);
    v.getBoutonAttaque().addActionListener(cAL);
  }

  public static void main(String[] args) {
    CarteScanner test = new CarteScanner("src/variable/cartes/carteTest2.txt");
    int x = test.nbrColonnes()+2;
    int y = test.nbrLignes()+2;
    Joueur joueur1 = new Joueur("Boulet", x, y);
    Joueur joueur2 = new Joueur("Artiste", x, y);
    Joueur joueur3 = new Joueur("Gros Boulet", x, y);
    Joueur joueur4 = new Joueur("Madame Paillettes", x, y);
    General[] generaux = {new Nosaure(joueur1, 0, 0), new Ninja(joueur2, 0, 0), new MadZombie(joueur3, 0, 0), new MagicalGirl(joueur4, 0, 0)};
    Plateau p = test.plateau(4, generaux);
    Jeu jeu = new Jeu (p);
	}
}
