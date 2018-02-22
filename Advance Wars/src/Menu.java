package src;

import javax.swing.*;
import java.awt.*;
import src.modele.*;

public class Menu extends JFrame {

  public Menu() {
    setSize(600, 800);
    setTitle("Menu");

    setVisible(true);
  }

  public static String menu() {
    String[] nbJoueurs = {"2", "3", "4"};
    JOptionPane menu = new JOptionPane();
    String nom = (String)menu.showInputDialog(null,
      "Combien de joueurs y a-t-il ?",
      "Menu",
      JOptionPane.QUESTION_MESSAGE,
      null, nbJoueurs, nbJoueurs[0]);
      return nom;
  }

  public static void main (String[] args) {
    String nom = menu();
    CarteScanner test = new CarteScanner("src/variable/cartes/carteTest2.txt");
    Plateau p = test.plateau();
    Jeu jeu = new Jeu (p);
	}

}
