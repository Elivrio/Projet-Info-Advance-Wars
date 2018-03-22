package src.vue;

import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Graphics;
import javax.swing.JPanel;

public class PanelMenu extends JPanel {
  private Image fondMenu;

  public PanelMenu() {
    fondMenu = Toolkit.getDefaultToolkit().createImage("src/variable/images/fondMenu.png");
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
