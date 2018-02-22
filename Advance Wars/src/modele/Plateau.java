package src.modele;

import java.util.Random;

public class Plateau {
  private int largeur, hauteur;
  private Terrain[][] terrain;
  private Unite[][] unites;
  private Joueur joueur;

  /*
    1 --> Plaine
    3 --> Montagne
    2 --> Eau
    0 --> Foret


    Route, Colline, Pont, etc.
  */

  public Plateau (int[][] carte) {
    largeur = carte[1].length;
    hauteur = carte.length;
    terrain = new Terrain[hauteur][largeur];
    joueur = new Joueur("Test", largeur, hauteur);
    for (int i=0; i<carte.length; i++)
      for (int j=0; j<carte[1].length; j++)
        terrain[i][j] = new Terrain(carte[i][j]);
    initUnite();
  }

  public void initUnite() {
    Random rand = new Random();
    unites = new Unite[hauteur][largeur];
    int x = 1;
    while (x <= 4) {
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
  public Joueur getJoueur() { return joueur; }

}
