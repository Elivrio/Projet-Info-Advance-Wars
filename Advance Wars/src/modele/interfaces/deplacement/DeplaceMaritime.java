package src.modele.interfaces.deplacement;

import src.modele.interfaces.deplacement.Deplacement;

public class DeplaceMaritime implements Deplacement {

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Description du deplacement.
   * @return Retourne le type de deplacement.
   */
  public String deplacement() {
    return "Se deplace sur la mer";
  }

}
