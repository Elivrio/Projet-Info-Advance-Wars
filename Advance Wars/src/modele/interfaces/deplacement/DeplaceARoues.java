package src.modele.interfaces.deplacement;

import src.modele.interfaces.deplacement.Deplacement;

public class DeplaceARoues implements Deplacement {

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Description du deplacement.
   * @return Retourne le type de deplacement.
   */
  public String deplacement() {
    return "Se deplace avec des roues";
  }

}
