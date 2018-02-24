package src;

import javax.swing.*;
import java.awt.*;
import src.modele.*;

public class Menu extends JFrame {

  private JPanel container = new JPanel();
  private JComboBox<Integer> combo;
  private JLabel label = new JLabel("Combien de joueurs voulez-vous ?");
  private JButton bouton = new JButton("C'est parti !");

  public Menu() {
    setTitle("Menu");
    setSize(600, 800);

    container.setBackground(Color.gray);
    container.setLayout(new BorderLayout());

    Integer[] nbJoueurs = {2, 3, 4};
    combo = new JComboBox<Integer>(nbJoueurs);
    combo.setPreferredSize(new Dimension(100, 20));

    JPanel top = new JPanel();
    top.add(new JLabel("Advance Wars"));
    container.add(top, BorderLayout.NORTH);

    JPanel mid = new JPanel();
    mid.add(label);
    mid.add(combo);
    container.add(mid, BorderLayout.CENTER);

    JPanel bottom = new JPanel();
    bottom.add(bouton);
    bottom.setLocation(300, 385);
    container.add(bottom, BorderLayout.SOUTH);

    setContentPane(container);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /*public static String menu() {
    String[] nbJoueurs = {"2", "3", "4"};
    JOptionPane menu = new JOptionPane();
    String nom = (String)menu.showInputDialog(null,
      "Combien de joueurs y a-t-il ?",
      "Menu",
      JOptionPane.QUESTION_MESSAGE,
      null, nbJoueurs, nbJoueurs[0]);
      return nom;
  }*/

  public static void main (String[] args) {
    Menu menu = new Menu();
	}

}
