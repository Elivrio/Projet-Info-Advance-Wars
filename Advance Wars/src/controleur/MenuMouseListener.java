package src.controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import src.Menu;

public class MenuMouseListener implements MouseListener {

  private Menu menu;

  public MenuMouseListener (Menu m) {
    menu = m;
  }

  public void mousePressed (MouseEvent e) {
    JTextField source = (JTextField) e.getSource();
    source.setText("");
  }

  public void mouseExited (MouseEvent e) {}
  public void mouseEntered (MouseEvent e) {}
  public void mouseClicked (MouseEvent e) {}
  public void mouseReleased (MouseEvent e) {}

}
