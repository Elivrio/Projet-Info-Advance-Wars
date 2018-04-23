package src.modele;

import src.modele.interfaces.combat.Combat;
import src.modele.interfaces.typeunite.TypeUnite;
import src.modele.interfaces.deplacement.Deplacement;

public abstract class AbstractUnite implements Combat, TypeUnite, Deplacement {

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  // Le nom de l'unite.
  protected final String nom;

  // Ses points de vie maximum et courant.
  protected int pvMax, pv;

  // Le type de combat qu'elle utilise.
  protected Combat typeCombat;

  // Le type de deplacement qu'elle utilise.
  protected Deplacement deplacement;

  // La distance maximale a laquelle elle peut se deplacer et la distance parcourue ce tour-ci.
  protected int deplace, distance;

  // La distance sur laquelle elle peut attaquer, la distance sur laquelle elle peut voir sur le terrain.
  protected int portee, vision;

  // La quantite d'essence utilisee par un vehicule, le coût de sa creation.
  protected int essence, cout;

  // Le type de l'unite (maritime, terrestre ou aerienne).
  protected TypeUnite type;

  // Le joueur qui possede l'unite.
  protected Joueur joueur;

  // La position x et y de l'unite dans le plateau.
  protected int x, y;

  // L'indice de l'unite dans le tableau global des unites, necessaire pour aller le chercher dans variable.
  protected int indice;

  // Un boolean qui indique si l'unite a attaque ce tour-ci.
  protected boolean attaque;

  // La quantite d'argent gagne par le joueur qui tue cette unite.
  protected int gainMort;

  // Le status de l'animation des degats reçus.
  protected int animDegats;

  // ********************************************
  // *************** Constructeur ***************
  // ********************************************

  /**
   * Cree une unite l'unite
   * @param nom         Le nom de l'unite
   * @param pvMax       Le max de points de vie de l'unite
   * @param typeCombat  Le type de combat de l'unite
   * @param deplacement Le deplacement de l'unite
   * @param distance    La distance maximale sur laquelle l'unite peut se deplacer
   * @param portee      La portee d'attaque de l'unite
   * @param vision      La vision de l'unite
   * @param essence     L'essence de l'unite
   * @param prix        Le prix de construction de l'unite
   * @param type        Le type de l'unite (maritime, terrestre, aerienne)
   * @param joueur      Le joueur qui contrôle l'unite
   * @param x           La position de l'unite sur le plateau [x][]
   * @param y           La position de l'unite sur le plateau [][y]
   * @param indice      L'indice de l'unite dans le tableau du type d'unite
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
    this.animDegats = 0; // status de l'animation degats
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
  public int getAnimDegats() { return animDegats; }
  public int getDegats() { return typeCombat.getDegats(); }

  public Combat getCombat() { return typeCombat; }

  public Deplacement getDeplacement() { return deplacement; }

  public Joueur getJoueur() { return joueur; }

  public String getNom() { return nom; }

  public TypeUnite getType() { return type; }

  // ***************************************
  // *************** Setters ***************
  // ***************************************

  /**
   * Permet de designer la case sur laquelle l'unite est situee.
   * @param x L'indice du tableau au premier niveau (plateau[x])
   * @param y L'indice dans le deuxieme tableau (plateau[x][y])
   */
  public void setCase (int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Ajoute n points de vie a l'unite.
   * Si pv + n depasse pvMax, pv vaut pvMax.
   * Si pv + n est en-dessous de 0, pv vaut 0.
   * @param n Le nombre ajoute a pv.
   */
  public void setPV (int n) {
    if (pv+n <= pvMax && pv+n >= 0)
      pv += n;
    else if (pv+n > pvMax)
      pv = pvMax;
    else pv = 0;
  }

  /**
   * Permet a l'unite de se deplacer et d'attaquer pour ce tour-ci.
   */
  public void reset() {
    deplace = 0;
    attaque = true;
  }

  /**
   * Augmente le deplacement de l'unite du parametre i
   * @param i Le nombre de deplacement effectue par l'unite.
   */
  public void setDeplace (int i) {
    deplace += i;
  }

  /**
   * Fixe le status de l'animation de degats.
   * @param status Le status de l'animation ciblee.
   */
  public void setAnimDegats (int status) {
    animDegats = status;
  }

  // ****************************************************
  // *************** Fonctions d'instance ***************
  // ****************************************************

  /**
   * Decrit la methode de combat de l'unite.
   * @return Renvoie une chaîne de caractere contenant une description de la methode de combat de l'unite.
   */
  public String combat() {
    return typeCombat.combat();
  }

  /**
   * Decrit la methode de deplacement de l'unite.
   * @return Renvoie une chaîne de caractere contenant une description de la methode de deplacement de l'unite.
   */
  public String deplacement() {
    return deplacement.deplacement();
  }

  /**
   * Decrit le type de l'unite.
   * @return Renvoie une chaîne de caractere contenant une description du type de l'unite.
   */
  public String type() {
    return type.type();
  }

  /**
   * Permet d'attaquer une unite. L'unite ciblee perd des points de vie egaux a l'attaque de l'unite.
   * @param cible l'unite ciblee par l'attaque.
   */
  public void attaquer (AbstractUnite cible) {
    cible.setPV(-getDegats());
    cible.setAnimDegats(getDegats());
    attaque = false;
  }

  /**
   * Permet d'afficher l'objet sous forme d'une chaîne de caratere.
   * @return La chaîne de caractere qui va decrire l'objet.
   */
  @Override
  public String toString(){
    return "" + nom;
  }
}
