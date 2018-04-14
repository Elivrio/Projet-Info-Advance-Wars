package src.vue;

import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Graphics;
import javax.swing.JPanel;

// Cette classe sert pour dessiner le fond du Menu, l'image violette.

@SuppressWarnings("serial")
public class PanelMenu extends JPanel {

  // ***************************************************
  // *************** Variable d'instance ***************
  // ***************************************************

  // permet de stocker efficacement l'image du fond de menu.
  private Image fondMenu;

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * Constructeur
   */
  public PanelMenu() {
    fondMenu = Toolkit.getDefaultToolkit().createImage("src/variable/images/fondMenu.png");
  }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Dessine le fond d'écran sur le JPanel.
   * @param g L'environnement Graphics fourni par java.
   */
  @Override
  protected void paintComponent(Graphics g) {

    // Test de sécurité.
    if (fondMenu != null) {
      // Objet permettant de manipuler les coins d'un objet de type Container.
      Insets insets = getInsets();

      // Crée des variable afin de dimensionner l'image dans le JPanel ciblé.
      int width = getWidth() - 1 - (insets.left + insets.right);
      int height = getHeight() - 1 - (insets.top + insets.bottom);
      int x = (width - fondMenu.getWidth(this)) / 2;
      int y = (height - fondMenu.getHeight(this)) / 2;

      // On dessine l'image sur le JPanel.
      g.drawImage(fondMenu, x, y, this);
    }
  }
}
