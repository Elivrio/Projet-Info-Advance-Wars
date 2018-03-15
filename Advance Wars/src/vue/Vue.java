package src.vue;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import src.vue.PanelMap;
import src.vue.MiniMap;
import src.modele.Plateau;
import src.modele.AbstractUnite;
import src.modele.AbstractTerrain;
import src.modele.Joueur;
import src.modele.general.General;
import src.modele.terrain.AbstractVille;
import src.modele.terrain.Usine;
import src.modele.terrain.Port;
import src.modele.terrain.Aeroport;
import src.variable.Variable;

public class Vue extends JFrame {

  private PanelMap panelPlateau;
  private MiniMap miniMap;
  private JTextPane textJoueur, textInfos;
  private JSplitPane split1, split2, split3;
  private Dimension dimensionEcran;
  private int largeurEcran, hauteurEcran;
  private JButton boutonCreationUniteTerrestre = new JButton("Créer une unité terrestre");
  private JButton boutonCreationUniteMaritime = new JButton("Créer une unité maritime");
  private JButton boutonCreationUniteAerienne = new JButton("Créer une unité aérienne");

  private JComboBox<String> choixCreationUnite;
  private JButton boutonJoueur = new JButton("Changer de joueur");
  private JButton boutonAttaque = new JButton("Attaquer");

  public Vue (PanelMap map) {
    dimensionEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    largeurEcran = (int)dimensionEcran.getWidth();
    hauteurEcran = (int)dimensionEcran.getHeight();
    setSize(largeurEcran, hauteurEcran);
    setTitle("Advance Wars");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
  }

  public PanelMap getMap() {
    return panelPlateau;
  }

  public MiniMap getMiniMap() {
    return miniMap;
  }

  public JButton getBoutonCreationUniteAerienne() { return boutonCreationUniteAerienne; }
  public JButton getBoutonCreationUniteMaritime() { return boutonCreationUniteMaritime; }
  public JButton getBoutonCreationUniteTerrestre() { return boutonCreationUniteTerrestre; }
  public JComboBox<String> getChoixCreationUnite() { return choixCreationUnite; }
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

  public void informations (AbstractTerrain terrain, Joueur joueur, int vision) {
    textInfos.setText("");
    String str = "";
    if (terrain instanceof AbstractVille) {
      AbstractVille ville = (AbstractVille)terrain;
      Joueur j = ville.getJoueur();
      if (j != null) {
        if (j == joueur)
          str += "Cette ville vous appartient !\n";
        else
          str += "Cette ville appartient au joueur " + ville.getJoueur().getNom() + ".";
      }
      else
        str += "Cette ville n'appartient pour le moment à aucun joueur.";
      afficher(textInfos, (vision == 0)? "Mystère absolu" : terrain.getNom(), str);
      if (j == joueur) {
        if (ville instanceof Usine)
          textInfos.insertComponent(boutonCreationUniteTerrestre);
        else if (ville instanceof Port)
          textInfos.insertComponent(boutonCreationUniteMaritime);
        else if (ville instanceof Aeroport)
          textInfos.insertComponent(boutonCreationUniteAerienne);

        afficher(textInfos, "", "");
      }
    }
    else afficher(textInfos, (vision == 0)? "Mystère absolu" : terrain.getNom(), str);
  }

  public void afficherChoixUnites (Joueur joueur, int n) {
    int prixMax = joueur.getArgent();
    AbstractUnite[] unites = Variable.listeUnites[n];
    choixCreationUnite = new JComboBox<String>();
    choixCreationUnite.setFocusable(false);

    for (int i = 0; i < unites.length; i++)
      if (unites[i].getCout() <= prixMax)
        choixCreationUnite.addItem(unites[i].getNom() + " (" + unites[i].getCout() + ")");

    textInfos.insertComponent(choixCreationUnite);
  }

  public void informations (Joueur joueur) {
    textJoueur.setText("");
    String str = "Possède " + (joueur.getNbUnites()-1) + ((joueur.getNbUnites()-1 > 1)? " unités" : " unité");
    str += "\nPossège " + joueur.getArgent() + " euros";
    if (joueur.getNbUnites() == 0)
      str += "\nSon général est mort ! Perdant du jeu.\n";
    else  str += "\nEst dirigé par le Général " + joueur.getUnites().get(0).getNom() + "\n";
    afficher(textJoueur, joueur.getNom(), str);
    textJoueur.insertComponent(boutonJoueur);
  }
}
