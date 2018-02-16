package src;

import src.controleur.Controleur;
import src.controleur.ControleurKey;
import src.controleur.ControleurMouseMotion;
import src.controleur.ControleurMouse;
import src.modele.Plateau;
import src.vue.Vue;
import src.modele.CarteScanner;

public class Jeu {
  private ControleurKey cK;
  private ControleurMouse cM;
  private ControleurMouseMotion cMM;
  private Vue v;
  private Plateau p;

  public Jeu(Plateau plat) {
      p = plat;
      v = new Vue(p);
      cK = new ControleurKey(v);
      cM = new ControleurMouse(v);
      cMM = new ControleurMouseMotion(v);
      // cM.initialiserMouseListener(v);         On l'aura au besoin, mais normalement c'est inutile
      v.addMouseMotionListener(cMM);
      v.addMouseListener(cM);
      v.addKeyListener(cK);
      v.setVisible(true);
  }

  public Jeu(int a, int b) {
    p = new Plateau(a,b);
    v = new Vue(p);
    cK = new ControleurKey(v);
    cM = new ControleurMouse(v);
    cMM = new ControleurMouseMotion(v);
    // cM.initialiserMouseListener(v);         On l'aura au besoin, mais normalement c'est inutile
    v.addMouseMotionListener(cMM);
    v.addKeyListener(cK);
    v.setVisible(true);
  }

  public static void main(String[] args) {
    //System.out.println((float)(80/100));
    CarteScanner test = new CarteScanner("src/variable/cartes/carteTest.txt");
    Plateau p = test.plateau();
    Jeu jeu2 = new Jeu(p);
    //Jeu jeu = new Jeu(10,10);
	}
}
