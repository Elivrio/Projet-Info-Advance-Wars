package src.vue;

import java.awt.Color;
import java.util.Vector;
import javax.swing.JList;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;
import java.awt.image.BufferedImage;
import javax.swing.DefaultComboBoxModel;

import src.variable.MyColor;

@SuppressWarnings("serial")
public class ComboColorChooser<Color> extends JComboBox<Color> {
  private Vector<Color> colors;

  public ComboColorChooser(LinkedList<Color> c) {
    colors= new Vector<Color>();
    for (Color color : c) {
      colors.add(color);
    }
    initializeCombo();
  }

  private void initializeCombo() {
    DefaultComboBoxModel<Color> model = new DefaultComboBoxModel<Color>(colors);
    setModel(model);
    setRenderer(new ListCellRenderer<Color>() {
      public Component getListCellRendererComponent(JList<? extends Color> list, Color value, int index, boolean isSelected, boolean cellHasFocus) {
        MyColor color = (MyColor)value;
        int w = 20;
        int h = 20;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.setColor(color);
        g.fillRect(0, 0, w, h);
        ImageIcon icon = new ImageIcon(img);
        JLabel label = new JLabel(color.getColorName(), icon, JLabel.LEADING);
        return label;
      }
    });
  }
}
