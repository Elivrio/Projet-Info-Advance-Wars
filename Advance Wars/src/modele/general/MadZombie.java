package src.modele.general;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Terrestre;
import src.modele.interfaces.combat.CombatCerveau;
import src.modele.interfaces.deplacement.DeplaceAPied;

public class MadZombie extends General {

  // *********************************************
  // *************** Constructeurs ***************
  // *********************************************

  /**
   * @param j Joueur possedant l'unite.
   * @param x Position de l'unite en abscisse.
   * @param y Position de l'unite en ordonnee.
   */
  public MadZombie (Joueur j, int x, int y) {
    super("MadZombie", 100, new CombatCerveau(), new DeplaceAPied(), 5, 1, 2, 100, new Terrestre(), j, x, y, 1);
  }

  /**
   * @param j Joueur possedant l'unite.
   */
  public MadZombie (Joueur j) {
    this(j, 0, 0);
  }
}
