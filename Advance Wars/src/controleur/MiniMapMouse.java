package src.controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import src.vue.Vue;

public class MiniMapMouse extends Controleur implements MouseListener {

  public MiniMapMouse (Vue v) {
    super(v);
  }

  @Override
  public void mouseClicked (MouseEvent me) {
    int taillePixel = miniMap.getTaillePixel();
    int x = me.getX() - miniMap.getNoirGauche();
    int y = me.getY() - miniMap.getNoirHaut();
    int i = y/taillePixel;
    int j = x/taillePixel;

    int iMin = (int)map.getHautMax() / 2;
    int jMin = (int)map.getLargMax() / 2;

    int iMax = map.getPlateau().getHauteur() - iMin;
    int jMax = map.getPlateau().getLargeur() - jMin;

    if (i >= iMin && i < iMax && j >= jMin && j < jMax) {
      map.setTabI(i - iMin + 1);
      map.setTabJ(j - jMin + 1);
      miniMap.setTabI(i - (int)miniMap.getHautMax() / 2 + 1);
      miniMap.setTabJ(j - (int)miniMap.getLargMax() / 2 + 1);

      map.revalidate();
      map.repaint();
      miniMap.repaint();
    }
  }

  @Override
  public void mouseExited(MouseEvent me) {}
  public void mouseEntered(MouseEvent me) {}
  public void mouseReleased(MouseEvent me) {}
  public void mousePressed(MouseEvent me) {}

}
