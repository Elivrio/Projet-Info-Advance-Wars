package src.modele.terrain;

import src.modele.Joueur;
import src.modele.AbstractTerrain;

public abstract class AbstractVille extends AbstractTerrain {

  protected Joueur joueur;
  protected int x, y;
  protected boolean achete;

  public AbstractVille (String s, int n, int x, int y) {
    super(s, n);
    joueur = null;
    this.x = x;
    this.y = y;
  }

  public boolean getAchete() {
    return achete;
  }

  public void setAchete (boolean b) {
    achete = b;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Joueur getJoueur() {
    return joueur;
  }

  public void setJoueur (Joueur j) {
    joueur = j;
  }

}
