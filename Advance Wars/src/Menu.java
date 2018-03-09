package src;

import java.awt.*;
import javax.swing.*;
import java.util.LinkedList;

import src.Jeu;
import src.vue.Vue;
import src.vue.PanelMap;
import src.modele.Joueur;
import src.modele.Plateau;
import src.modele.CarteScanner;
import src.modele.general.Ninja;
import src.modele.general.General;
import src.modele.general.Nosaure;
import src.modele.general.MadZombie;
import src.modele.general.MagicalGirl;
import src.controleur.MenuKeyListener;
import src.controleur.MenuMouseListener;
import src.controleur.MenuActionListener;

public class Menu extends JFrame {

  private JPanel background = new JPanel();
  private JComboBox<Integer> choixNbJoueurs;
  private JLabel label = new JLabel("Combien de joueurs voulez-vous ?");
  private JPanel midBottom = new JPanel();
  private JButton boutonGo = new JButton("C'est parti !");
  private MenuActionListener mAL;
  private MenuMouseListener mML;
  private MenuKeyListener mKL;
  private LinkedList<JTextField> fieldNoms = new LinkedList<JTextField>();
  private LinkedList<JComboBox<String>> choixGeneral = new LinkedList<JComboBox<String>>();

  public Menu() {

    setTitle("Menu");
    setSize(1600, 1200);

    mAL = new MenuActionListener(this);
    mML = new MenuMouseListener(this);
    mKL = new MenuKeyListener(this);
    addKeyListener(mKL);

    Integer[] nbJoueurs = {2, 3, 4};
    choixNbJoueurs = new JComboBox<Integer>(nbJoueurs);
    choixNbJoueurs.setPreferredSize(new Dimension(100, 20));
    choixNbJoueurs.addActionListener(mAL);

    background.setLayout(new BorderLayout());

    JPanel top = new JPanel();
    top.add(new JLabel("Advance Wars"));

    background.add(top, BorderLayout.NORTH);

    JPanel mid = new JPanel();
    JPanel midTop = new JPanel();
    midTop.add(label);
    midTop.add(choixNbJoueurs);
    afficherChoixNoms();
    mid.add(midTop);
    mid.add(midBottom);

    background.add(mid, BorderLayout.CENTER);

    boutonGo.setFocusable(false);
    boutonGo.addActionListener(mAL);

    JPanel bottom = new JPanel();
    bottom.add(boutonGo);
    bottom.setLocation(300, 385);
    background.add(bottom, BorderLayout.SOUTH);

    setContentPane(background);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /*public static String menu() {
    String[] nbJoueurs = {"2", "3", "4"};
    JOptionPane menu = new JOptionPane();
    String nom = (String)menu.showInputDialog(null,
    "Combien de joueurs y a-t-il ?",
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
      fieldNoms.get(i).addMouseListener(mML);
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

  public Joueur[] creationJoueurs (String[] noms, int x, int y) {
    Joueur[] joueurs = new Joueur[noms.length];
    for (int i = 0; i < noms.length; i++)
      joueurs[i] = new Joueur(noms[i], x, y);
    return joueurs;
  }

  public General[] creationGeneraux (String[] noms, Joueur[] joueurs) {
    General[] generaux = new General[noms.length];
    for (int i = 0; i < noms.length; i++)
      switch (noms[i]) {
        case "Nosaure" : generaux[i] = new Nosaure(joueurs[i], 0, 0); break;
        case "Ninja" : generaux[i] = new Ninja(joueurs[i], 0, 0); break;
        case "MadZombie" : generaux[i] = new MadZombie(joueurs[i], 0, 0); break;
        case "MagicalGirl" : generaux[i] = new MagicalGirl(joueurs[i], 0, 0); break;
      }
    return generaux;
  }

  public void lancerJeu (String[] noms, String[] nomsGeneraux) {
    CarteScanner test = new CarteScanner("src/variable/cartes/carteTest2.txt");
    int x = test.nbrColonnes()+2;
    int y = test.nbrLignes()+2;
    Joueur[] joueurs = creationJoueurs(noms, x, y);
    General[] generaux = creationGeneraux(nomsGeneraux, joueurs);
    Plateau p = test.plateau(generaux.length, generaux);
    Jeu jeu = new Jeu(p);
  }

  public static void main (String[] args) {
    Menu menu = new Menu();
  }

}
