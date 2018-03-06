package src.modele;

import java.util.Random;
import java.util.*;
import src.modele.general.General;
import src.modele.general.MadZombie;
import src.modele.general.Nosaure;
import src.modele.general.Ninja;
import src.modele.general.MagicalGirl;
import src.modele.terrain.Eau;
import src.modele.terrain.Foret;
import src.modele.terrain.Montagne;
import src.modele.terrain.Plaine;
import src.modele.terrain.TrouNoir;
import src.modele.terrain.Ville;

public class Plateau {
  private int largeur, hauteur;
  private AbstractTerrain[][] terrain;
  private AbstractUnite[][] unites;
  private Joueur[] joueurs;

  /*
    1 --> Plaine
    3 --> Montagne
    2 --> Eau
    0 --> Foret


    Route, Colline, Pont, etc.
  */

  public Plateau (int[][] carte, General[] generaux) {
    largeur = carte[1].length;
    hauteur = carte.length;
    terrain = new AbstractTerrain[hauteur][largeur];

    unites = new AbstractUnite[hauteur][largeur];
    joueurs = new Joueur[generaux.length];
    initJoueurs(generaux);

    for (int i=0; i<carte.length; i++)
      for (int j=0; j<carte[1].length; j++)
        //terrain[i][j] = new Terrain(carte[i][j]);
        switch (carte[i][j]) {
          case 0 : terrain[i][j] = new Foret(); break;
          case 1 : terrain[i][j] = new Plaine(); break;
          case 2 : terrain[i][j] = new Eau(); break;
          case 3 : terrain[i][j] = new Montagne(); break;
          case 4 : terrain[i][j] = new TrouNoir(); break;
          case 5 : terrain[i][j] = new Ville(); break;
        }

  }

  public void initJoueurs (General[] generaux) {
    for (int i = 0; i < generaux.length; i++) {
      joueurs[i] = generaux[i].getJoueur();
      initUnite(joueurs[i], generaux[i]);
    }
  }

  public void initUnite (Joueur joueur, General general) {
    Random rand = new Random();
    int x = 0;
    while (x < 1) {
      int i = rand.nextInt(hauteur-2);
      int j = rand.nextInt(largeur-2);
      if (unites[i+1][j+1] == null) {
        general.setCase(j+1, i+1);
        joueur.add(general);
        unites[i+1][j+1] = general;
        System.out.println(i + " " + j);
        x++;
      }
    }
  }

  public int getHauteur() { return hauteur; }
  public int getLargeur() { return largeur; }
  public AbstractUnite[][] getUnites() { return unites; }
  public AbstractTerrain[][] getTerrain() { return terrain; }
  public Joueur[] getJoueurs() { return joueurs; }

  public void rmvUnite (AbstractUnite u) {
    unites[u.getY()][u.getX()] = null;
  }

  public void setUnites (int ancienX, int ancienY, int x, int y) {
    AbstractUnite u = unites[ancienY][ancienX];
    unites[ancienY][ancienX] = null;
    unites[y][x] = u;
  }

}
