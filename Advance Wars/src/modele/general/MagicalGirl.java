package src.modele.general;

import src.modele.Joueur;
import src.modele.AbstractUnite;
import src.modele.interfaces.typeunite.Terrestre;
import src.modele.interfaces.combat.CombatPaillettes;
import src.modele.interfaces.deplacement.DeplaceAPied;

public class MagicalGirl extends General {

  // *********************************************
  // *************** Constructeurs ***************
  // *********************************************

  /**
   * @param j Joueur possedant l'unite.
   * @param x Position de l'unite en abscisse.
   * @param y Position de l'unite en ordonnee.
   */
  public MagicalGirl (Joueur j, int x, int y) {
    super("Magical Girl", 100, new CombatPaillettes(), new DeplaceAPied(), 3, 3, 5, new Terrestre(), j, x, y, 4);
  }

  /**
   * @param j Joueur possedant l'unite.
   */
  public MagicalGirl (Joueur j) {
    this(j, 0, 0);
  }
}
