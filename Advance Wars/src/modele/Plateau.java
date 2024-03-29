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

  // Entier permettant de savoir si une unite prend le QG d'un autre joueur.
  private int nbToursSurQG;

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

  // Variable permettant d'afficher un popup.
  private Joueur joueurMortActuel;

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * @param  carte     La carte du jeu.
   * @param  armees    La repartition des unites sur le terrain.
   * @param  generaux  Le tableau des generaux choisis par les joueurs.
   * @param  jou       Les joueurs.
   * @throws Exception Exception.
   */
  public Plateau (int[][][] carte, int[][][] armees, General[] generaux, LinkedList<Joueur> jou) throws Exception {
    hauteur = carte.length;
    largeur = carte[0].length;
    terrain = new AbstractTerrain[hauteur][largeur];
    villes = new LinkedList<AbstractVille>();
    unites = new AbstractUnite[hauteur][largeur];
    joueurMortActuel = null;
    nbToursSurQG = 0;
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
            if (carte[i][j][1] < joueurs.size()+1){
              Qg qg = new Qg(j, i);
              qg.setJoueur(joueurs.get(carte[i][j][1]-1));
              terrain[i][j] = qg;
              villes.add(qg);
            }
            break;
          default : throw new SNHException();
        }
      }
    }
  }


  // ***************************************
  // *************** Getters ***************
  // ***************************************

  public Joueur getJoueurMortActuel() {
    return joueurMortActuel;
  }

  public int getHauteur() { return hauteur; }
  public int getLargeur() { return largeur; }
  public AbstractUnite[][] getUnites() { return unites; }
  public AbstractTerrain[][] getTerrain() { return terrain; }
  public LinkedList<Joueur> getJoueurs() { return joueurs; }
  public LinkedList<AbstractVille> getVilles() { return villes; }

  // ***************************************
  // *************** Setters ***************
  // ***************************************

  /**
   * Reinitialise l'achat de chaque ville a false.
   */
  public void reset() {
    for (AbstractVille v : villes)
      v.setAchete(false);
  }

  public void addUnite (AbstractUnite unite, Joueur joueur, boolean b) {
    joueur.add(unite, b);
    int i = unite.getY();
    int j = unite.getX();
    unites[i][j] = unite;
  }

  public void rmvUnite (AbstractUnite u) {
    unites[u.getY()][u.getX()] = null;
  }

  public void setUnites (int ancienX, int ancienY, int x, int y) {
    AbstractUnite u = unites[ancienY][ancienX];
    unites[ancienY][ancienX] = null;
    unites[y][x] = u;
  }

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  /**
   * Cree les generaux de chaque joueur.
   * @param generaux Le tableau de generaux choisis.
   */
  public void initJoueurs (General[] generaux) {
    for (int i = 0; i < generaux.length; i++) {
      joueurs.set(i, generaux[i].getJoueur());
      initUnite(joueurs.get(i), generaux[i]);
    }
  }

  /**
   * Initialise les unites et les place sur le terrain.
   * @param joueur  Le joueur auquel appartiennent les unites.
   * @param general Le general du joueur.
   */
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

  /**
   * On verifie la mort de l'unite cible. Si cette unite est morte,
   * le joueur qui controle l'unite attaquant a l'origine de la mort gagne de l'argent
   * @param attaquant L'unite qui attaque.
   * @param cible     L'unite attaquee.
   * @return          On retourne une valeur (1 si un joueur a ete tue, 2 s'il ne reste qu'un joueur en vie, 0 sinon).
   */
  public int mort (AbstractUnite attaquant, AbstractUnite cible) {
    // Si la cible est morte,
    if (cible.getPV() <= 0) {
      Joueur joueurCible = cible.getJoueur();
      // On retire du plateau et du Joueur qui le controle.
      joueurCible.remove(cible);
      this.rmvUnite(cible);

      // On augmente l'argent du joueur qui controle l'unite.
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
      joueurMortActuel = joueurMort;
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
  public int prises() {
    // On prend les villes une par une.
    for (int i = 0; i < villes.size(); i++) {
      AbstractVille ville = villes.get(i);

      // On regarde l'unite sur la case de la ville.
      AbstractUnite unite = unites[ville.getY()][ville.getX()];

      // Si l'unite n'est pas nulle, on change le proprietaire de la ville.
      if (unite != null) {

        // Si la ville est une mine,
        if (ville instanceof Mine &&
            ((ville.getJoueur() == null) ||
            (ville.getJoueur() != null && ville.getJoueur() != unite.getJoueur()))) {

          if (ville.getJoueur() != null)
            // On decremente le compteur de mines du joueur precedent
            ville.getJoueur().addMine(-1);
          // Et on incremente le compteur de villes du joueur qui l'a recuperee
          unite.getJoueur().addMine(1);
        }

        // Si la ville n'est pas le QG de l'unite dessus
        if (ville instanceof Qg && (unite.getJoueur() != ville.getJoueur())) {
          if (nbToursSurQG < 4*joueurs.size())
            nbToursSurQG++;
          else {
            nbToursSurQG = 0;
            terrain[ville.getY()][ville.getX()] = new Plaine();
            return mortJoueur(ville.getJoueur());
          }
        }
        if (!(ville instanceof Qg))
          ville.setJoueur(unite.getJoueur());
      }
    }
    return 0;
  }

  /**
   * Fonction de debugage.
   */
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
