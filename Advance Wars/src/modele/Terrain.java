package src.modele;

public class Terrain {

  private int type;
  private String nom;

  Terrain (int t) {
    type = t;
    if (type == 0)
      nom = "Forêt";
    else if (type == 1)
      nom = "Plaine";
    else if (type == 2)
      nom = "Eau";
    else if (type == 3)
      nom = "Montagne";
    else nom = "Trou noir";
  }

  public int getType() { return type; }
  public String getNom() { return nom; }
}
