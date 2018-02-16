package src.modele;

import java.util.Scanner;
import java.io.*;

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
      System.out.print(ligne[i] + " ");
    }
    System.out.println();
    return ligne;
  }

  public Plateau plateau() {
    if (sameNbrColonnes()) {
      int[][] terrain = new int[nbrLignes()+2][nbrColonnes(/*0*/)+2];
      for (int i = 0; i < terrain.length; i++) {
        terrain[i] = lignePlateau(i, terrain);
        if (i > 0 && i < terrain.length-1)
          sc.nextLine();
      }
      return new Plateau(terrain);
    }
    System.out.println("impossible de creer plateau");
    return null;
  }
}
