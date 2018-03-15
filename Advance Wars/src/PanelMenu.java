package src;

import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;

public class PanelMenu extends JPanel {
  Image fondMenu;

  public PanelMenu() {
    fondMenu = Toolkit.getDefaultToolkit().createImage("src/variable/images/fondMenu.png");
  }

  @Override
  public Dimension getPreferredSize() {
    return ((fondMenu == null)? new Dimension(0, 0) : new Dimension(fondMenu.getWidth(this), fondMenu.getHeight(this)));
  }

  @Override
  protected void paintComponent(Graphics g) {

    if (fondMenu != null) {
      Insets insets = getInsets();

      int width = getWidth() - 1 - (insets.left + insets.right);
      int height = getHeight() - 1 - (insets.top + insets.bottom);

      int x = (width - fondMenu.getWidth(this)) / 2;
      int y = (height - fondMenu.getHeight(this)) / 2;

      g.drawImage(fondMenu, x, y, this);
    }

  }

}
