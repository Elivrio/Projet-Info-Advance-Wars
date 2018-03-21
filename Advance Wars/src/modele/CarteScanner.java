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

  public int nbrLignes() {
    int lignes = 0;
    Scanner compteur = null;
    try {
      compteur = new Scanner(carte);
    } catch(Exception e) {
      System.out.println("fichier introuvable");
    }
    while(compteur.hasNextLine()) {
      lignes ++;
      compteur.nextLine();
    }
    compteur.close();
    return lignes;
  }

  public int nbrColonnes(/*int line*/) {
    int colonnes = 0;
    Scanner compteur = null;
    try {
      compteur = new Scanner(carte);
    } catch(Exception e) {
      System.out.println("fichier introuvable");
    }
    /*int i = 0;
    while (compteur.hasNextLine() && i < line) {
    compteur.nextLine();
    i++;
  }*/
  while (compteur.hasNextInt() && !sc.hasNext(";")) {
    colonnes ++;
    compteur.nextInt();
  }
  compteur.close();
  return colonnes;
}

public boolean sameNbrColonnes() { //à voir ? utile ?
  int colonnes = nbrColonnes(/*0*/);
  for (int i = 1; i < nbrLignes(); i++) {
    int colonnesSuiv = nbrColonnes(/*i*/);
    if (colonnesSuiv != colonnes)
    return false;
    colonnes = colonnesSuiv;
  }
  return true;
}

private int[] lignePlateau (int l, int[][] t) {
  int[] ligne = new int[t[0].length];
  for (int i = 0; i < ligne.length; i++) {
    if (l == 0 || l == t.length - 1 || i == 0 || i == ligne.length-1)
    ligne[i] = 4;
    else
    ligne[i] = sc.nextInt();
    //System.out.print(ligne[i] + " ");
  }
  //System.out.println();
  return ligne;
}

/*public Plateau plateau (int nbJoueurs, General[] generaux) {
  if (sameNbrColonnes()) {
    int[][] terrain = new int[nbrLignes()+2][nbrColonnes(/*0*//*)+2];
    for (int i = 0; i < terrain.length; i++) {
      terrain[i] = lignePlateau(i, terrain);
      if (i > 0 && i < terrain.length-1)
      sc.nextLine();
    }
    return new Plateau(terrain, generaux);
  }
  System.out.println("impossible de creer plateau");
  return null;
}*/

// LES FONCTIONS SUIVANTES NE DOIVENT PAS ETRE UTILISEES EN DEHORS DE LA METHODE PLATEAU()

public int getLignes() { return sc.nextInt(); }
public int getColonnes() { return sc.nextInt(); }
public int getJoueurs() { return sc.nextInt(); }

private int[][] ligne(int l, int[][][] t, boolean b) {
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

private int[][][] Tableau(int x, int y, boolean b) {
  int[][][] tableau = new int[x][y][2];
  for (int i = 0; i < tableau.length; i++) {
    tableau[i] = ligne(i, tableau, b);
    if (i > 0 && i < tableau.length-1)
    sc.nextLine();
  }
  return tableau;
}

public Plateau plateau(Joueur[] joueurs, General[] generaux, int lignes, int colonnes) {
  int joueur = getJoueurs();
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
