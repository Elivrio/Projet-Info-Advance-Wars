package src.modele;

public class Unite {
  private int type;
  private int x;
  private int y;
  private int pv;
  private int vision;
  private int distance;
  private String nom;

  Unite (int t, int x, int y) {
    type = t;
    this.x = x;
    this.y = y;
    switch (type) {
      case 1 :
        nom = "Zombie";
        pv = 36;
        vision = 2;
        distance = 5;
        break;
      case 2 :
        nom = "Ninja";
        pv = 42;
        vision = 5;
        distance = 5;
        break;
      case 3 :
        nom = "Nosaure";
        pv = 73;
        vision = 7;
        distance = 2;
        break;
      case 4 :
        nom = "Magical Girl";
        pv = 66;
        vision = 5;
        distance = 4;
        break;
    }
  }

  public int getDistance() { return distance; }
  public int getVision() { return vision; }
  public String getNom() { return nom; }
  public int getType() { return type; }
  public int getPV() { return pv; }
  public int getX() { return x; }
  public int getY() { return y; }

  public void setCase (int x, int y) {
    this.x = x;
    this.y = y;
  }
}
