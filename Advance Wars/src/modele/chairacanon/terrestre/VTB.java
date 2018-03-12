package src.modele.chairacanon.terrestre;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Terrestre;
import src.modele.interfaces.deplacement.DeplaceAChenilles;

public class VTB extends AbstractUnite {

  public VTB (Joueur j, int x, int y) {
    super("VTB", 99, null, new DeplaceAChenilles(), 6, 0, 1, 70, 5000, new Terrestre(), j, x, y, 0);
  }
}
