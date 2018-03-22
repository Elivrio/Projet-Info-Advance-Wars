package src.vue;

import java.util.LinkedList;

import src.vue.Vue;
import src.vue.MiniMap;
import src.vue.PanelMap;
import src.modele.Joueur;
import src.modele.Plateau;
import src.modele.CarteScanner;
import src.modele.general.Ninja;
import src.modele.AbstractUnite;
import src.controleur.Controleur;
import src.modele.general.Nosaure;
import src.modele.general.General;
import src.modele.general.MadZombie;
import src.controleur.ControleurKey;
import src.modele.general.MagicalGirl;
import src.controleur.ControleurMouse;
import src.modele.terrain.AbstractVille;
import src.controleur.ControleurMouseMotion;

public class Jeu {

  private Vue v;
  private Plateau p;
  private PanelMap map;
  private ControleurKey cK;
  private ControleurMouse cM;
  private ControleurMouseMotion cMM;

  public Jeu (Plateau plat) {
    p = plat;
    map = new PanelMap(p, this);
    v = new Vue(map);
    cK = new ControleurKey(v);
    cM = new ControleurMouse(v);
    cMM = new ControleurMouseMotion(v);
    map.addKeyListener(cK);
    map.addMouseListener(cM);
    map.addMouseMotionListener(cMM);
  }

  public void mort (AbstractUnite unite, AbstractUnite cible) {
    if (cible.getPV() <= 0) {
      cible.getJoueur().remove(cible);
      map.rmvUnite(cible);
        if (cible instanceof General)
          unite.getJoueur().setArgent(10000);
        else
          unite.getJoueur().setArgent(1000);
    }
  }

  public void finTour (PanelMap map, Vue vue, MiniMap miniMap) {
    villesPrises(map);
    map.setJoueur(1);
    map.setCliquee(null);
    miniMap.setJoueur(1);
    map.repaint();
    miniMap.repaint();
    vue.informations();
    vue.informations(map.getJoueur());
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
