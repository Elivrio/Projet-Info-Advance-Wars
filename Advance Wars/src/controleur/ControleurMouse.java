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

  protected boolean deplacement;
  protected int [][] chemin;
  protected int distance; 

  public ControleurMouse (Vue v) {
    super(v);
    deplacement = false;
    distance = 0;
    chemin = null;
  }

  //calcul de la case cliquée 
  public int getI (MouseEvent me, int taillePixel) {
    int y = me.getY() + map.getTabI()*100 + map.getPosI();
    return y/taillePixel;
  }

  public int getJ (MouseEvent me, int taillePixel) {
    int x = me.getX() + map.getTabJ()*100 + map.getPosJ();
    return x/taillePixel;
  }


  // Fonction appelée lorsqu'on clique sur une case du plateau
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

    chemin(unite);
    deplacement = true;
    
    // Si les déplacements sont affichés et qu'on clique sur une case cible possible, on y va
    if (cliquee != null
        && map.getJoueur().possede(cliquee)
        && (i-1 >= 0)
        && (i < map.getTerrain().length)
        && (j-1 >= 0)
        && (j < map.getTerrain()[0].length)) {
          if (!map.getAttaque() && unite == null){
              if (cliquee.getDeplace() + Math.abs((j) - cliquee.getX()) + Math.abs((i) - cliquee.getY()) <= cliquee.getDistance()) {
                for(int k=0; k<chemin.length; k++){
                    int x = chemin[k][0];
                    int y = chemin[k][1];
                    if ( k>=1 && map.getPlateau().getUnites()[x][y] != null)
                      break; 
                    if (x!=0 && y!=0) {
                      map.getPlateau().setUnites(cliquee.getX(), cliquee.getY(), y, x);
                      cliquee.setDeplace(Math.abs((y) - cliquee.getX()) + Math.abs((x) - cliquee.getY()));
                      cliquee.setCase(y, x);
                      map.getJoueur().vision(map.getTerrain());
                    }
                }
                chemin = null;
              }
              deplacement = false;
              distance = 0;
          }
          if (unite != null
              && !map.getJoueur().possede(unite)
              && (Math.abs((j) - cliquee.getX()) + Math.abs((i) - cliquee.getY()) <= cliquee.getPortee())
              && map.getAttaque()
              && cliquee.getAttaque() < 1) {
                cliquee.attaquer(unite);
                jeu.mort(cliquee, unite);
                vue.informations(cliquee, unite, cliquee.getDegats());
                attaque = true;
          }
    }
    if (!attaque) {
      map.setAttaque(false);
      map.setCliquee(unite);
      // Si la case possède une unité, on affiche ses caractéristiques
      if (unite != null)
        vue.informations(unite);
      // Sinon, on affiche les caractéristiques du terrain
      else if (ville != null)
        vue.informations(ville, map.getJoueur(), map.getJoueur().getVision()[i][j]);
      else
        vue.informations(terrain, map.getJoueur().getVision()[i][j]);
    }
    map.repaint();
    miniMap.repaint();
  }

  // creation de la taille possible du chemin 
  public void chemin(AbstractUnite cliquee){
    if (cliquee != null ){
      int tailleChemin = cliquee.getDistance();
      chemin = new int[tailleChemin+1][2];
    }
    else if(chemin == null) {
      chemin = new int[1][2];
      chemin[0][0] = 0;
      chemin[0][1] = 0;
    }
    //System.out.println("taille chemin possible "+chemin.length);
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
    if (deplacement && cliquee!=null) {
      int i = getI(me, taillePixel);
      int j = getJ(me, taillePixel);

      // si on a pas encore commencer le deplacement
      if (chemin[0][0] == 0 && chemin[0][1]== 0){
        chemin[0][0] = i;
        chemin[0][1] = j;
      }

      // si on veut reculer 
      if (distance >= 1){
          boolean b3 = chemin[distance-1][0]==i;
          boolean b4 = chemin[distance-1][1]==j;
          if (b3 && b4) {
            chemin[distance][0]=-1;
            chemin[distance][1]=-1;
            distance -=1;
          }
        }
      // si on veut avancer   
      if (distance <= chemin.length-2 && distance >= 0){
        boolean b1 = chemin[distance][0]!=i;
        boolean b2 = chemin[distance][1]!=j;
        if (b1|| b2) {
          distance +=1;
          chemin[distance][0] = i;
          chemin[distance][1] = j;
          
        }
      }
      //System.out.println("distance "+ distance );
    }
    
  }
  @Override
  public void mouseDragged(MouseEvent me) {}
}
