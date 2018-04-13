package src.modele;

import src.modele.interfaces.combat.Combat;
import src.modele.interfaces.typeunite.TypeUnite;
import src.modele.interfaces.deplacement.Deplacement;

public abstract class AbstractUnite implements Combat, TypeUnite, Deplacement {

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  // Le nom de l'unité
  protected final String nom;

  // Ses points de vie maximum et courant.
  protected int pvMax, pv;

  // Le type de combat qu'elle utilise.
  protected Combat typeCombat;

  // Le type de déplacement qu'elle utilise.
  protected Deplacement deplacement;

  // La distance maximale à laquelle elle peut se déplacer et la distance parcourue ce tour-ci.
  protected int deplace, distance;

  // La distance sur laquelle elle peut attaquer, la distance sur laquelle elle peut voir sur le terrain.
  protected int portee, vision;

  // La quantité d'essence utilisée par un véhicule, le coût de sa création.
  protected int essence, cout;

  // Le type de l'unité (maritime, terrestre ou aérienne).
  protected TypeUnite type;

  // Le joueur qui possède l'unité.
  protected Joueur joueur;

  // La position x et y de l'unité dans le plateau.
  protected int x, y;

  // L'indice de l'unité dans le tableau global des unités, nécessaire pour aller le chercher dans variable.
  protected int indice;

  // Un boolean qui indique si l'unité a attaqué ce tour-ci.
  protected boolean attaque;

  // La quantité d'argent gagné par le joueur qui tue cette unité.
  protected int gainMort;

  // Le status de l'animation des dégats reçus.
  protected int[] animDegats;

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * Crée une unité l'unité
   * @param nom         Le nom de l'unité
   * @param pvMax       Le max de points de vie de l'unité
   * @param typeCombat  Le type de combat de l'unité
   * @param deplacement Le déplacement de l'unité
   * @param distance    La distance maximale sur laquelle l'unité peut se déplacer
   * @param portee      La portée d'attaque de l'unité
   * @param vision      La vision de l'unité
   * @param essence     L'essence de l'unité
   * @param prix        Le prix de construction de l'unité
   * @param type        Le type de l'unité (maritime, terrestre, aérienne)
   * @param joueur      Le joueur qui contrôle l'unité
   * @param x           La position de l'unité sur le plateau [x][]
   * @param y           La position de l'unité sur le plateau [][y]
   * @param indice      L'indice de l'unité dans le tableau du type d'unité
   */
  public AbstractUnite (String nom, int pvMax, Combat typeCombat, Deplacement deplacement, int distance, int portee, int vision, int essence, int prix, TypeUnite type, Joueur joueur, int x, int y, int indice) {
    this.nom = nom;
    this.pvMax = pvMax;
    this.pv = pvMax;
    this.typeCombat = typeCombat;
    this.deplacement = deplacement;
    this.distance = distance;
    this.deplace = 0;
    this.portee = portee;
    this.vision = vision;
    this.essence = essence;
    this.type = type;
    this.joueur = joueur;
    this.x = x;
    this.y = y;
    this.indice = indice;
    this.cout = prix;
    this.gainMort = cout/2;
    this.animDegats = new int[2];
  }

  // ***************************************
  // *************** Getters ***************
  // ***************************************

  public boolean getAttaque() { return attaque; }

  public int getX() { return x; }
  public int getY() { return y; }
  public int getPV() { return pv; }
  public int getCout() { return cout; }
  public int getPVMax() { return pvMax; }
  public int getVision() { return vision; }
  public int getIndice() { return indice; }
  public int getPortee() { return portee; }
  public int getDeplace() { return deplace; }
  public int getGainMort() { return gainMort; }
  public int getDistance() { return distance; }
  public int getDegats() { return typeCombat.getDegats(); }

  public int[] getAnimDegats() { return animDegats; }

  public Combat getCombat() { return typeCombat; }

  public Deplacement getDeplacement() { return deplacement; }

  public Joueur getJoueur() { return joueur; }

  public String getNom() { return nom; }

  public TypeUnite getType() { return type; }

  // ***************************************
  // *************** Setters ***************
  // ***************************************

  /**
   * Permet de désigner la case sur laquelle l'unité est située.
   * @param x L'indice du tableau au premier niveau (plateau[x])
   * @param y L'indice dans le deuxieme tableau (plateau[x][y])
   */
  public void setCase (int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Ajoute n points de vie à l'unité.
   * Si pv + n dépasse pvMax, pv vaut pvMax.
   * Si pv + n est en-dessous de 0, pv vaut 0.
   * @param n Le nombre ajouté a pv.
   */
  public void setPV (int n) {
    if (pv+n <= pvMax && pv+n >= 0)
      pv += n;
    else if (pv+n > pvMax)
      pv = pvMax;
    else pv = 0;
  }

  /**
   * Permet à l'unité de se déplacer et d'attaquer pour ce tour-ci.
   */
  public void reset() {
    deplace = 0;
    attaque = true;
  }

  /**
   * Augmente le déplacement de l'unité du paramètre i
   * @param i Le nombre de déplacement effectué par l'unité.
   */
  public void setDeplace (int i) {
    deplace += i;
  }

  /**
   * [setAnimDegats description]
   * @param deg    [description]
   * @param status [description]
   */
  public void setAnimDegats (int deg, int status) {
    animDegats[0] = deg;
    animDegats[1] = status;
  }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Décrit la méthode de combat de l'unité.
   * @return Renvoie une chaîne de caractère contenant une description de la méthode de combat de l'unité.
   */
  public String combat() {
    return typeCombat.combat();
  }

  /**
   * Décrit la méthode de déplacement de l'unité.
   * @return Renvoie une chaîne de caractère contenant une description de la méthode de déplacement de l'unité.
   */
  public String deplacement() {
    return deplacement.deplacement();
  }

  /**
   * Décrit le type de l'unité.
   * @return Renvoie une chaîne de caractère contenant une description du type de l'unité.
   */
  public String type() {
    return type.type();
  }

  /**
   * Permet d'attaquer une unité. L'unité ciblée perd des points de vie égaux à l'attaque de l'unité.
   * @param cible l'unité ciblée par l'attaque.
   */
  public void attaquer (AbstractUnite cible) {
    cible.setPV(-getDegats());
    cible.setAnimDegats(getDegats(),1);
    attaque = false;
  }

  /**
   * Permet d'afficher l'objet sous forme d'une chaîne de caratère.
   * @return La chaîne de caractère qui va décrire l'objet.
   */
  @Override
  public String toString(){
    return "" + nom;
  }
}
