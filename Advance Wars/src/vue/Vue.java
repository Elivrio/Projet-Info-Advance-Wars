package src.vue;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import src.vue.PanelMap;
import src.modele.Plateau;
import src.modele.Unite;
import src.modele.Terrain;
import src.modele.Joueur;

public class Vue extends JFrame {

  private PanelMap panelPlateau;
  private JTextPane textJoueur, textInfos;
  private JSplitPane split1, split2;
  private Dimension dimensionEcran;
  private int largeurEcran, hauteurEcran;

  public Vue (PanelMap map) {
    dimensionEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    largeurEcran = (int)dimensionEcran.getWidth();
    hauteurEcran = (int)dimensionEcran.getHeight();
    setSize(largeurEcran, hauteurEcran);
    setTitle("Advance Wars");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

  public void informations (Unite unite) {
    textInfos.setText("");
    String str = "Unité de type " + unite.getNom();
    str += "\nPossède " + unite.getPV() + " points de vie";
    str += "\nPeut voir à une distance de " + unite.getVision() + " cases";
    str += "\nPeut avancer de " + unite.getDistance() + " cases";
    afficher(textInfos, "Informations unité", str);
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
    str += "\nPossède " + joueur.getNbUnites() + " unités";
    for (int i=0; i<joueur.getNbUnites(); i++)
      str += "\nUnité " + (i+1) + " : " + joueur.getUnites().get(i).getNom();
    afficher(textJoueur, "Informations joueur", str);
  }
}
