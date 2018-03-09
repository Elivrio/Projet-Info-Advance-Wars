package src.controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import src.Menu;

public class MenuKeyListener implements KeyListener {

  private Menu menu;

  public MenuKeyListener (Menu m) {
    menu = m;
  }

  public void keyPressed (KeyEvent e) {
    if (e.getKeyChar() == Event.ENTER) {
      menu.lancerJeu(menu.recupererNoms(), menu.recupererGeneraux());
      menu.dispose();
    }
  }

  public void keyReleased(KeyEvent e) {}
  public void keyTyped(KeyEvent evt) {}
}
