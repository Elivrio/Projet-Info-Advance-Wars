package src.modele;

import java.util.*;
import src.modele.AbstractUnite;

public class Joueur {
  /*
  0 --> Pas de vision (noir)
  1 --> Dévoilée mais pas visible (gris)
  2 --> Vision directe
  */

  private final String nom;
  private LinkedList<AbstractUnite> unites;
  private int[][] vision;

  public Joueur (String n, int x, int y) {
    nom = n;
    unites = new LinkedList<AbstractUnite>();
    vision = new int[y][x];
  }

  public String getNom() { return nom; }
  public LinkedList<AbstractUnite> getUnites() { return unites; }
  public int getNbUnites() { return unites.size(); }
  public int[][] getVision() { return vision; }

  public boolean possede (AbstractUnite u) {
    return unites.contains(u);
  }

  public void remove (AbstractUnite u) {
    unites.remove(u);
  }

  public void add (AbstractUnite u) {
    unites.add(u);
  }

  public void reset() {
    for (int i = 0; i < unites.size(); i++) {
      unites.get(i).reset();
    }
  }

  public void vision() {
    for (int i = 0; i < vision.length; i++) {
      for (int j = 0; j < vision[0].length; j++)
        if (vision[i][j] == 2 || vision[i][j] == 1)
          vision[i][j] = 1;
    }

    for (int i = 0; i < unites.size(); i++) {
      AbstractUnite unite = unites.get(i);
      vision(0, unite, unite.getX(), unite.getY());
    }
  }

  public void vision (int indice, AbstractUnite unite, int x, int y) {
    if (indice < unite.getVision())
      for (int i = -1; i <= 1; i++)
        for (int j = -1; j <= 1; j++)
          if (i != j && i != -j
          && y+i >= 0 && y+i < vision.length
          && x+j >= 0 && x+j < vision[0].length) {
            vision[y+i][x+j] = 2;
            vision(indice+1, unite, x+j, y+i);
          }
  }

}
