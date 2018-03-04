 package src.controleur;

 import java.awt.event.*;
 import src.vue.*;

 public class ControleurActionListener extends Controleur implements ActionListener {

   public ControleurActionListener (Vue v) {
     super(v);
   }

   public void actionPerformed (ActionEvent e) {
     Object source = e.getSource();
     // Si on clique sur le bouton pour changer de joueur
     if (source == vue.getBoutonJoueur()) {
       map.setCliquee(null);
       map.setJoueur(1);
       vue.informations(map.getJoueur());
       vue.informations();
    }
    // Si on clique sur le bouton pour attaquer
    else if (source == vue.getBoutonAttaque())
      if (map.getAttaque())
        map.setAttaque(false);
      else map.setAttaque(true);
    map.repaint();
   }
 }
