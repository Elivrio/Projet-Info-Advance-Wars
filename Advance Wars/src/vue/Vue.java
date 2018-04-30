package src.vue;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.util.LinkedList;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JOptionPane;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.BadLocationException;

import src.vue.MiniMap;
import src.vue.PanelMap;
import src.modele.Joueur;
import src.modele.Plateau;
import src.variable.Variable;
import src.modele.terrain.Port;
import src.modele.terrain.Usine;
import src.modele.AbstractUnite;
import src.controleur.MouseIcone;
import src.modele.general.General;
import src.modele.AbstractTerrain;
import src.controleur.ActionVille;
import src.modele.terrain.Aeroport;
import src.modele.terrain.AbstractVille;
import src.controleur.AnimationActionListener;
import src.controleur.ControleurActionListener;

@SuppressWarnings("serial")
public class Vue extends JFrame {

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  // Variable permettant de connaître le type d'unite que l'on est en train
  // de creer avec une listener, a savoir terrestre (0), maritime(1) ou aerienne(2).
  private int typeUnites;

  // Le listener qui gere les animatations dans la vue.
  private AnimationActionListener aAL;

  // Le listener qui gere les actions dans la vue..
  private ControleurActionListener cAL;

  // Les JButtons qui apparaissent dans la vue.
  // Ils doivent faire parti des variables d'instance afin d'etre recuperable dans le contrôleur.
  private JButton boutonAttaque = new JButton("Attaquer");
  private JButton boutonJoueur = new JButton("Changer de joueur");
  private JButton boutonCreationUniteMaritime = new JButton("Creer une unite maritime");
  private JButton boutonCreationUniteAerienne = new JButton("Creer une unite aerienne");
  private JButton boutonCreationUniteTerrestre = new JButton("Creer une unite terrestre");

  // Le JPanel que l'on ajoute lorsque le joueur souhaite creer une unite.
  private JPanel panelChoixUnites;

  // Les Panel qui permettent de separer la vue entre plateau de jeu, minimap et panneaux d'informations.
  private JSplitPane split1, split2, split3;

  // Les panneaux d'informations du jeu.
  private JTextPane textJoueur, textInfos;

  // Contient toutes les informations que l'on va ajouter dans panelChoixUnites afin de creer l'affichage.
  private LinkedList<JLabel> listeIcones = new LinkedList<JLabel>();

  // Le listener que l'on ajoute a panelChoixUnites pour creer les unites sur le plateau.
  private MouseIcone mI;

  // La miniMap affichee dans la vue.
  private MiniMap miniMap;

  // La map qui affiche le terrain.
  private PanelMap map;

  // Le timer pour permettre les animations dans la vue.
  private Timer timer;

  // **************************************************
  // *************** Variable de classe ***************
  // **************************************************

  private final static Color couleurFond = new Color(0.3f, 0.3f, 0.3f);

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param plateau Le Plateau qui definit le terrain.
   */
  public Vue (Plateau plateau) {
    // La dimension de l'ecran.
    Dimension dimensionEcran = Toolkit.getDefaultToolkit().getScreenSize();
    int largeurEcran = (int)dimensionEcran.getWidth();
    int hauteurEcran = (int)dimensionEcran.getHeight();

    // On definit la taille de la fenetre, son nom et la methode de fermuture.
    setSize(largeurEcran, hauteurEcran);
    setTitle("Advance Wars");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // La carte et la minimap que l'on affiche dans la vue.
    this.map = new PanelMap(plateau);
    this.miniMap = new MiniMap(plateau, 35 * hauteurEcran / 100, 25 * largeurEcran / 100);

    //
    GridLayout grid = new GridLayout(3, 4);
    panelChoixUnites = new JPanel(grid);

    boutonJoueur.setFocusable(false);
    boutonAttaque.setFocusable(false);
    boutonCreationUniteAerienne.setFocusable(false);
    boutonCreationUniteMaritime.setFocusable(false);
    boutonCreationUniteTerrestre.setFocusable(false);
    textJoueur = new JTextPane();
    textJoueur.setEditable(false);
    textJoueur.setBackground(couleurFond);
    informations(map.getJoueur());

    textInfos = new JTextPane();
    textInfos.setEditable(false);
    textInfos.setBackground(couleurFond);

    split1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, textJoueur, textInfos);
    split1.setDividerSize(2);
    split1.setEnabled(false);
    split1.setDividerLocation(20*hauteurEcran/100);

    split2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split1, miniMap);
    split2.setDividerSize(0);
    split2.setEnabled(false);
    split2.setDividerLocation(65*hauteurEcran/100);

    split3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, map, split2);
    split3.setDividerSize(0);
    split3.setEnabled(false);
    split3.setDividerLocation(75*largeurEcran/100);
    getContentPane().add(split3, BorderLayout.CENTER);

    setVisible(true);

    cAL = new ControleurActionListener(this);

    boutonJoueur.addActionListener(cAL);
    boutonAttaque.addActionListener(cAL);
    aAL = new AnimationActionListener(this);
    timer = new Timer(250, aAL);
    timer.start();
  }

  // ***************************************
  // *************** Getters ***************
  // ***************************************

  public boolean getAnimationStatus() { return map.getAnimation(); }

  public int getTypeUnites() { return typeUnites; }

  public JButton getBoutonJoueur() { return boutonJoueur; }
  public JButton getBoutonAttaque() { return boutonAttaque; }
  public JButton getBoutonCreationUniteAerienne() { return boutonCreationUniteAerienne; }
  public JButton getBoutonCreationUniteMaritime() { return boutonCreationUniteMaritime; }
  public JButton getBoutonCreationUniteTerrestre() { return boutonCreationUniteTerrestre; }

  public LinkedList<JLabel> getListeIcones() { return listeIcones; }

  public MiniMap getMiniMap() { return miniMap; }

  public PanelMap getMap() { return map; }

  public Timer getTimer() { return timer; }

  // ***************************************
  // *************** Setters ***************
  // ***************************************

  public void animationStatus(boolean b) { map.setAnimation(b); }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  public static void afficher(JTextPane textPane, String titre, String infos, Color couleurTitre) {

		SimpleAttributeSet style_normal = new SimpleAttributeSet();
		StyleConstants.setForeground(style_normal, Color.WHITE);
		StyleConstants.setFontFamily(style_normal, "Calibri");
		StyleConstants.setFontSize(style_normal, 18);

		SimpleAttributeSet style_titre = new SimpleAttributeSet();
		style_titre.addAttributes(style_normal);
    StyleConstants.setForeground(style_titre, couleurTitre);
		StyleConstants.setFontSize(style_titre, 22);
		StyleConstants.setBold(style_titre, true);

		try {
			StyledDocument doc = textPane.getStyledDocument();
			doc.insertString(doc.getLength(), titre + "\n\n", style_titre);
			doc.insertString(doc.getLength(), infos + "\n", style_normal);
		}
		catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

  public void informations() {
    textInfos.setText("");
  }

  public void informations (AbstractUnite unite) {
    textInfos.setText("");
    String nom = unite.getNom();
    String str = unite.type();
    Color couleur = unite.getJoueur().getColor();
    nom += " - " + unite.getJoueur().getNom();
    if (unite.getCombat() != null)
      str += "\n" + unite.combat();
    if (unite.getDeplacement() != null)
      str += "\n" + unite.deplacement();
    str += "\nPoints de vie : " + unite.getPV() + "/" + unite.getPVMax();
    str += "\nPortee : " + unite.getPortee() + ((unite.getPortee() > 1) ? " cases." : " case.");
    if (map.getJoueur().possede(unite)) {
      str += "\nChamp de vision : " + unite.getVision() + ((unite.getVision() > 1) ? " cases." : " case.");
    }
    afficher(textInfos, nom, str, couleur);
    if (map.getJoueur().possede(unite) && unite.getCombat() != null)
      textInfos.insertComponent(boutonAttaque);
  }

  public void informations (AbstractUnite unite, AbstractUnite cible, int degats) {
    informations(unite);
    String str = cible.getNom() + " a perdu " + degats + " points de vie !";
    afficher(textInfos, "", str, Color.WHITE);
  }

  public void informations (AbstractTerrain terrain, int vision) {
    textInfos.setText("");
    String str = "";
    afficher(textInfos, (vision == 0)? "Mystere absolu" : terrain.getNom(), str, Color.WHITE);
  }

  public void informations (AbstractVille ville, Joueur joueur, int vision) {
    textInfos.setText("");
    String str = "";
    Joueur j = ville.getJoueur();
    Color couleur = ((j==null)?Color.WHITE:j.getColor());
    JButton button = null;
    str += "Ville possedee par : " + ((j == null) ? "personne." :(j.getNom() + "."));
    afficher(textInfos, (vision == 0) ? "Mystere absolu" : ville.getNom(), str, couleur);

    ActionVille aL = new ActionVille(this, ville);

    if (j == joueur && !ville.getAchete()) {
      if (ville instanceof Usine) {
        boutonCreationUniteTerrestre = new JButton("Creer une unite terrestre");
        button = boutonCreationUniteTerrestre;
      }
      if (ville instanceof Aeroport) {
        boutonCreationUniteAerienne = new JButton("Creer une unite aerienne");
        button = boutonCreationUniteAerienne;
      }
      if (ville instanceof Port) {
        boutonCreationUniteMaritime = new JButton("Creer une unite maritime");
        button = boutonCreationUniteMaritime;
      }
      if (button != null) { // cas des villes permettant de produire des unites
        button.setFocusable(false);
        button.addActionListener(aL);
        textInfos.insertComponent(button);
      }
    }

    afficher(textInfos, "", "", Color.WHITE);
  }

  public void afficherChoixUnites (Joueur joueur, int n, AbstractVille ville) {
    int prixMax = joueur.getArgent();
    panelChoixUnites.removeAll();
    panelChoixUnites.setFocusable(false);
    panelChoixUnites.setPreferredSize(new Dimension(200, 200));
    panelChoixUnites.setBackground(couleurFond);
    typeUnites = n;
    AbstractUnite[] unites = Variable.listeUnites[n];
    for (int i = 0; i < unites.length; i++)
      System.out.print(unites[i].getNom() + " ");
    System.out.println();
    listeIcones.clear();
    mI = new MouseIcone(this, ville);
    for (int i = 0; i < unites.length; i++) {
      JLabel icone;
      if (unites[i].getCout() <= prixMax) {
        icone = new JLabel(new ImageIcon(Variable.tImIcone[unites[i].getIndice()-5]));
      }
      else {
        icone = new JLabel(new ImageIcon(Variable.tImIconeTropCher[unites[i].getIndice()-5]));
      }
      icone.addMouseListener(mI);
      listeIcones.add(icone);
      panelChoixUnites.add(icone);
    }
    panelChoixUnites.revalidate();
    textInfos.insertComponent(panelChoixUnites);
  }

  public void informations (Joueur joueur) {
    textJoueur.setText("");
    String str = "" + (joueur.getNbUnites()-1) + ((joueur.getNbUnites()-1 > 1) ? " unites" : " unite");
    str += "\n" + joueur.getArgent() + " €";
    if (joueur.getNbUnites() == 0)
      str += "\nSon general est mort ! Perdant du jeu.\n";
    else  str += "\nEst dirige par le General " + joueur.getUnites().get(0).getNom() + "\n";
    afficher(textJoueur, joueur.getNom(), str, joueur.getColor());
    textJoueur.insertComponent(boutonJoueur);
  }

  public void nouveauTour() {
    map.nouveauTour();
    miniMap.nouveauTour();
    this.informations();
    this.informations(map.getJoueur());
  }

  /**
   * Met a jour les informations lors d'un combat.
   * @param attaquant L'unite qui attaque.
   * @param cible     L'unite qui est attaquee.
   * @param degats    Le nombre de points de degâts infliges.
   */
  public void informationsCombat (AbstractUnite attaquant, AbstractUnite cible, int degats) {

    // On verifie si un joueur est mort,
    int n = map.getPlateau().mort(attaquant, cible);
    // Si un joueur est mort, on affiche un pop-up le signalant.
    if (n == 1 || n == 2)
      popUpMort(cible.getJoueur());
    // S'il ne reste qu'un joueur en jeu, on gere la fin du jeu.
    if (n == 2)
      popUpFinPartie();

    if (cible.getPV() <= 0) {
      // On met a jour les informations du joueur qui vient de tuer une unite.
      this.informations(map.getJoueur());
    }
    // On met a jour les informations des unites en affichant les degats infliges.
    this.informations(attaquant, cible, degats);
  }

  /**
   * Permet de gerer la fin d'un tour de jeu.
   */
  public void finTour() {
    // On verifie si les villes sur la carte changent de proprietaire.
    int n = map.getPlateau().prises();
    // Si un joueur est mort, on affiche un pop-up le signalant.
    if (n == 1 || n == 2)
      popUpMort(map.getPlateau().getJoueurMortActuel());
    // S'il ne reste qu'un joueur en jeu, on gere la fin du jeu.
    if (n == 2)
      popUpFinPartie();

    // On change de joueur et on met la vue a jour.
    this.nouveauTour();
  }

  /**
   * Affichage d'un pop-up annoncant la mort d'un joueur.
   * @param joueurMort Le joueur qui vient de mourir.
   */
  public void popUpMort (Joueur joueurMort) {
    String str = "Le joueur " + joueurMort.getNom() + " a perdu la partie.";
    JOptionPane.showMessageDialog(null, str, "Joueur mort", JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Affichage d'un pop-up annonçant la fin de la partie.
   */
  public void popUpFinPartie() {
    String str = "Bravo ! Vous avez gagne la partie.";
    JOptionPane.showMessageDialog(null, str, "Partie terminee", JOptionPane.INFORMATION_MESSAGE);
    //dispose();
    System.exit(0);
  }

}
