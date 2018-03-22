package src.vue;

import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.LinkedList;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.JTextField;

import src.vue.Jeu;
import src.vue.Vue;
import src.vue.MyColor;
import src.vue.PanelMap;
import src.vue.PanelMenu;
import src.modele.Joueur;
import src.modele.Plateau;
import src.modele.CarteScanner;
import src.modele.general.Ninja;
import src.variable.SNHException;
import src.vue.ComboColorChooser;
import src.modele.general.General;
import src.modele.general.Nosaure;
import src.modele.general.MadZombie;
import src.modele.general.MagicalGirl;
import src.controleur.MenuMouseListener;
import src.controleur.MenuActionListener;

public class Menu extends JFrame {

  private Jeu jeu;
  private MenuMouseListener mML;
  private MenuActionListener mAL;
  private JPanel midCenter = new JPanel();
  private PanelMenu background;
  private JPanel bot = new JPanel();
  private JComboBox<Integer> choixNbJoueurs;
  private JButton boutonGo = new JButton("C'est parti !");
  private JLabel midLab = new JLabel("Combien de joueurs voulez-vous ?");
  private LinkedList<JTextField> fieldNoms = new LinkedList<JTextField>();
  private LinkedList<JComboBox<String>> choixGeneral = new LinkedList<JComboBox<String>>();

  private final static Color transparent = new Color(157, 144, 199, 0);
  private final static MyColor[] basicTab = new MyColor[3];

  static {
    basicTab[0] = new MyColor(Color.RED.getRGB(), "Rouge");
    basicTab[1] = new MyColor(Color.BLUE.getRGB(), "Bleu");
    basicTab[2] = new MyColor(156, 58, 97, "Horrible");
  }

  public Menu() {

    Dimension dimensionEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    int largeurEcran = (int)dimensionEcran.getWidth();
    int hauteurEcran = (int)dimensionEcran.getHeight();

    setTitle("Menu");
    setSize(600, 500);
    setLocationRelativeTo(null);

    mML = new MenuMouseListener(this);
    mAL = new MenuActionListener(this);

    Integer[] nbJoueurs = {2, 3, 4};
    choixNbJoueurs = new JComboBox<Integer>(nbJoueurs);
    choixNbJoueurs.setSelectedItem(4);
    choixNbJoueurs.setPreferredSize(new Dimension(100, 20));
    choixNbJoueurs.addActionListener(mAL);

    background = new PanelMenu();

    background.setLayout(new BorderLayout());

    JLabel top = new JLabel("Advance Wars");
    top.setFont(top.getFont().deriveFont(Font.BOLD, 48));
    top.setHorizontalAlignment(JLabel.CENTER);
    background.add(top, BorderLayout.NORTH);

    midCenter.setPreferredSize(new Dimension(largeurEcran*50/100,hauteurEcran*50/100));

    midLab.setFont(midLab.getFont().deriveFont(Font.BOLD, 24));
    afficherChoixNoms();

    background.add(midCenter, BorderLayout.CENTER);

    boutonGo.setFocusable(false);
    boutonGo.setPreferredSize(new Dimension(200, 40));
    boutonGo.addActionListener(mAL);
    bot.setBackground(transparent);
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
    background.repaint();
    midCenter.removeAll();
    int nbJoueurs = (int)choixNbJoueurs.getSelectedItem();
    midCenter = new JPanel();
    midCenter.setLayout(new GridLayout(nbJoueurs + 2, 1));
    midCenter.setBackground(transparent);
    JPanel chJou = new JPanel();
    chJou.setBackground(transparent);
    chJou.setLayout(new FlowLayout());
    chJou.add(midLab);
    chJou.add(choixNbJoueurs);
    midCenter.add(chJou);
    for (int i = 0; i < nbJoueurs; i++) {
      JPanel pan = new JPanel();
      JLabel pres = new JLabel("Joueur " + (i+1));

      fieldNoms.add(new JTextField("Nom du joueur"));
      fieldNoms.get(i).setPreferredSize(new Dimension(150, 30));
      fieldNoms.get(i).addMouseListener(mML);

      String[] generaux = {"Ninja", "Nosaure", "MadZombie", "MagicalGirl"};
      choixGeneral.add(new JComboBox<String>(generaux));
      choixGeneral.get(i).addActionListener(mAL);

      ComboColorChooser chCou = new ComboColorChooser();

      pan.setBackground(transparent);
      pan.add(pres);
      pan.add(fieldNoms.get(i));
      pan.add(choixGeneral.get(i));
      pan.add(chCou);
      midCenter.add(pan);
    }
    midCenter.setBackground(new Color(100, 100, 100, 0));
    background.add(midCenter, BorderLayout.CENTER);
    background.revalidate();
    background.repaint();

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

  public General[] creationGeneraux (General[] generaux, String[] noms, Joueur[] joueurs) throws Exception {
    for (int i = 0; i < noms.length; i++)
      switch (noms[i]) {
        case "Nosaure" : generaux[i] = new Nosaure(joueurs[i], 0, 0); break;
        case "Ninja" : generaux[i] = new Ninja(joueurs[i], 0, 0); break;
        case "MadZombie" : generaux[i] = new MadZombie(joueurs[i], 0, 0); break;
        case "MagicalGirl" : generaux[i] = new MagicalGirl(joueurs[i], 0, 0); break;
        default : throw new SNHException();
      }
    return generaux;
  }

  public void lancerJeu (String[] noms, String[] nomsGeneraux) throws Exception {
    CarteScanner test = new CarteScanner("src/variable/cartes/carteTest3.txt");
    int y = test.getLignes() + 2;
    int x = test.getColonnes() + 2;
    Joueur[] joueurs = creationJoueurs(noms, x, y);
    General[] generaux = new General[nomsGeneraux.length];
    try {
      generaux = creationGeneraux(generaux, nomsGeneraux, joueurs);
    } catch (Exception evt) {
      evt.printStackTrace();
      System.out.println(evt);
      System.exit(1);
    }
    Plateau p = test.plateau(joueurs, generaux, y-2, x-2);
    jeu = new Jeu(p);
  }

  public static void main (String[] args) {
    Menu menu = new Menu();
  }

}
