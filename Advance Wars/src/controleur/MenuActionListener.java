package src.controleur;

import java.awt.event.*;
import src.Menu;

public class MenuActionListener implements ActionListener {

  private Menu menu;

  public MenuActionListener (Menu m) {
    menu = m;
  }

  public void actionPerformed (ActionEvent e) {
    Object source = e.getSource();
    if (source == menu.getBoutonGo()) {
      menu.lancerJeu(menu.recupererNoms(), menu.recupererGeneraux());
      menu.dispose();
    }
    else if (source == menu.getChoixNbJoueurs())
      menu.afficherChoixNoms();
  }

}
