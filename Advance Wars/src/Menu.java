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

  private Jeu jeu;
  private MenuKeyListener mKL;
  private MenuMouseListener mML;
  private MenuActionListener mAL;
  private JPanel midLeft = new JPanel();
  private JPanel midCenter = new JPanel();
  private JPanel midRight = new JPanel();
  private JPanel background = new JPanel();
  private JPanel bot = new JPanel();
  private JComboBox<Integer> choixNbJoueurs;
  private JButton boutonGo = new JButton("C'est parti !");
  private JLabel label = new JLabel("Combien de joueurs voulez-vous ?");
  private JLabel midLab = new JLabel("Combien de joueurs voulez-vous ?");
  private LinkedList<JTextField> fieldNoms = new LinkedList<JTextField>();
  private LinkedList<JComboBox<String>> choixGeneral = new LinkedList<JComboBox<String>>();

  public Menu() {

    Dimension dimensionEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    int largeurEcran = (int)dimensionEcran.getWidth();
    int hauteurEcran = (int)dimensionEcran.getHeight();

    setTitle("Menu");
    setSize(largeurEcran, hauteurEcran);

    addKeyListener(mKL);
    mKL = new MenuKeyListener(this);
    mML = new MenuMouseListener(this);
    mAL = new MenuActionListener(this);

    Integer[] nbJoueurs = {2, 3, 4};
    choixNbJoueurs = new JComboBox<Integer>(nbJoueurs);
    choixNbJoueurs.setPreferredSize(new Dimension(100, 20));
    choixNbJoueurs.addActionListener(mAL);

    background = new PanelMenu();


    background.setLayout(new BorderLayout());

    JLabel top = new JLabel("Advance Wars");
    top.setFont(label.getFont().deriveFont(Font.BOLD, 48));
    top.setHorizontalAlignment(JLabel.CENTER);
    background.add(top, BorderLayout.NORTH);

    midCenter.setPreferredSize(new Dimension(largeurEcran*65/100,hauteurEcran*50/100));

    midLab.setFont(label.getFont().deriveFont(Font.BOLD, 24));
    midLab.setForeground(Color.YELLOW);
    afficherChoixNoms();

    background.add(midCenter, BorderLayout.CENTER);

    midLeft.setBackground(new Color(100, 100, 100, 0));
    midLeft.setPreferredSize(new Dimension(largeurEcran*25/100,hauteurEcran*50/100));
    background.add(midLeft, BorderLayout.WEST);

    midRight.setBackground(new Color(100, 100, 100, 0));
    midRight.setPreferredSize(new Dimension(largeurEcran*25/100,hauteurEcran*50/100));
    background.add(midRight, BorderLayout.EAST);

    boutonGo.setFocusable(false);
    boutonGo.setBackground(new Color(0, 0, 0, 0));
    boutonGo.addActionListener(mAL);
    bot.setBackground(new Color(0, 0, 0, 0));
    bot.add(boutonGo);
    background.add(bot, BorderLayout.SOUTH);

    //add(background);

    setContentPane(background);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public JButton getBoutonGo() {
    return boutonGo;
  }

  public JComboBox<Integer> getChoixNbJoueurs() {
    return choixNbJoueurs;
  }

  public void afficherChoixNoms() {
    int nbJoueurs = (int)choixNbJoueurs.getSelectedItem();
    midCenter = new JPanel();
    midCenter.setLayout(new GridLayout(nbJoueurs + 1, 1));
    midCenter.setBackground(new Color(0, 0, 0, 0));
    JPanel tmp = new JPanel();
    tmp.setBackground(new Color(0, 0, 0, 0));
    tmp.setLayout(new FlowLayout());
    tmp.add(midLab);
    tmp.add(choixNbJoueurs);
    midCenter.add(tmp);
    for (int i = 0; i < nbJoueurs; i++) {
      JPanel pan = new JPanel();
      JLabel pres = new JLabel("Joueur " + (i+1));
      fieldNoms.add(new JTextField("Nom du joueur"));
      fieldNoms.get(i).setPreferredSize(new Dimension(150, 30));
      fieldNoms.get(i).addMouseListener(mML);
      String[] generaux = {"Ninja", "Nosaure", "MadZombie", "MagicalGirl"};
      choixGeneral.add(new JComboBox<String>(generaux));
      choixGeneral.get(i).addActionListener(mAL);

      pan.setBackground(new Color(0, 0, 0, 0));
      pan.add(pres);
      pan.add(fieldNoms.get(i));
      pan.add(choixGeneral.get(i));
      midCenter.add(pan);
    }
    midCenter.setBackground(new Color(0, 0, 0, 0));
    background.add(midCenter, BorderLayout.CENTER);
    background.revalidate();
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
    CarteScanner test = new CarteScanner("src/variable/cartes/carteTest3.txt");
    int x = test.nbrColonnes()+2;
    int y = test.nbrLignes()+2;
    Joueur[] joueurs = creationJoueurs(noms, x, y);
    General[] generaux = creationGeneraux(nomsGeneraux, joueurs);
    Plateau p = test.plateau(generaux.length, generaux);
    jeu = new Jeu(p);
  }

  public static void main (String[] args) {
    Menu menu = new Menu();
  }

}
