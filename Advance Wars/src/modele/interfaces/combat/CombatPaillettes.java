package src.modele.interfaces.combat;

import src.modele.interfaces.combat.Combat;

public class CombatPaillettes implements Combat {

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  // Le nombre de degats infliges par coup.
  protected int degats = 30;

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Description du type de combat.
   * @return Retourne l'arme utilisee.
   */
  public String combat() {
    return "Lance des paillettes";
  }

  /**
   * Points de degats infliges.
   * @return Retourne le nombre de points de degats infliges par coup.
   */
  public int getDegats() {
    return degats;
  }

}
