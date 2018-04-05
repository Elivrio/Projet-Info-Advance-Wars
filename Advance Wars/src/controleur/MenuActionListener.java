package src.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.vue.Menu;
import src.variable.MyColor;
import src.vue.ComboColorChooser;

public class MenuActionListener implements ActionListener {

  private Menu menu;

  public MenuActionListener (Menu m) {
    menu = m;
  }

  @Override
  public void actionPerformed (ActionEvent e) {
    menu.repaint();
    Object source = e.getSource();
    if (source == menu.getBoutonGo()) {
      try {
        menu.lancerJeu(menu.recupererNoms(), menu.recupererGeneraux());
        menu.dispose();
      } catch (Exception evt){
        evt.printStackTrace();
        System.out.println(evt);
        System.exit(1);
      }
    }
    else if (source == menu.getChoixNbJoueurs())
      menu.afficherChoixNoms();
    for (ComboColorChooser<MyColor> m : menu.getChoixCouleurs())
      if (source == m) {
        menu.estCouleurPossible(m.getSelectedItem());
      }
  }
}
