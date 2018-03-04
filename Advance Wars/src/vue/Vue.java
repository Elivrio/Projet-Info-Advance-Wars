package src.vue;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import src.vue.PanelMap;
import src.modele.Plateau;
import src.modele.AbstractUnite;
import src.modele.Terrain;
import src.modele.Joueur;
import src.modele.general.General;

public class Vue extends JFrame {

  private PanelMap panelPlateau;
  private JTextPane textJoueur, textInfos;
  private JSplitPane split1, split2;
  private Dimension dimensionEcran;
  private int largeurEcran, hauteurEcran;
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
    split1.setDividerLocation(35*hauteurEcran/100);
    getContentPane().add(split1, BorderLayout.CENTER);

    split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelPlateau, split1);
    split2.setDividerSize(0);
    split2.setEnabled(false);
    split2.setDividerLocation(75*largeurEcran/100);
    getContentPane().add(split2, BorderLayout.CENTER);

    setVisible(true);
  }

  public PanelMap getMap() {
    return panelPlateau;
  }

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
    String str = ((unite instanceof General)? "Général " : "Unité de type ") + unite.getNom();
    str += "\n" + unite.type();
    if (unite.getCombat() != null)
      str += "\n" + unite.combat();
    if (unite.getDeplacement() != null)
      str += "\n" + unite.deplacement();
    str += "\nPossède " + unite.getPV() + " points de vie sur " + unite.getPVMax();
    str += "\nPeut voir à une distance de " + unite.getVision() + " cases";
    str += "\nPeut avancer de " + unite.getDistance() + " cases";
    str += "\nS'est déplacé ce tour-ci de " + unite.getDeplace() + " cases";
    str += "\nPeut attaquer à une portée de " + unite.getPortee() + " cases\n\n";
    afficher(textInfos, "Informations unité", str);
    if (unite.getCombat() != null)
      textInfos.insertComponent(boutonAttaque);
  }

  public void informations (Terrain terrain, int vision) {
    textInfos.setText("");
    String str = "Terrain de type ";
    if (vision == 0)
      str += "Mystère Absolu";
    else str += terrain.getNom();
    afficher(textInfos, "Informations terrain", str);
  }

  public void informations (Joueur joueur) {
    textJoueur.setText("");
    String str = "Joueur " + joueur.getNom();
    str += "\nPossède " + (joueur.getNbUnites()-1) + ((joueur.getNbUnites()-1 > 1)? " unités" : " unité");
    str += "\nEst dirigé par le Général " + joueur.getUnites().get(0).getNom();
    str += "\n\n";
    afficher(textJoueur, "Informations joueur", str);
    textJoueur.insertComponent(boutonJoueur);
  }
}
