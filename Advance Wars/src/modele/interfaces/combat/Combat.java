package src.modele.interfaces.combat;

public interface Combat {

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Description du type de combat.
   * @return Retourne l'arme utilisee.
   */
  public String combat();

  /**
   * Points de degats infliges.
   * @return Retourne le nombre de points de degats infliges par coup.
   */
  public int getDegats();

}
