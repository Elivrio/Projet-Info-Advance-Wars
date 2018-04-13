package src.controleur;

import javax.swing.JLabel;
import java.util.LinkedList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import src.vue.Vue;
import src.modele.Joueur;
import src.modele.Plateau;
import src.variable.Variable;
import src.modele.AbstractUnite;
import src.variable.SNHException;
import src.modele.terrain.AbstractVille;
import src.modele.chairacanon.aerienne.*;
import src.modele.chairacanon.maritime.*;
import src.modele.chairacanon.terrestre.*;

public class MouseIcone extends Controleur implements MouseListener {

  // ****************************************************
  // *************** Variables d'instance ***************
  // ****************************************************

  // Ville cliquée en ce moment.
  private AbstractVille ville;

  /**
   * @param vue   La vue du jeu.
   * @param ville La ville sélectionnée.
   */
  public MouseIcone (Vue vue, AbstractVille ville) {
    super(vue);
    this.ville = ville;
  }

  /**
   * Est appelée lors d'un clic sur une icône d'unité lors de l'achat.
   * @param me Clic souris transformé en variable par Java.
   */
  @Override
  public void mouseClicked (MouseEvent me) {
    Object source = me.getSource();
    LinkedList<JLabel> icones = vue.getListeIcones();
    // On récupère une liste de toutes les unités possibles.
    AbstractUnite[] unites = Variable.listeUnites[vue.getTypeUnites()];
    int x = ville.getX();
    int y = ville.getY();
    SNHException evt = new SNHException();
    Plateau plateau = map.getPlateau();

    // On cherche l'icône sur laquelle on a cliqué.
    for (int i = 0; i < icones.size(); i++)
      if (source == icones.get(i)) {
        Joueur joueur = map.getJoueur();
        // Si aucune unité n'est positionnée sur la ville
        // Et que l'unité choisie est dans les moyens du joueur,
        if (unites[i].getCout() <= joueur.getArgent() && plateau.getUnites()[y][x] == null) {
          // On crée la nouvelle unité du joueur sur la ville.
          switch (vue.getTypeUnites()) {
            case 0 :
              switch (i) {
                case 0 : plateau.addUnite(new AAir (joueur, x, y), joueur, true); break;
                case 1 : plateau.addUnite(new Artillerie (joueur, x, y), joueur, true); break;
                case 2 : plateau.addUnite(new Bazooka (joueur, x, y), joueur, true); break;
                case 3 : plateau.addUnite(new DCA (joueur, x, y), joueur, true); break;
                case 4 : plateau.addUnite(new Fantassin (joueur, x, y), joueur, true); break;
                case 5 : plateau.addUnite(new LMiss (joueur, x, y), joueur, true); break;
                case 6 : plateau.addUnite(new Recon (joueur, x, y), joueur, true); break;
                case 7 : plateau.addUnite(new Tank (joueur, x, y), joueur, true); break;
                case 8 : plateau.addUnite(new TankM (joueur, x, y), joueur, true); break;
                case 9 : plateau.addUnite(new VTB (joueur, x, y), joueur, true); break;
                default :
                  evt.printStackTrace();
                  System.out.println(evt);
                  System.exit(1);
                  break;
              }
              break;
            case 1 :
              switch (i) {
                case 0 : plateau.addUnite(new Barge (joueur, x, y), joueur, true); break;
                case 1 : plateau.addUnite(new Cuirasse (joueur, x, y), joueur, true); break;
                case 2 : plateau.addUnite(new Destroyeur (joueur, x, y), joueur, true); break;
                case 3 : plateau.addUnite(new SousMarin (joueur, x, y), joueur, true); break;
                default :
                  evt.printStackTrace();
                  System.out.println(evt);
                  System.exit(1);
                  break;
              }
              break;
            case 2 :
              switch (i) {
                case 0 : plateau.addUnite(new Bombardier (joueur, x, y), joueur, true); break;
                case 1 : plateau.addUnite(new Chasseur (joueur, x, y), joueur, true); break;
                case 2 : plateau.addUnite(new Helico (joueur, x, y), joueur, true); break;
                case 3 : plateau.addUnite(new HelicoptereTransport (joueur, x, y), joueur, true); break;
                default :
                  evt.printStackTrace();
                  System.out.println(evt);
                  System.exit(1);
                  break;
              }
              break;
            default :
              evt.printStackTrace();
              System.out.println(evt);
              System.exit(1);
              break;
          }
          // On déclare que la ville a déjà créé une unité ce tour-ci.
          ville.setAchete(true);
          // On met à jour les informations.
          vue.informations(joueur);
          vue.informations(ville, joueur, joueur.getVision()[y][x]);
          map.repaint();
          miniMap.repaint();
        }
      }
  }

  // Fonctions de l'interface non utilisées.
  @Override
  public void mouseExited (MouseEvent me) {}
  public void mouseEntered (MouseEvent me) {}
  public void mousePressed (MouseEvent me) {}
  public void mouseReleased (MouseEvent me) {}

}
