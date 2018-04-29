package src.modele;

import java.util.Random;
import java.util.LinkedList;

import src.vue.Vue;
import src.vue.Jeu;
import src.modele.terrain.Eau;
import src.modele.terrain.Mine;
import src.modele.terrain.Port;
import src.modele.terrain.Foret;
import src.modele.terrain.Usine;
import src.modele.AbstractUnite;
import src.variable.SNHException;
import src.modele.terrain.Plaine;
import src.modele.general.General;
import src.modele.terrain.Aeroport;
import src.modele.terrain.Montagne;
import src.modele.terrain.TrouNoir;
import src.modele.terrain.Mine;
import src.modele.terrain.Qg;
import src.modele.terrain.AbstractVille;


public class Plateau {

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  // Hauteur et largeur du terrain.
  private int largeur, hauteur;

  // Liste des unites en jeu.
  private AbstractUnite[][] unites;

  // Liste des joueurs en jeu.
  private LinkedList<Joueur> joueurs;

  // Terrain du jeu.
  private AbstractTerrain[][] terrain;

  // Liste des villes placees sur le terrain.
  private LinkedList<AbstractVille> villes;

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param  carte     La carte du jeu.
   * @param  armees    Patate.
   * @param  generaux  [description]
   * @param  jou       [description]
   * @throws Exception [description]
   */
  public Plateau(int[][][] carte, int[][][] armees, General[] generaux, LinkedList<Joueur> jou) throws Exception {
    hauteur = carte.length;
    largeur = carte[0].length;
    terrain = new AbstractTerrain[hauteur][largeur];
    villes = new LinkedList<AbstractVille>();
    unites = new AbstractUnite[hauteur][largeur];
    joueurs = jou;
    initJoueurs(generaux);
    for (int i = 0; i < hauteur; i++) {
      for (int j = 0; j < largeur; j++) {
        switch (carte[i][j][0]) { // creation des terrains
          case 0 : terrain[i][j] = new Foret(); break;
          case 1 : terrain[i][j] = new Plaine(); break;
          case 2 : terrain[i][j] = new Eau(); break;
          case 3 : terrain[i][j] = new Montagne(); break;
          case 4 : terrain[i][j] = new TrouNoir(); break;
          case 5 :
            Usine usine = new Usine(j, i);
            terrain[i][j] = usine;
            villes.add(usine);
            break;
          case 6 :
            Port port = new Port(j, i);
            terrain[i][j] = port;
            villes.add(port);
            break;
          case 7 :
            Aeroport aeroport = new Aeroport(j, i);
            terrain[i][j] = aeroport;
            villes.add(aeroport);
            break;
          case 8 :
            Mine mine = new Mine(j, i);
            terrain[i][j] = mine;
            villes.add(mine);
            break;
          case 9 :
            Qg qg = new Qg(j, i);
            terrain[i][j] = qg;
            villes.add(qg);
            break;
          default : throw new SNHException();
        }
        // futur emplacement pour le placement initial des armees
      }
    }
  }

  public void reset() {
    for (AbstractVille v : villes)
      v.setAchete(false);
  }

  public void initJoueurs (General[] generaux) {
    for (int i = 0; i < generaux.length; i++) {
      joueurs.set(i, generaux[i].getJoueur());
      initUnite(joueurs.get(i), generaux[i]);
    }
  }

  public void initUnite (Joueur joueur, General general) {
    Random rand = new Random();
    int i, j;
    do {
      i = rand.nextInt(hauteur-2);
      j = rand.nextInt(largeur-2);
    } while (unites[i+1][j+1] != null);
    general.setCase(j+1, i+1);
    addUnite(general, joueur, false);
  }

  public void addUnite (AbstractUnite unite, Joueur joueur, boolean b) {
    joueur.add(unite, b);
    int i = unite.getY();
    int j = unite.getX();
    unites[i][j] = unite;
  }

  public int getHauteur() { return hauteur; }
  public int getLargeur() { return largeur; }
  public AbstractUnite[][] getUnites() { return unites; }
  public AbstractTerrain[][] getTerrain() { return terrain; }
  public LinkedList<Joueur> getJoueurs() { return joueurs; }
  public LinkedList<AbstractVille> getVilles() { return villes; }

  public void rmvUnite (AbstractUnite u) {
    unites[u.getY()][u.getX()] = null;
  }

  public void setUnites (int ancienX, int ancienY, int x, int y) {
    AbstractUnite u = unites[ancienY][ancienX];
    unites[ancienY][ancienX] = null;
    unites[y][x] = u;
  }

  /**
   * On verifie la mort de l'unite cible. Si cette unite est morte,
   * le joueur qui contrôle l'unite attaquant a l'origine de la mort gagne de l'argent
   * @param attaquant L'unite qui attaque.
   * @param cible     L'unite attaquee.
   * @return          On retourne une valeur (1 si un joueur a ete tue, 2 s'il ne reste qu'un joueur en vie, 0 sinon).
   */
  public int mort (AbstractUnite attaquant, AbstractUnite cible) {
    // Si la cible est morte,
    if (cible.getPV() <= 0) {
      Joueur joueurCible = cible.getJoueur();
      // On retire du plateau et du Joueur qui le contrôle.
      joueurCible.remove(cible);
      this.rmvUnite(cible);

      // On augmente l'argent du joueur qui contrôle l'unite.
      attaquant.getJoueur().setArgent(cible.getGainMort());

      if (joueurCible.generalMort())
        return mortJoueur(joueurCible);
    }
    return 0;
  }

  /**
   * On gere la mort d'un joueur.
   * @param  joueurMort Le joueur qui est mort.
   * @return            On retourne une valeur (1 si un joueur a ete tue, 2 s'il ne reste qu'un joueur en vie, 0 sinon).
   */
  public int mortJoueur (Joueur joueurMort) {
    int i = 0;
    // On cherche le joueur mort dans la liste des joueurs,
    while (i < joueurs.size() && joueurs.get(i) != joueurMort)
      i++;
    // On le supprime de la liste.
    if (joueurs.get(i) == joueurMort) {
      joueurs.remove(i);
      // S'il ne reste qu'un joueur en jeu, on renvoie la valeur 1.
      if (joueurs.size() == 1)
        return 2;
      else
        return 1;
    }
    return 0;
  }

  /**
   * Verifie si des villes sont en prises par des joueurs et change la possession des villes si necessaire.
   */
  public void prises() {
    // On prend les villes une par une.
    for (int i = 0; i < villes.size(); i++) {
      AbstractVille ville = villes.get(i);

      if ((ville instanceof Mine) && (ville.getJoueur() != null))
        ville.getJoueur().setArgent(1000);

      // On regarde l'unite sur la case de la ville.
      AbstractUnite unite = unites[ville.getY()][ville.getX()];

      // Si l'unite n'est pas nulle, on change le proprietaire de la ville.
      if (unite != null)
        ville.setJoueur(unite.getJoueur());
    }
  }


  public void debug() {
    for (int i = 0; i < terrain.length; i++ ){
      for (int j = 0; j < terrain[0].length; j++)
        System.out.print(terrain[i][j]);
      System.out.println("");
    }

    for (int i = 0; i < terrain.length; i++ ){
      for (int j = 0; j < terrain[0].length; j++)
        System.out.print(terrain[i][j]);
      System.out.println("");
    }
  }
}
