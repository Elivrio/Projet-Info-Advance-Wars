package src.modele;

abstract class General extends Unite{


  public General(String s, int t, int x, int y) {
    super(s, t, x, y);
  }

  abstract bonusPersonel();
}
