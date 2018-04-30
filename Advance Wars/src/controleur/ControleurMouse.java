package src.controleur;

import java.util.LinkedList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import src.vue.Vue;
import src.vue.PanelMap;
import src.variable.Variable;
import src.modele.AbstractUnite;
import src.modele.AbstractTerrain;
import src.modele.terrain.AbstractVille;

public class ControleurMouse extends Controleur implements MouseMotionListener, MouseListener {

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  // Booleen permettant de savoir si on se deplace.
  protected boolean deplacement;

  protected int[][] chemin;

  protected int distance;

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  public ControleurMouse (Vue v) {
    super(v);
    deplacement = false;
    distance = 0;
    chemin = null;
  }

  // ***************************************
  // *************** Getters ***************
  // ***************************************

  //calcul de la case cliquee
  public int getI (MouseEvent me, int taillePixel) {
    int y = me.getY() + map.getTabI()*100 + map.getPosI();
    return y/taillePixel;
  }

  public int getJ (MouseEvent me, int taillePixel) {
    int x = me.getX() + map.getTabJ()*100 + map.getPosJ();
    return x/taillePixel;
  }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  // Fonction appelee lorsqu'on clique sur une case du plateau
  @Override
  public void mouseClicked (MouseEvent me) {

    int taillePixel = map.getTaillePixel();
    int i = this.getI(me, taillePixel);
    int j = this.getJ(me, taillePixel);

    AbstractUnite unite = isUnite(i, j);
    AbstractTerrain terrain = isTerrain(i, j);
    AbstractVille ville = isVille(terrain, i, j);
    boolean attaque = false;

    AbstractUnite cliquee = map.getCliquee();
    deplacement = true;
    chemin(unite);

    // Si les deplacements sont affiches et qu'on clique sur une case cible possible, on y va
    if (cliquee != null
        && map.getJoueur().possede(cliquee)
        && (i - 1 >= 0)
        && (i < map.getTerrain().length)
        && (j - 1 >= 0)
        && (j < map.getTerrain()[0].length)) {  
          if (!map.getAttaque() && unite == null){
              if (cliquee.getDeplace() + Math.abs((j) - cliquee.getX()) + Math.abs((i) - cliquee.getY()) <= cliquee.getDistance()) {
                cliquee.setMouvement(true);
                vue.getMap().mouvement(cliquee, chemin);
              } 
              deplacement = false;
              distance = 0;
          }
          if (unite != null
              && !map.getJoueur().possede(unite)
              && (Math.abs((j) - cliquee.getX()) + Math.abs((i) - cliquee.getY()) <= cliquee.getPortee())
              && map.getAttaque()
              && cliquee.getAttaque()) {
                cliquee.attaquer(unite);
                vue.informationsCombat(cliquee, unite, cliquee.getDegats());
                attaque = true;
          }
          chemin = null;
    }
    

    if (!attaque) {
      map.setAttaque(false);
      map.setCliquee(unite);
      // Si la case possede une unite, on affiche ses caracteristiques
      if (unite != null)
        vue.informations(unite);
      // Sinon, on affiche les caracteristiques du terrain
      else if (ville != null)
        vue.informations(ville, map.getJoueur(), map.getJoueur().getVision()[i][j]);
      else
        vue.informations(terrain, map.getJoueur().getVision()[i][j]);
    }
    map.repaint();
    miniMap.repaint();
  }

  // creation de la taille possible du chemin
  public void chemin (AbstractUnite cliquee) {
    if (cliquee != null) {
      int tailleChemin = cliquee.getDistance()-cliquee.getDeplace();
      chemin = new int[tailleChemin+1][2];
    }
    else if (chemin == null) {
      chemin = new int[1][2];
      chemin[0][0] = 0;
      chemin[0][1] = 0;
    }
  }


  public AbstractVille isVille (AbstractTerrain terrain, int i, int j) {
    if (terrain instanceof AbstractVille) {
      LinkedList<AbstractVille> villes = map.getVilles();
      for (AbstractVille v : villes)
        if (v.getX() == j && v.getY() == i)
          return v;
    }
    return null;
  }

  public AbstractUnite isUnite (int i, int j) {
    return map.getUnites()[i][j];
  }

  public AbstractTerrain isTerrain (int i, int j) {
    return map.getTerrain()[i][j];
  }

  @Override
  public void mouseExited(MouseEvent me) {}
  public void mouseEntered(MouseEvent me) {}
  public void mouseReleased(MouseEvent me) {}
  public void mousePressed(MouseEvent me) {}

  @Override
  public void mouseMoved(MouseEvent me) {
    AbstractUnite cliquee = map.getCliquee();
    int taillePixel = map.getTaillePixel();

    // si on peut se deplacer
    if (deplacement && cliquee!=null && chemin !=null ) {
      int i = getI(me, taillePixel);
      int j = getJ(me, taillePixel);

      // si on a pas encore commence le deplacement
      if (chemin[0][0] == 0 && chemin[0][1] == 0) {
        chemin[0][0] = i;
        chemin[0][1] = j;
      }

      // si on veut reculer
      if (distance >= 1) {
          boolean b3 = chemin[distance - 1][0] == i;
          boolean b4 = chemin[distance - 1][1] == j;
          if (b3 && b4) {
            chemin[distance][0] =- 1;
            chemin[distance][1] =- 1;
            distance -= 1;
          }
        }
      // si on veut avancer
      if (distance <= chemin.length - 2 && distance >= 0){
        boolean b1 = chemin[distance][0] != i;
        boolean b2 = chemin[distance][1] != j;
        if (b1 || b2) {
          distance += 1;
          chemin[distance][0] = i;
          chemin[distance][1] = j;
        }
      }
      if (chemin != null && chemin.length>0)
        cliquee.setChemin(chemin); 
    }
  }

  @Override
  public void mouseDragged(MouseEvent me) {}
}
