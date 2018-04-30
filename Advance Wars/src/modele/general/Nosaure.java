package src.modele.general;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Terrestre;
import src.modele.interfaces.combat.CombatRawr;
import src.modele.interfaces.deplacement.DeplaceAPied;

public class Nosaure extends General {

  // *********************************************
  // *************** Constructeurs ***************
  // *********************************************

  /**
   * @param j Joueur possedant l'unite.
   * @param x Position de l'unite en abscisse.
   * @param y Position de l'unite en ordonnee.
   */
  public Nosaure (Joueur j, int x, int y) {
    super("Nosaure", 100, new CombatRawr(), new DeplaceAPied(), 2, 3, 7, 100, new Terrestre(), j, x, y, 3);
  }

  /**
   * @param j Joueur possedant l'unite.
   */
  public Nosaure(Joueur j) {
    this(j, 0, 0);
  }
}
