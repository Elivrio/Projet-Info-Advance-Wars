package src.modele;

import java.util.Random;

public class Plateau {
  private int largeur, hauteur;
  private int[][] terrain;
  private Unite[][] unites;

  /*
    1 --> Plaine
    3 --> Montagne
    2 --> Eau
    0 --> Foret


    Route, Colline, Pont, etc.
  */

  public Plateau (int l, int h) {
    hauteur = h; largeur = l;
    terrain = new int[l][h];
    unites = new Unite[l][h];
    this.initialiser();
  }

  public Plateau (int[][] carte) {
    largeur = carte[1].length;
    hauteur = carte.length;
    terrain = carte;
    initUnite();
  }

  public void initUnite() {
    Random rand = new Random();
    unites = new Unite[hauteur][largeur];
    int x = 1;
    while (x <= 2) {
      int i = rand.nextInt(hauteur-2);
      int j = rand.nextInt(largeur-2);
      if (unites[i+1][j+1] == null) {
        unites[i+1][j+1] = new Unite(x);
        System.out.println(i + " " + j);
        x++;
      }
    }
  }

  public int getHauteur() { return hauteur; }
  public int getLargeur() { return largeur; }
  public Unite[][] getUnites() { return unites; }
  public int[][] getTerrain() { return terrain; }

  void initialiser() {
    Random rand = new Random();
    int tmp = 0;
    for (int i = 0; i < hauteur ; i ++) {
      for (int j = 0; j < largeur; j++) {
        tmp = rand.nextInt(4);
        terrain[i][j] = tmp;
        System.out.print(tmp + " ");
      }
      System.out.println();
    }
    int i = rand.nextInt(hauteur);
    int j = rand.nextInt(largeur);
    unites[i][j] = new Unite(1);
    System.out.println(i + " " + j);
  }

}
