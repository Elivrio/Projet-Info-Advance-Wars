package src.modele;

public class Unite {
  private int type;
  private int pv;
  private int vision;
  private String nom;

  Unite (int t) {
    type = t;
    if (t == 1) {
      nom = "Zombie";
      pv = 36;
      vision = 2;
    }
    else if (t == 2) {
      nom = "Ninja";
      pv = 42;
      vision = 4;
    }
  }

  public String getNom() { return nom; }
  public int getType() { return type; }
  public int getPV() { return pv; }
}
