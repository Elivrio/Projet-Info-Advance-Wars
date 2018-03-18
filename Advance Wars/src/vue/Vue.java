package src.vue;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.util.LinkedList;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
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
import src.modele.terrain.Aeroport;
import src.controleur.ActionVille;
import src.modele.terrain.AbstractVille;
import src.controleur.ControleurActionListener;

public class Vue extends JFrame {

  private PanelMap panelPlateau;
  private JPanel panelChoixUnites;
  private MiniMap miniMap;
  private JTextPane textJoueur, textInfos;
  private JSplitPane split1, split2, split3;
  private Dimension dimensionEcran;
  private int largeurEcran, hauteurEcran;
  private JButton boutonCreationUniteTerrestre = new JButton();
  private JButton boutonCreationUniteMaritime = new JButton();
  private JButton boutonCreationUniteAerienne = new JButton();
  private MouseIcone mI;
  private ControleurActionListener cAL;
  private int typeUnites;

  private LinkedList<JLabel> listeIcones = new LinkedList<JLabel>();

  private JButton boutonJoueur = new JButton("Changer de joueur");
  private JButton boutonAttaque = new JButton("Attaquer");

  public Vue (PanelMap map) {

    dimensionEcran = Toolkit.getDefaultToolkit().getScreenSize();
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
    panelPlateau = map;
    textJoueur = new JTextPane();
    textJoueur.setEditable(false);
    textJoueur.setBackground(new Color(0.3f, 0.3f, 0.3f));
    informations(map.getJoueur());

    textInfos = new JTextPane();
    textInfos.setEditable(false);
    textInfos.setBackground(new Color(0.3f, 0.3f, 0.3f));

    split1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, textJoueur, textInfos);
    split1.setDividerSize(2);
    split1.setEnabled(false);
    split1.setDividerLocation(20*hauteurEcran/100);

    miniMap = new MiniMap(map.getPlateau(), map.getJeu(), 35*hauteurEcran/100, 25*largeurEcran/100);

    split2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split1, miniMap);
    split2.setDividerSize(0);
    split2.setEnabled(false);
    split2.setDividerLocation(65*hauteurEcran/100);

    split3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelPlateau, split2);
    split3.setDividerSize(0);
    split3.setEnabled(false);
    split3.setDividerLocation(75*largeurEcran/100);
    getContentPane().add(split3, BorderLayout.CENTER);

    setVisible(true);

    cAL = new ControleurActionListener(this);

    boutonJoueur.addActionListener(cAL);
    boutonAttaque.addActionListener(cAL);
  }

  public PanelMap getMap() {
    return panelPlateau;
  }

  public MiniMap getMiniMap() {
    return miniMap;
  }

  public int getTypeUnites() { return typeUnites; }
  public LinkedList<JLabel> getListeIcones() { return listeIcones; }
  public JButton getBoutonCreationUniteAerienne() { return boutonCreationUniteAerienne; }
  public JButton getBoutonCreationUniteMaritime() { return boutonCreationUniteMaritime; }
  public JButton getBoutonCreationUniteTerrestre() { return boutonCreationUniteTerrestre; }
  public JButton getBoutonJoueur() { return boutonJoueur; }
  public JButton getBoutonAttaque() { return boutonAttaque; }

  public static void afficher(JTextPane textPane, String titre, String infos){

		SimpleAttributeSet style_normal = new SimpleAttributeSet();
		StyleConstants.setForeground(style_normal, Color.WHITE);
		StyleConstants.setFontFamily(style_normal, "Calibri");
		StyleConstants.setFontSize(style_normal, 18);

		SimpleAttributeSet style_titre = new SimpleAttributeSet();
		style_titre.addAttributes(style_normal);
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
    String str = unite.type();
    if (unite.getCombat() != null)
      str += "\n" + unite.combat();
    if (unite.getDeplacement() != null)
      str += "\n" + unite.deplacement();
    str += "\nPossède " + unite.getPV() + " points de vie sur " + unite.getPVMax();
    str += "\nPeut voir à une distance de " + unite.getVision() + " cases";
    str += "\nPeut avancer de " + unite.getDistance() + " cases";
    str += "\nS'est déplacé ce tour-ci de " + unite.getDeplace() + " cases";
    str += "\nPeut attaquer à une portée de " + unite.getPortee() + " cases\n";
    afficher(textInfos, unite.getNom(), str);
    if (unite.getCombat() != null)
      textInfos.insertComponent(boutonAttaque);
  }

  public void informations (AbstractUnite unite, AbstractUnite cible, int degats) {
    informations(unite);
    String str = cible.getNom() + " a perdu " + degats + " points de vie !";
    afficher(textInfos, "", str);
  }

  public void informations (AbstractTerrain terrain, int vision) {
    textInfos.setText("");
    String str = "";
    afficher(textInfos, (vision == 0)? "Mystère absolu" : terrain.getNom(), str);
  }

  public void informations (AbstractVille ville, Joueur joueur, int vision) {
    textInfos.setText("");
    String str = "";
    Joueur j = ville.getJoueur();
    if (j != null) {
      if (j == joueur)
        str += "Cette ville vous appartient !\n";
      else
        str += "Cette ville appartient au joueur " + ville.getJoueur().getNom() + ".";
    }
    else
      str += "Cette ville n'appartient pour le moment à aucun joueur.";

    afficher(textInfos, (vision == 0)? "Mystère absolu" : ville.getNom(), str);

    ActionVille aL = new ActionVille(this, ville);
    if (j == joueur && !ville.getAchete()) {
      if (ville instanceof Usine) {
        boutonCreationUniteTerrestre = new JButton("Créer une unité terrestre");
        boutonCreationUniteTerrestre.setFocusable(false);
        textInfos.insertComponent(boutonCreationUniteTerrestre);
        boutonCreationUniteTerrestre.addActionListener(aL);
      }
      else if (ville instanceof Port) {
        boutonCreationUniteMaritime = new JButton("Créer une unité maritime");
        boutonCreationUniteMaritime.setFocusable(false);
        textInfos.insertComponent(boutonCreationUniteMaritime);
        boutonCreationUniteMaritime.addActionListener(aL);
      }
      else if (ville instanceof Aeroport) {
        boutonCreationUniteAerienne = new JButton("Créer une unité aérienne");
        boutonCreationUniteAerienne.setFocusable(false);
        textInfos.insertComponent(boutonCreationUniteAerienne);
        boutonCreationUniteAerienne.addActionListener(aL);
      }

      afficher(textInfos, "", "");
    }
  }

  public void afficherChoixUnites (Joueur joueur, int n, AbstractVille ville) {
    int prixMax = joueur.getArgent();
    panelChoixUnites.setFocusable(false);
    panelChoixUnites.removeAll();
    panelChoixUnites.setPreferredSize(new Dimension(200, 200));
    panelChoixUnites.setBackground(new Color(0.3f, 0.3f, 0.3f));
    typeUnites = n;
    AbstractUnite[] unites = Variable.listeUnites[n];
    JLabel icone;
    listeIcones.clear();
    mI = new MouseIcone(this, ville);
    for (int i = 0; i < unites.length; i++) {
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
    String str = "Possède " + (joueur.getNbUnites()-1) + ((joueur.getNbUnites()-1 > 1)? " unités" : " unité");
    str += "\nPossède " + joueur.getArgent() + " euros";
    if (joueur.getNbUnites() == 0)
      str += "\nSon général est mort ! Perdant du jeu.\n";
    else  str += "\nEst dirigé par le Général " + joueur.getUnites().get(0).getNom() + "\n";
    afficher(textJoueur, joueur.getNom(), str);
    textJoueur.insertComponent(boutonJoueur);
  }
}
