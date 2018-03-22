package src.controleur;

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

  private AbstractVille ville;

  public MouseIcone (Vue v, AbstractVille vi) {
    super(v);
    ville = vi;
  }

  @Override
  public void mouseClicked (MouseEvent me) {
    Object source = me.getSource();
    LinkedList icones = vue.getListeIcones();
    AbstractUnite[] unites = Variable.listeUnites[vue.getTypeUnites()];
    int x = ville.getX();
    int y = ville.getY();
    SNHException evt = new SNHException();
    Plateau plateau = map.getPlateau();
    for (int i = 0; i < icones.size(); i++)
      if (source == icones.get(i)) {
        Joueur j = map.getJoueur();
        if (unites[i].getCout() <= j.getArgent() && plateau.getUnites()[y][x] == null) {
          switch (vue.getTypeUnites()) {
            case 0 :
              switch (i) {
                case 0 : plateau.addUnite(new AAir (j, x, y), j, true); break;
                case 1 : plateau.addUnite(new Artillerie (j, x, y), j, true); break;
                case 2 : plateau.addUnite(new Bazooka (j, x, y), j, true); break;
                case 3 : plateau.addUnite(new DCA (j, x, y), j, true); break;
                case 4 : plateau.addUnite(new Fantassin (j, x, y), j, true); break;
                case 5 : plateau.addUnite(new LMiss (j, x, y), j, true); break;
                case 6 : plateau.addUnite(new Recon (j, x, y), j, true); break;
                case 7 : plateau.addUnite(new Tank (j, x, y), j, true); break;
                case 8 : plateau.addUnite(new TankM (j, x, y), j, true); break;
                case 9 : plateau.addUnite(new VTB (j, x, y), j, true); break;
                default :
                  evt.printStackTrace();
                  System.out.println(evt);
                  System.exit(1);
                  break;
              }
              break;
            case 1 :
              switch (i) {
                case 0 : plateau.addUnite(new Barge (j, x, y), j, true); break;
                case 1 : plateau.addUnite(new Cuirasse (j, x, y), j, true); break;
                case 2 : plateau.addUnite(new Destroyeur (j, x, y), j, true); break;
                case 3 : plateau.addUnite(new SousMarin (j, x, y), j, true); break;
                default :
                  evt.printStackTrace();
                  System.out.println(evt);
                  System.exit(1);
                  break;
              }
              break;
            case 2 :
              switch (i) {
                case 0 : plateau.addUnite(new Bombardier (j, x, y), j, true); break;
                case 1 : plateau.addUnite(new Chasseur (j, x, y), j, true); break;
                case 2 : plateau.addUnite(new Helico (j, x, y), j, true); break;
                case 3 : plateau.addUnite(new HelicoptereTransport (j, x, y), j, true); break;
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
          ville.setAchete(true);
          vue.informations(j);
          vue.informations(ville, j, j.getVision()[y][x]);
          map.repaint();
          miniMap.repaint();
        }
      }
  }

  @Override
  public void mouseExited (MouseEvent me) {}
  public void mouseEntered (MouseEvent me) {}
  public void mousePressed (MouseEvent me) {}
  public void mouseReleased (MouseEvent me) {}

}
