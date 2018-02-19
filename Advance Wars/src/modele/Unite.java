package src.modele;

public class Unite {
  private int type;
  private int pv;
  private int vision;
  private int distance;
  private String nom;

  Unite (int t) {
    type = t;
    if (t == 1) {
      nom = "Zombie";
      pv = 36;
      vision = 2;
      distance = 3;
    }
    else if (t == 2) {
      nom = "Ninja";
      pv = 42;
      vision = 4;
      distance = 5;
    }
    else if (t == 3) {
      nom = "Nosaure";
      pv = 73;
      vision = 7;
      distance = 2;
    }
    else if (t == 4) {
      nom = "Magical Girl";
      pv = 66;
      vision = 5;
      distance = 4;
    }
  }

  public int getDistance() { return distance; }
  public int getVision() { return vision; }
  public String getNom() { return nom; }
  public int getType() { return type; }
  public int getPV() { return pv; }
}
