package src.vue;

import java.awt.Font;
import java.awt.Color;
import java.lang.Object;
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
import src.vue.PanelMenu;
import src.modele.Joueur;
import src.modele.Plateau;
import src.variable.MyColor;
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

@SuppressWarnings("serial")
public class Menu extends JFrame {

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  // Le JPanel qui sert de fond d'écran dans le menu.
  private PanelMenu fond;

  // Le listener qui gère les actions de la souris dans le menu.
  private MenuMouseListener mML;

  // Le listener qui gère les actions générales dans le menu : boutons, JComboBox, etc.
  private MenuActionListener mAL;

  // Le JPanel qui correspond à la partie inférieure du menu.
  private JPanel bas = new JPanel();

  // Le JPanel qui correspond à la partie centrale du menu.
  private JPanel centre = new JPanel();

  // La JComboBox qui permet de choisir le nombre de joueurs souhaité dans une partie.
  private JComboBox<Integer> choixNbJoueurs;

  // Le JButton permettant de lancer la partie.
  private JButton boutonGo = new JButton("C'est parti !");

  // Le JLabel qui s'affiche dans la partie centrale supérieure du menu.
  private JLabel milieuLabel = new JLabel("Combien de joueurs voulez-vous ?");

  // Une LinkedList des zones de textes qui permettent d'écrire le nom du joueur souhaité.
  private LinkedList<JTextField> zoneNoms = new LinkedList<JTextField>();

  // Une LinkedList des zones de choix permettant au joueur de choisir son Général.
  private LinkedList<JComboBox<String>> choixGeneral = new LinkedList<JComboBox<String>>();

  // Une LinkedList des zones de choix permettant au joueur de choisir sa couleur.
  private LinkedList<ComboColorChooser<MyColor>> choixCouleur = new LinkedList<ComboColorChooser<MyColor>>();

  // ***************************************************
  // *************** Variables de classe ***************
  // ***************************************************

  // Cette couleur permet de rendre le fond d'un JPanel transparent et donc de voir l'image de fond de menu.
  private final static Color transparent = new Color(128, 128, 128, 0);

  // Contient toutes les couleurs accessibles au Joueur pour le jeu.
  private final static LinkedList<MyColor> basicTab = new LinkedList<MyColor>();

  // Les différents généraux disponibles permettant de lancer le jeu.
  private final static String[] generaux = {"Ninja", "Nosaure", "MadZombie", "MagicalGirl"};


  static {
    /*
    RED       : java.awt.Color[r=255, g=0,   b=0]
    GREEN     : java.awt.Color[r=0,   g=255, b=0]
    BLUE      : java.awt.Color[r=0,   g=0,   b=255]
    YELLOW    : java.awt.Color[r=255, g=255, b=0]
    MAGENTA   : java.awt.Color[r=255, g=0,   b=255]
    CYAN      : java.awt.Color[r=0,   g=255, b=255]
    WHITE     : java.awt.Color[r=255, g=255, b=255]
    BLACK     : java.awt.Color[r=0,   g=0,   b=0]
    GRAY      : java.awt.Color[r=128, g=128, b=128]
    LIGHT_GRAY: java.awt.Color[r=192, g=192, b=192]
    DARK_GRAY : java.awt.Color[r=64,  g=64,  b=64]
    PINK      : java.awt.Color[r=255, g=175, b=175]
    ORANGE    : java.awt.Color[r=255, g=200, b=0]
    */

    basicTab.add(new MyColor(Color.RED.getRGB()/*, 50*/, "Rouge"));
    basicTab.add(new MyColor(Color.GREEN.getRGB()/*, 50*/, "Vert"));
    basicTab.add(new MyColor(Color.BLUE.getRGB()/*, 50*/, "Bleu"));
    basicTab.add(new MyColor(Color.YELLOW.getRGB()/*, 50*/, "Jaune"));
    basicTab.add(new MyColor(Color.MAGENTA.getRGB()/*, 50*/, "Magenta"));
    basicTab.add(new MyColor(Color.CYAN.getRGB()/*, 50*/, "Cyan"));
    basicTab.add(new MyColor(Color.WHITE.getRGB()/*, 50*/, "Blanc"));
    basicTab.add(new MyColor(Color.BLACK.getRGB()/*, 50*/, "Noir"));
    basicTab.add(new MyColor(Color.GRAY.getRGB()/*, 50*/, "Gris"));
    basicTab.add(new MyColor(Color.PINK.getRGB()/*, 50*/, "Rose"));
    basicTab.add(new MyColor(Color.ORANGE.getRGB()/*, 50*/, "Orange"));
    basicTab.add(new MyColor(148, 33, 146/*, 50*/, "Violet"));
    basicTab.add(new MyColor(170, 121, 66/*, 50*/, "Brun"));
  }

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  public Menu() {

    // On définit le nom de la fenêtre, sa taille, puis on la centre sur l'écran de l'utilisateur.
    setTitle("Menu");
    setSize(600, 500);
    setLocationRelativeTo(null);

    // On initialise les listeners du menu.
    mML = new MenuMouseListener(this);
    mAL = new MenuActionListener(this);

    // Le nombre de joueur que l'on peut avoir sur la carte, le format peut-être amener à changer car peu modulable.
    Integer[] nbJoueurs = {2, 3, 4};
    // On initialise la liste de sélection permettant de choisir le nombre de joueurs.
    choixNbJoueurs = new JComboBox<Integer>(nbJoueurs);
    choixNbJoueurs.setSelectedItem(4);
    choixNbJoueurs.setPreferredSize(new Dimension(100, 20));
    // On lui ajoute le listener d'action du menu.
    choixNbJoueurs.addActionListener(mAL);

    // Initialisation du JPanel qui servira comme fond d'écran.
    fond = new PanelMenu();

    // La répartion sur le Panel de fond se fait par zone (haut, milieu et centre).
    fond.setLayout(new BorderLayout());

    // Le JLabel qui sera affiché en haut du menu et qui ne bouge pas.
    JLabel haut = new JLabel("Advance Wars");
    haut.setFont(haut.getFont().deriveFont(Font.BOLD, 48));
    haut.setHorizontalAlignment(JLabel.CENTER);

    fond.add(haut, BorderLayout.NORTH);

    // On initialise le paneau central et on l'ajoute au fond.
    milieuLabel.setFont(milieuLabel.getFont().deriveFont(Font.BOLD, 24));
    afficherChoixNoms();

    // Le bouton qui permet de lancer le jeu. Il est placé dans un Panel afin
    // d'afficher un décor transparent derrière lui.
    boutonGo.setFocusable(false);
    boutonGo.setPreferredSize(new Dimension(200, 40));
    boutonGo.addActionListener(mAL);
    bas.setBackground(transparent);
    bas.add(boutonGo);

    fond.add(bas, BorderLayout.SOUTH);

    // On indique le Panel que doit utiliser la Frame Menu est fond, puis on l'affiche.
    setContentPane(fond);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  // ***************************************
  // *************** Getters ***************
  // ***************************************

  public JButton getBoutonGo() { return boutonGo; }

  public LinkedList<JComboBox<String>> getChoixGeneral() { return choixGeneral; }

  public LinkedList<ComboColorChooser<MyColor>> getChoixCouleurs() { return choixCouleur; }

  public JComboBox<Integer> getChoixNbJoueurs() { return choixNbJoueurs; }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Initialise le premier JPanel du Panel central et l'ajoute à ce dernier.
   * Cette fonction permet de réduire la fonction afficherChoixNoms.
   */
  public void milieuHaut() {
    JPanel milieuHaut = new JPanel();
    milieuHaut.setBackground(transparent);
    milieuHaut.setLayout(new FlowLayout());
    milieuHaut.add(milieuLabel);
    milieuHaut.add(choixNbJoueurs);
    centre.add(milieuHaut);
  }

  /**
   * Fonction qui permet de mettre à jour le JPanel centre.
   */
  public void afficherChoixNoms() {
    // On retire tout ce qui se situe sur le panneau central.
    // Nécessaire pour que le Layout ne garde ne soit pas gardé en mémoire avec une mauvaise séparation.
    centre.removeAll();

    // On récupère le nombre de Joueur que l'on doit initialiser (donc afficher).
    int nbJoueurs = (int)choixNbJoueurs.getSelectedItem();

    // On initialise le Layout pour qu'on puisse avoir un nombre différent de joueur sur le Panel central.
    centre.setLayout(new GridLayout(nbJoueurs + 2, 1));

    // Pour qu'on puisse voir le fond.
    centre.setBackground(transparent);

    // On inclut la première partie du paneau central.
    milieuHaut();

    // Pour chaque joueur, on crée un JPanel dans lequel on ajoute un Label avec le numéro du joueur,
    // ensuite une zone de texte pour mettre le nom que l'on souhaite avoir durant la partie,
    // puis une liste de sélection permettant de choisir son général et enfin une liste de sélection
    // permettant de choisir la couleur de son choix.
    for (int i = 0; i < nbJoueurs; i++) {
      JPanel milieuCentre = new JPanel();
      JLabel pres = new JLabel("Joueur " + (i+1));

      zoneNoms.add(new JTextField("Joueur " + (i+1) ));
      zoneNoms.get(i).setPreferredSize(new Dimension(150, 30));
      zoneNoms.get(i).addMouseListener(mML);

      choixGeneral.add(new JComboBox<String>(generaux));
      choixGeneral.get(i).addActionListener(mAL);

      choixCouleur.add(new ComboColorChooser<MyColor>(basicTab));
      choixCouleur.get(i).setSelectedItem(basicTab.get(i));
      choixCouleur.get(i).addActionListener(mAL);

      milieuCentre.setBackground(transparent);
      milieuCentre.add(pres);
      milieuCentre.add(zoneNoms.get(i));
      milieuCentre.add(choixGeneral.get(i));
      milieuCentre.add(choixCouleur.get(i));

      // Le Panel en question est bien placé grâce au GridLayout.
      centre.add(milieuCentre);
    }
    // On ajoute le centre au menu puis on redessine ce dernier.
    fond.add(centre, BorderLayout.CENTER);
    fond.revalidate();
  }

  /**
   * Permet de récupérer les noms des joueurs dans un tableau.
   * @return Un tableau de chaînes de caractères correspondant aux noms que les joueurs ont choisi.
   */
  public String[] recupererNoms() {
    int nbJoueurs = (int)choixNbJoueurs.getSelectedItem();
    String[] noms = new String[nbJoueurs];
    for (int i = 0; i < nbJoueurs; i++)
      noms[i] = zoneNoms.get(i).getText();
    return noms;
  }

  /**
   * Permet de récupérer les généraux des joueurs dans un tableau afin de les initialiser plus tard.
   * @return Un tableau de chaînes de caractères correspondant aux noms des généraux que les joueurs ont choisi.
   */
  public String[] recupererGeneraux() {
    int nbJoueurs = (int)choixNbJoueurs.getSelectedItem();
    String[] generaux = new String[nbJoueurs];
    for (int i = 0; i < nbJoueurs; i++)
      generaux[i] = (String)choixGeneral.get(i).getSelectedItem();
    return generaux;
  }

  /**
   * Permet de récupérer les couleurs choisies par les joueurs dans un tableau.
   * @return Un tableau de couleurs correspondant aux couleurs que les joueurs ont choisi.
   */
  public MyColor[] recupererCouleur() {
    int nbJoueurs = (int)choixNbJoueurs.getSelectedItem();
    MyColor[] color = new MyColor[nbJoueurs];
    for (int i = 0; i < nbJoueurs; i++)
      color[i] = (MyColor)choixCouleur.get(i).getSelectedItem();
    return color;
    }

  /**
   * Permet de créer le tableau des Joueur qui joueront la partie.
   * @param  noms     Le tableau de noms correspondant aux choix des joueurs.
   * @param  x        La largeur du plateau
   * @param  y        La hauteur du plateau
   * @param  couleurs Le tableau de couleurs correspondant aux choix des joueurs.
   * @return          Les Joueurs qui joueront la partie.
   */
  public Joueur[] creationJoueurs (String[] noms, int x, int y, MyColor[] couleurs) {
    Joueur[] joueurs = new Joueur[noms.length];
    for (int i = 0; i < noms.length; i++){
      joueurs[i] = new Joueur(noms[i], x, y, couleurs[i]);
    }
    return joueurs;
  }

  /**
   * Permet de créer les généraux de chaque Joueur et de les renvoyer sous forme de liste.
   * @param  nomsGeneraux Les noms des généraux choisis par chaque Joueur.
   * @param  joueurs      Les Joueurs de la partie.
   * @return              Renvoie un tableau de général correspondant aux généraux choisis.
   * @throws Exception    Should Never Happen.
   */
  public General[] creationGeneraux (String[] nomsGeneraux, Joueur[] joueurs) throws Exception {
    // On crée un tableau de la longeur adéquate.
    General[] generaux = new General[nomsGeneraux.length];
    for (int i = 0; i < nomsGeneraux.length; i++)
      // Pour chaque nom de général, on crée un nouveau général et on lui donne son joueur en argument.
      switch (nomsGeneraux[i]) {
        case "Nosaure" : generaux[i] = new Nosaure(joueurs[i]); break;
        case "Ninja" : generaux[i] = new Ninja(joueurs[i]); break;
        case "MadZombie" : generaux[i] = new MadZombie(joueurs[i]); break;
        case "MagicalGirl" : generaux[i] = new MagicalGirl(joueurs[i]); break;
        default : throw new SNHException();
      }
    return generaux;
  }

  /**
   * Permet de lancer un Jeu avec les Joueurs que l'on a défini dans l'interface.
   * @throws Exception Should Never Happen.
   */
  public void lancerJeu () throws Exception {
    // Les spécificitées de la carte choisie, cette classe va construire le plateau à partir d'un fichier texte
    // et plusieurs arguments.
    CarteScanner carteScannee = new CarteScanner("src/variable/cartes/carteTest3.txt");

    // La hauteur (y) et la largeur (x) du plateau créé.
    int y = carteScannee.getLignes();
    int x = carteScannee.getColonnes();

    // On crée les joueurs
    Joueur[] joueurs = creationJoueurs(recupererNoms(), x + 2, y + 2, recupererCouleur());
    try {
      // On crée les généraux.
      General[] generaux = creationGeneraux(recupererGeneraux(), joueurs);

      // On crée le plateau.
      Plateau plateau = carteScannee.plateau(joueurs, generaux, y, x);

      // Et finalement, on lance le jeu.
      Jeu jeu = new Jeu(plateau);
    } catch (Exception evt) {
      // Simple sécurité, SNH.
      evt.printStackTrace();
      System.out.println(evt);
      System.exit(1);
    }
  }
}
