package src;

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;
import src.modele.*;

public class PanelMenu extends JPanel {

    public PanelMenu() {
      setLayout(new BorderLayout());
      setVisible(true);
    }

    public void paint(Graphics g) { // ÇA FAIT TOUT BUGUER
      Image fondMenu = Toolkit.getDefaultToolkit().createImage("src/variable/images/fondMenu.png");
      g.drawImage(fondMenu, 0, 0, this);
    }

}
