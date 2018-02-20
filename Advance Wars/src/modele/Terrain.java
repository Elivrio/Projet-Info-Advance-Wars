package src.modele;

public class Terrain {

  private int type;
  private String nom;

  Terrain (int t) {
    type = t;
    switch (type) {
      case 0 : nom = "Forêt"; break;
      case 1 : nom = "Plaine"; break;
      case 2 : nom = "Mer"; break;
      case 3 : nom = "Montagne"; break;
      case 4 : nom = "Trou Noir"; break;
    }
  }

  public int getType() { return type; }
  public String getNom() { return nom; }
}
