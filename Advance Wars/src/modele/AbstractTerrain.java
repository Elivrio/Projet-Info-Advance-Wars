package src.modele;

public abstract class AbstractTerrain {

  protected final String nom;
  protected final int type;

  public AbstractTerrain (String s, int t) {
    nom = s;
    type = t;
  }

  public String getNom() {
    return nom;
  }

  public int getType() {
    return type;
  }

  @Override
  public String toString() {
    return ("" + type);
  }
}
