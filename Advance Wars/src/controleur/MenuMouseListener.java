package src.controleur;

import javax.swing.JTextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import src.vue.Menu;

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
