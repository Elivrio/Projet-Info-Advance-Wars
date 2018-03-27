package src.modele;

import java.awt.Color;
import java.util.LinkedList;

import src.variable.MyColor;
import src.modele.AbstractUnite;
import src.modele.terrain.Foret;
import src.modele.terrain.Montagne;

public class Joueur {
  /*
  0 --> Pas de vision (noir)
  1 --> Dévoilée mais pas visible (gris)
  2 --> Vision directe
  */

  private final String nom;
  private LinkedList<AbstractUnite> unites;
  private int[][] vision;
  private int argent;
  private MyColor couleur;

  public Joueur (String n, int x, int y, MyColor c) {
    nom = n;
    unites = new LinkedList<AbstractUnite>();
    vision = new int[y][x];
    couleur = c;
    argent = 10000;
  }

  public String getNom() { return nom; }
  public LinkedList<AbstractUnite> getUnites() { return unites; }
  public int getNbUnites() { return unites.size(); }
  public int[][] getVision() { return vision; }
  public int getArgent() { return argent; }
  public Color getColor() { return couleur; }

  public boolean possede (AbstractUnite u) {
    return unites.contains(u);
  }

  public void setArgent (int n) {
    argent += n;
  }

  public void remove (AbstractUnite u) {
    unites.remove(u);
  }

  public void add (AbstractUnite u, boolean b) {
    if (b)
      argent -= u.getCout();
    unites.add(u);
  }

  public void reset() {
    for (int i = 0; i < unites.size(); i++)
      unites.get(i).reset();
  }

  public void vision (AbstractTerrain[][] terrain) {
    for (int i = 0; i < vision.length; i++)
      for (int j = 0; j < vision[0].length; j++)
        if (vision[i][j] == 2 || vision[i][j] == 1)
          vision[i][j] = 1;

    for (int i = 0; i < unites.size(); i++) {
      AbstractUnite unite = unites.get(i);
      vision(0, unite, unite.getX(), unite.getY(), terrain, unite.getVision());
    }
  }

  public void vision (int indice, AbstractUnite unite, int x, int y, AbstractTerrain[][] terrain, int n) {
    vision[y][x] = 2;
    if (terrain[y][x] instanceof Montagne)
      n += 2;
    if (indice < n)
      for (int i = -1; i <= 1; i++)
        for (int j = -1; j <= 1; j++)
          if (i != j && i != -j
          && y+i >= 1 && y+i < vision.length - 1
          && x+j >= 1 && x+j < vision[0].length - 1) {
            vision[y+i][x+j] = 2;
            if ((!(terrain[y+i][x+j] instanceof Foret) ||
                terrain[y][x] instanceof Foret) &&
                (!(terrain[y+i][x+j] instanceof Montagne) ||
                terrain[y][x] instanceof Montagne))
                vision(indice+1, unite, x+j, y+i, terrain, n);
          }
  }
}
