package src.modele;

import java.util.Random;
import java.util.*;

public class Plateau {
  private int largeur, hauteur;
  private Terrain[][] terrain;
  private Unite[][] unites;
  private Joueur[] joueurs;

  /*
    1 --> Plaine
    3 --> Montagne
    2 --> Eau
    0 --> Foret


    Route, Colline, Pont, etc.
  */

  public Plateau (int[][] carte, int nbJoueurs) {
    largeur = carte[1].length;
    hauteur = carte.length;
    terrain = new Terrain[hauteur][largeur];

    unites = new Unite[hauteur][largeur];
    joueurs = new Joueur[nbJoueurs];
    initJoueurs(2);

    for (int i=0; i<carte.length; i++)
      for (int j=0; j<carte[1].length; j++)
        terrain[i][j] = new Terrain(carte[i][j]);

  }


    public void initJoueurs (int nbJoueurs) {
      String[] nomsJoueurs = {"Boulet", "Artiste"};
      for (int i = 0; i < nbJoueurs; i++) {
        Joueur j = new Joueur(nomsJoueurs[i], largeur, hauteur);
        joueurs[i] = j;
        initUnite(j, i, 2);
      }
    }

  public void initUnite (Joueur joueur, int nJoueur, int nbUnites) {
    Random rand = new Random();
    int x;
    if (nJoueur == 0)
      x = 1;
    else x = 3;
    while (x <= nbUnites + ((nJoueur == 0)? 0 : 2)) {
      int i = rand.nextInt(hauteur-2);
      int j = rand.nextInt(largeur-2);
      if (unites[i+1][j+1] == null) {
        Unite unite = new Unite(x, j+1, i+1, joueur);
        joueur.add(unite);
        unites[i+1][j+1] = unite;
        System.out.println(i + " " + j);
        x++;
      }
    }
  }

  public int getHauteur() { return hauteur; }
  public int getLargeur() { return largeur; }
  public Unite[][] getUnites() { return unites; }
  public Terrain[][] getTerrain() { return terrain; }
  public Joueur[] getJoueurs() { return joueurs; }

}
