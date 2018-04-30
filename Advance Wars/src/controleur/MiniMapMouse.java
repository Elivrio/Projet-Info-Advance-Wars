package src.controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import src.vue.Vue;

public class MiniMapMouse extends Controleur implements MouseListener {

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param vue La vue du jeu.
   */
  public MiniMapMouse (Vue vue) {
    super(vue);
  }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Est appelee lors d'un clic sur la mini map.
   * @param me Clic souris transforme en variable par Java.
   */
  @Override
  public void mouseClicked (MouseEvent me) {
    int taillePixel = miniMap.getTaillePixel();

    // On recupere la case sur laquelle on a clique
    int x = me.getX() - miniMap.getNoirGauche();
    int y = me.getY() - miniMap.getNoirHaut();
    int i = y/taillePixel;
    int j = x/taillePixel;

    // On regarde jusqu'ou peut aller le rectangle rouge en bas et a gauche.
    int iMin = (int)map.getHautMax() / 2;
    int jMin = (int)map.getLargMax() / 2;

    // On regarde jusqu'ou peut aller le rectangle rouge en haut et a droite.
    int iMax = map.getPlateau().getHauteur() - iMin;
    int jMax = map.getPlateau().getLargeur() - jMin;

    // Si on a clique sur une case dans l'intervalle possible,
    if (i >= iMin && i < iMax && j >= jMin && j < jMax) {
      // On deplace la camera,
      map.setTabI(i - iMin + 1);
      map.setTabJ(j - jMin + 1);
      miniMap.setTabI(i - (int)miniMap.getHautMax() / 2 + 1);
      miniMap.setTabJ(j - (int)miniMap.getLargMax() / 2 + 1);

      // Et on repeint.
      map.revalidate();
      map.repaint();
      miniMap.repaint();
    }
  }

  // Fonctions de l'interface non utilisees.
  @Override
  public void mouseExited(MouseEvent me) {}
  public void mouseEntered(MouseEvent me) {}
  public void mouseReleased(MouseEvent me) {}
  public void mousePressed(MouseEvent me) {}

}
