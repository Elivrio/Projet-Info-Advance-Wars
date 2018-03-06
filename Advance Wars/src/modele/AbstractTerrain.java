package src.modele;

public abstract class AbstractTerrain {

  protected final String nom;

  public AbstractTerrain (String s) {
    nom = s;
  }

  public String getNom() {
    return nom;
  }
}
