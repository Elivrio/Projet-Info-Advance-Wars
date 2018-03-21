package src.vue;

import java.awt.Color;
import javax.swing.JList;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.ListCellRenderer;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;

import src.vue.MyColor;

public class ComboColorChooser extends JComboBox {
  private MyColor[] colors;

  public ComboColorChooser() {
    initializeCombo();
  }

  private void initializeCombo() {
    colors = new MyColor[3];
    colors[0] = new MyColor(Color.RED.getRGB(), "Rouge");
    colors[1] = new MyColor(Color.BLUE.getRGB(), "Bleu");
    colors[2] = new MyColor(156, 58, 97, "Horrible");
    DefaultComboBoxModel model = new DefaultComboBoxModel(colors);
    setModel(model);
    setRenderer(new ListCellRenderer() {
      public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        MyColor myColor = (MyColor) value;
        int w = 20;
        int h = 20;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.setColor(myColor);
        g.fillRect(0, 0, w, h);
        ImageIcon icon = new ImageIcon(img);
        JLabel label = new JLabel(myColor.getColorName(), icon, JLabel.LEADING);
        return label;
      }
    });
  }
}
