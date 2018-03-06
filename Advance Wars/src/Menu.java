package src;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import src.modele.*;
import src.controleur.MenuActionListener;

public class Menu extends JFrame {

  private JPanel container = new JPanel();
  private JComboBox<Integer> choixNbJoueurs;
  private JLabel label = new JLabel("Combien de joueurs voulez-vous ?");
  JPanel midBottom = new JPanel();
  private JButton boutonGo = new JButton("C'est parti !");
  private MenuActionListener mAL;
  private LinkedList<JTextField> fieldNoms = new LinkedList<JTextField>();
  private LinkedList<JComboBox<String>> choixGeneral = new LinkedList<JComboBox<String>>();

  public Menu() {
    setTitle("Menu");
    setSize(1500, 500);

    mAL = new MenuActionListener(this);

    container.setBackground(Color.gray);
    container.setLayout(new BorderLayout());

    Integer[] nbJoueurs = {2, 3, 4};
    choixNbJoueurs = new JComboBox<Integer>(nbJoueurs);
    choixNbJoueurs.setPreferredSize(new Dimension(100, 20));
    choixNbJoueurs.addActionListener(mAL);

    JPanel top = new JPanel();
    top.add(new JLabel("Advance Wars"));
    container.add(top, BorderLayout.NORTH);

    JPanel mid = new JPanel();
    JPanel midTop = new JPanel();
    midTop.add(label);
    midTop.add(choixNbJoueurs);
    mid.add(midTop);
    mid.add(midBottom);

    container.add(mid, BorderLayout.CENTER);

    boutonGo.setFocusable(false);
    boutonGo.addActionListener(mAL);

    JPanel bottom = new JPanel();
    bottom.add(boutonGo);
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

  public JButton getBoutonGo() {
    return boutonGo;
  }

  public JComboBox<Integer> getChoixNbJoueurs() {
    return choixNbJoueurs;
  }

  public void afficherChoixNoms() {
    midBottom.removeAll();
    int nbJoueurs = (int)choixNbJoueurs.getSelectedItem();

    for (int i = 0; i < nbJoueurs; i++) {
      JLabel pres = new JLabel("Joueur " + (i+1));
      fieldNoms.add(new JTextField("Nom du joueur"));
      fieldNoms.get(i).setPreferredSize(new Dimension(150, 30));
      String[] generaux = {"Nosaure", "Ninja", "MadZombie", "MagicalGirl"};
      choixGeneral.add(new JComboBox<String>(generaux));
      choixGeneral.get(i).addActionListener(mAL);
      JPanel pan = new JPanel();
      pan.add(pres);
      pan.add(fieldNoms.get(i));
      pan.add(choixGeneral.get(i));
      midBottom.add(pan);
    }
    midBottom.revalidate();
  }

  public String[] recupererNoms() {
    int nbJoueurs = (int)choixNbJoueurs.getSelectedItem();
    String[] noms = new String[nbJoueurs];
    for (int i = 0; i < nbJoueurs; i++)
      noms[i] = fieldNoms.get(i).getText();
    return noms;
  }

  public String[] recupererGeneraux() {
    int nbJoueurs = (int)choixNbJoueurs.getSelectedItem();
    String[] generaux = new String[nbJoueurs];
    for (int i = 0; i < nbJoueurs; i++)
      generaux[i] = (String)choixGeneral.get(i).getSelectedItem();
    return generaux;
  }

  public LinkedList<JComboBox<String>> getChoixGeneral() {
    return choixGeneral;
  }

  public static void main (String[] args) {
    Menu menu = new Menu();
	}

}
