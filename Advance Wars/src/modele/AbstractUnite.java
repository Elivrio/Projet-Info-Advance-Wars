package src.modele;

import src.modele.interfaces.combat.Combat;
import src.modele.interfaces.typeunite.TypeUnite;
import src.modele.interfaces.deplacement.Deplacement;

public abstract class AbstractUnite implements Combat, TypeUnite, Deplacement {
  protected final String nom;
  protected int pvMax, pv;
  protected Combat typeCombat;
  protected Deplacement deplacement;
  protected int deplace, distance;
  protected TypeUnite type;
  protected Joueur joueur;



  public AbstractUnite (String n, int pM, Combat c, Deplacement d, int dis, TypeUnite t, Joueur j) {
    nom = n;
    pvMax = pM;
    pv = pM;
    typeCombat = c;
    deplacement = d;
    distance = dis;
    deplace = 0;
    type = t;
    joueur = j;
  }

  public void premiereArme(){
    typeCombat.premiereArme();
  }

  public void move(){
    deplacement.move();
  }




}
