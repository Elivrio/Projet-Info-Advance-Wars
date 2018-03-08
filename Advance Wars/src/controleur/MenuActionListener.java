package src.controleur;

import java.awt.event.*;
import java.util.LinkedList;
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
import src.Menu;
import src.Jeu;

public class MenuActionListener implements ActionListener {

  private Menu menu;

  public MenuActionListener (Menu m) {
    menu = m;
  }

  public void actionPerformed (ActionEvent e) {
    Object source = e.getSource();
    if (source == menu.getBoutonGo()) {
      lancerJeu(menu.recupererNoms(), menu.recupererGeneraux());
      menu.dispose();
    }
    else if (source == menu.getChoixNbJoueurs())
      menu.afficherChoixNoms();
  }

  public Joueur[] creationJoueurs (String[] noms, int x, int y) {
    Joueur[] joueurs = new Joueur[noms.length];
    for (int i = 0; i < noms.length; i++)
      joueurs[i] = new Joueur(noms[i], x, y);
    return joueurs;
  }

  public General[] creationGeneraux (String[] noms, Joueur[] joueurs) {
    General[] generaux = new General[noms.length];
    for (int i = 0; i < noms.length; i++)
      switch (noms[i]) {
        case "Nosaure" : generaux[i] = new Nosaure(joueurs[i], 0, 0); break;
        case "Ninja" : generaux[i] = new Ninja(joueurs[i], 0, 0); break;
        case "MadZombie" : generaux[i] = new MadZombie(joueurs[i], 0, 0); break;
        case "MagicalGirl" : generaux[i] = new MagicalGirl(joueurs[i], 0, 0); break;
      }
    return generaux;
  }

  public void lancerJeu(String[] noms, String[] nomsGeneraux) {
    CarteScanner test = new CarteScanner("src/variable/cartes/carteTest2.txt");
    int x = test.nbrColonnes()+2;
    int y = test.nbrLignes()+2;
    Joueur[] joueurs = creationJoueurs(noms, x, y);
    General[] generaux = creationGeneraux(nomsGeneraux, joueurs);
    Plateau p = test.plateau(generaux.length, generaux);
    Jeu jeu = new Jeu(p);
  }

}
