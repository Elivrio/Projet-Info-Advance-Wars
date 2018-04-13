package src.vue;

import java.awt.Color;
import java.awt.Toolkit;
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
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.Timer;

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
import src.controleur.ControleurActionListener;
import src.controleur.AnimationActionListener;


// La vue du jeu. Elle contient toute la partie de l'affichage du jeu/
@SuppressWarnings("serial")
public class Vue extends JFrame {

  private int typeUnites;
  private int largeurEcran, hauteurEcran;

  private AnimationActionListener aAL;

  private ControleurActionListener cAL;

  private JButton boutonAttaque = new JButton("Attaquer");
  private JButton boutonJoueur = new JButton("Changer de joueur");
  private JButton boutonCreationUniteMaritime = new JButton("Créer une unité maritime");
  private JButton boutonCreationUniteAerienne = new JButton("Créer une unité aérienne");
  private JButton boutonCreationUniteTerrestre = new JButton("Créer une unité terrestre");

  private JPanel panelChoixUnites;

  private JSplitPane split1, split2, split3;

  private JTextPane textJoueur, textInfos;

  private LinkedList<JLabel> listeIcones = new LinkedList<JLabel>();

  private MouseIcone mI;
  private MiniMap miniMap;

  private PanelMap map;

  private Timer timer;

  private final static Color couleurBackground = new Color(0.3f, 0.3f, 0.3f);

  public Vue (PanelMap map) {

    Dimension dimensionEcran = Toolkit.getDefaultToolkit().getScreenSize();
    largeurEcran = (int)dimensionEcran.getWidth();
    hauteurEcran = (int)dimensionEcran.getHeight();
    setSize(largeurEcran, hauteurEcran);
    setTitle("Advance Wars");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    GridLayout grid = new GridLayout(3, 4);
    panelChoixUnites = new JPanel(grid);

    boutonJoueur.setFocusable(false);
    boutonAttaque.setFocusable(false);
    boutonCreationUniteAerienne.setFocusable(false);
    boutonCreationUniteMaritime.setFocusable(false);
    boutonCreationUniteTerrestre.setFocusable(false);
    this.map = map;
    textJoueur = new JTextPane();
    textJoueur.setEditable(false);
    textJoueur.setBackground(couleurBackground);
    informations(map.getJoueur());

    textInfos = new JTextPane();
    textInfos.setEditable(false);
    textInfos.setBackground(couleurBackground);

    split1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, textJoueur, textInfos);
    split1.setDividerSize(2);
    split1.setEnabled(false);
    split1.setDividerLocation(20*hauteurEcran/100);

    miniMap = new MiniMap(map.getPlateau(), map.getJeu(), 35*hauteurEcran/100, 25*largeurEcran/100);

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

  public PanelMap getMap() { return map; }
  public MiniMap getMiniMap() { return miniMap; }
  public int getTypeUnites() { return typeUnites; }
  public LinkedList<JLabel> getListeIcones() { return listeIcones; }
  public JButton getBoutonCreationUniteAerienne() { return boutonCreationUniteAerienne; }
  public JButton getBoutonCreationUniteMaritime() { return boutonCreationUniteMaritime; }
  public JButton getBoutonCreationUniteTerrestre() { return boutonCreationUniteTerrestre; }
  public JButton getBoutonJoueur() { return boutonJoueur; }
  public JButton getBoutonAttaque() { return boutonAttaque; }
  public Timer getTimer() { return timer; }
  public boolean getAnimationStatus() { return map.getAnimation(); }

  public void animationStatus(boolean b) { map.setAnimation(b); }

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
			doc.insertString(doc.getLength(), titre+"\n\n", style_titre);
			doc.insertString(doc.getLength(), infos+"\n", style_normal);
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
    str += "\nPortée : " + unite.getPortee() + ((unite.getPortee() > 1)?" cases." : "case.");
    if (map.getJoueur().possede(unite)) {
      str += "\nChamps de vision : " + unite.getVision() + ((unite.getVision() > 1)? " cases." : " case.");
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
    afficher(textInfos, (vision == 0)? "Mystère absolu" : terrain.getNom(), str, Color.WHITE);
  }

  public void informations (AbstractVille ville, Joueur joueur, int vision) {
    textInfos.setText("");
    String str = "";
    Joueur j = ville.getJoueur();
    Color couleur = ((j==null)?Color.WHITE:j.getColor());
    JButton button = null;
    str += "Ville possédée par : " + ((j == null)?"personne." :(j.getNom() + "."));
    afficher(textInfos, (vision == 0)? "Mystère absolu" : ville.getNom(), str, couleur);

    ActionVille aL = new ActionVille(this, ville);

    if (j == joueur && !ville.getAchete()) {
      if (ville instanceof Usine) {
        boutonCreationUniteTerrestre = new JButton("Créer une unité terrestre");
        button = boutonCreationUniteTerrestre;
      }
      if (ville instanceof Aeroport) {
        boutonCreationUniteAerienne = new JButton("Créer une unité aérienne");
        button = boutonCreationUniteAerienne;
      }
      if (ville instanceof Port) {
        boutonCreationUniteMaritime = new JButton("Créer une unité maritime");
        button = boutonCreationUniteMaritime;
      }
      button.setFocusable(false);
      button.addActionListener(aL);
      textInfos.insertComponent(button);
    }

    afficher(textInfos, "", "", Color.WHITE);
  }

  public void afficherChoixUnites (Joueur joueur, int n, AbstractVille ville) {
    int prixMax = joueur.getArgent();
    panelChoixUnites.removeAll();
    panelChoixUnites.setFocusable(false);
    panelChoixUnites.setPreferredSize(new Dimension(200, 200));
    panelChoixUnites.setBackground(couleurBackground);
    typeUnites = n;
    AbstractUnite[] unites = Variable.listeUnites[n];
    listeIcones.clear();
    mI = new MouseIcone(this, ville);
    for (int i = 0; i < unites.length; i++) {
      JLabel icone;
      if (unites[i].getCout() <= prixMax)
        icone = new JLabel(new ImageIcon(Variable.tImIcone[unites[i].getIndice()-5]));
      else
        icone = new JLabel(new ImageIcon(Variable.tImIconeTropCher[unites[i].getIndice()-5]));
      icone.addMouseListener(mI);
      listeIcones.add(icone);
      panelChoixUnites.add(icone);
    }
    panelChoixUnites.revalidate();
    textInfos.insertComponent(panelChoixUnites);
  }

  public void informations (Joueur joueur) {
    textJoueur.setText("");
    String str = "" + (joueur.getNbUnites()-1) + ((joueur.getNbUnites()-1 > 1)? " unités" : " unité");
    str += "\n" + joueur.getArgent() + " €";
    if (joueur.getNbUnites() == 0)
      str += "\nSon général est mort ! Perdant du jeu.\n";
    else  str += "\nEst dirigé par le Général " + joueur.getUnites().get(0).getNom() + "\n";
    afficher(textJoueur, joueur.getNom(), str, joueur.getColor());
    textJoueur.insertComponent(boutonJoueur);
  }

  public void newTurn() {
    map.newTurn();
    miniMap.newTurn();
    this.informations();
    this.informations(map.getJoueur());
  }
}
