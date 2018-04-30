package src.modele.general;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Terrestre;
import src.modele.interfaces.combat.CombatSabre;
import src.modele.interfaces.deplacement.DeplaceAPied;

public class Ninja extends General {

  // *********************************************
  // *************** Constructeurs ***************
  // *********************************************

  /**
   * @param j Joueur possedant l'unite.
   * @param x Position de l'unite en abscisse.
   * @param y Position de l'unite en ordonnee.
   */
  public Ninja (Joueur j, int x, int y) {
    super("Ninja", 100, new CombatSabre(), new DeplaceAPied(), 5, 3, 5, new Terrestre(), j, x, y, 2);
  }

  /**
   * @param j Joueur possedant l'unite.
   */
  public Ninja(Joueur j) {
    this(j, 0, 0);
  }
}
