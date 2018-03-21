package src.modele;

import java.io.File;
import java.util.Scanner;

import src.modele.general.General;

public class CarteScanner {
  private Scanner sc;
  private File carte;

  public CarteScanner (String fic) {
    sc = null;
    try {
      carte = new File(fic);
      sc = new Scanner(carte);
    } catch(Exception e) {
      System.out.println("Erreur ouverture fichier");
    }
  }

// LES FONCTIONS SUIVANTES NE DOIVENT PAS ETRE UTILISEES PLUS D'UNE FOIS

public int getLignes() { return sc.nextInt(); }
public int getColonnes() { return sc.nextInt(); }
public int getJoueurs() { return sc.nextInt(); }

private int[][] ligne(int l, int[][][] t, boolean b) { // lit une ligne de la carte terrain ou armee
  int[][] ligne = new int[t[0].length][2];
  for (int i = 0; i < ligne.length; i++) {
    if (l == 0 || l == t.length - 1 || i == 0 || i == ligne.length-1) {
      if (b) {
        ligne[i][0] = 4;
        ligne[i][1] = 0;
      } else {
        ligne[i][0] = 0;
        ligne[i][1] = 0;
      }
    } else {
      ligne[i][0] = sc.nextInt();
      //System.out.print(ligne[i][0]);
      ligne[i][1] = sc.nextInt();
      //System.out.print(ligne[i][1]);
      sc.next();
    }
    //System.out.print(ligne[i][0] + " ");
  }
  //System.out.println();
  return ligne;
}

private int[][][] Tableau(int x, int y, boolean b) { // construit un tableau soit du terrain soit de l'armee
  int[][][] tableau = new int[x][y][2];
  for (int i = 0; i < tableau.length; i++) {
    tableau[i] = ligne(i, tableau, b);
    if (i > 0 && i < tableau.length-1)
    sc.nextLine();
  }
  return tableau;
}

public Plateau plateau(Joueur[] joueurs, General[] generaux, int lignes, int colonnes) { // construit un plateau à partir de la carte texte
  // getLignes et getColonnes sont maintenant utilisées dans Menu.lancerJeu (obligatoire avant d'utiliser plateau)
  int joueur = getJoueurs(); // obligatoire pour bien lire la carte
  //if (joueurs.length > joueur)
  // throw Exception à venir.
  sc.nextLine();
  int[][][] terrain = Tableau(lignes + 1, colonnes + 1, true);
  sc.nextLine();
  System.out.println();
  int[][][] unites = Tableau(lignes + 1, colonnes + 1, false);
  return new Plateau(terrain, unites, generaux, joueurs);
}

}
