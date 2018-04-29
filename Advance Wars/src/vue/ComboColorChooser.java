package src.vue;

import java.awt.Color;
import java.util.Vector;
import javax.swing.JList;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JLabel;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;
import java.awt.image.BufferedImage;
import javax.swing.DefaultComboBoxModel;

import src.variable.MyColor;

// Une classe qui permet de creer les listes de selection dans le menu afin de choisir la Couleur.
// La tâche est majoritairement effectuee par setRenderer() qui permet de definir le contenu de la JComboBox
// et qui dessine un carre de couleur dans chaque case, accompagne du nom de la couleur.

@SuppressWarnings("serial")
public class ComboColorChooser<Color> extends JComboBox<Color> {

  // ***************************************************
  // *************** Variable d'instance ***************
  // ***************************************************

  // Les couleurs qui vont etre affichees dans la JComboBox creee.
  // On utilise un Objet Vector pour empecher des casts improques et effacer des warnings.
  private Vector<Color> colors;

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param c La liste de couleurs qui s'affichera dans la Combobox choisie.
   */
  public ComboColorChooser(LinkedList<Color> c) {
    colors= new Vector<Color>();
    for (Color color : c) {
      colors.add(color);
    }
    initializeCombo();
  }

  /**
   * Permet d'initialiser de façon propre le contenu de la JComboBox de couleurs.
   */
  private void initializeCombo() {
    // Cree un modele par default pour une JComboBox et l'applique a la Combobox que l'on est en train de creer.
    DefaultComboBoxModel<Color> model = new DefaultComboBoxModel<Color>(colors);
    setModel(model);

    // Le renderer va prendre chaque element du modele et appliquer a chaque cellule la fonction getListCellRendererComponent
    // On va de cette façon dessiner dans les cellules de la Combobox.
    setRenderer(new ListCellRenderer<Color>() {
      public Component getListCellRendererComponent(JList<? extends Color> list, Color value, int index, boolean isSelected, boolean cellHasFocus) {
        MyColor color = (MyColor)value;

        // On cree un image et on dessine un rectangle de la couleur souhaitee dessus.
        BufferedImage img = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.setColor(color);
        g.fillRect(0, 0, 20, 20);

        // On ajoute l'image coloree et le nom de la couleur dans un JLabel.
        ImageIcon icon = new ImageIcon(img);
        JLabel label = new JLabel(color.getColorName(), icon, JLabel.LEADING);

        // On renvoie le JLabel pour qu'il soit le Component de la cellule cible.
        return label;
      }
    });
  }
}
