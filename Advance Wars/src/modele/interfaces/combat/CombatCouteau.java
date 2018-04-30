package src.modele.interfaces.combat;

import src.modele.interfaces.combat.Combat;

public class CombatCouteau implements Combat {

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  // Le nombre de degats infliges par coup.
  protected int degats = 5;

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Description du type de combat.
   * @return Retourne l'arme utilisee.
   */
  public String combat() {
    return "Se bat au couteau";
  }

  /**
   * Points de degats infliges.
   * @return Retourne le nombre de points de degats infliges par coup.
   */
  public int getDegats() {
    return degats;
  }

}
