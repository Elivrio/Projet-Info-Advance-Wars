package src.modele.general;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Terrestre;
import src.modele.interfaces.combat.CombatCouteau;
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
    super("Ninja", 10, new CombatCouteau(), new DeplaceAPied(), 5, 5, 100, new Terrestre(), j, x, y, 2);
  }

  /**
   * @param j Joueur possedant l'unite.
   */
  public Ninja(Joueur j) {
    this(j, 0, 0);
  }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  public void bonusPersonel() {}
}
