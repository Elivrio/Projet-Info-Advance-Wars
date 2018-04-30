package src.modele;

import java.io.File;
import java.util.Scanner;
import java.util.LinkedList;

import src.modele.general.General;

public class CarteScanner {

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  // Le scanner utilise pour lire la carte.
  private Scanner sc;

  // Le fichier contenant la carte.
  private File carte;

  //
  private boolean li = true;
  private boolean co = true;

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param fic Lien vers le fichier.
   */
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

  // ***************************************
  // *************** Getters ***************
  // ***************************************

  public int getLignes() {
    if (li) {
      li = false;
      return sc.nextInt();
    }
    return 0;
  }

  public int getColonnes() {
    if (co) {
      co = false;
      return sc.nextInt();
    }
    return 0;
  }

  public int getJoueurs() { return sc.nextInt(); }

  /**
   * Lit une ligne de la carte terrain ou armee.
   * @param  l [description]
   * @param  t [description]
   * @param  b [description]
   * @return   [description]
   */
  private int[][] ligne (int l, int[][][] t, boolean b) {
    int[][] ligne = new int[t[0].length][2];
    for (int i = 0; i < ligne.length; i++)
      if (l == 0 || l == t.length - 1 || i == 0 || i == ligne.length-1) {
        ligne[i][0] = 0;
        if (b)
          ligne[i][0] = 4;
        ligne[i][1] = 0;
      } else {
        ligne[i][0] = sc.nextInt();
        ligne[i][1] = sc.nextInt();
        sc.next();
      }
    return ligne;
  }

  /**
   * Construit le tableau soit du terrain soit de l'armee.
   * @param  x [description]
   * @param  y [description]
   * @param  b [description]
   * @return   [description]
   */
  private int[][][] Tableau(int x, int y, boolean b) {
    int[][][] tableau = new int[x][y][2];
    for (int i = 0; i < tableau.length; i++) {
      tableau[i] = ligne(i, tableau, b);
      if (i > 0 && i < tableau.length-1)
        sc.nextLine();
    }
    return tableau;
  }

  /**
   * Construit un plateau a partir de la carte txt.
   * @param  joueurs  [description]
   * @param  generaux [description]
   * @param  lignes   [description]
   * @param  colonnes [description]
   * @return          [description]
   */
  public Plateau plateau (LinkedList<Joueur> joueurs, General[] generaux, int lignes, int colonnes) {
    // getLignes et getColonnes sont maintenant utilisees dans Menu.lancerJeu (obligatoire avant d'utiliser plateau)
    int joueur = getJoueurs();
    // Obligatoire pour bien lire la carte, permettra a terme de faire un check sur la bonne taille.
    //if (joueurs.length > joueur)
    // throw Exception a venir.
    sc.nextLine();
    int[][][] terrain = Tableau(lignes + 2, colonnes + 2, true);
    sc.nextLine();
    int[][][] unites = Tableau(lignes + 2, colonnes + 2, false);
    try {
      return new Plateau(terrain, unites, generaux, joueurs);
    } catch (Exception evt) {
      evt.printStackTrace();
      System.out.println(evt);
      System.exit(1);
    }
    return null;
  }

}
