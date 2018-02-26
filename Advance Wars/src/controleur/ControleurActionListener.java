 package src.controleur;

 import java.awt.event.*;
 import src.vue.*;

 public class ControleurActionListener extends Controleur implements ActionListener {

   public ControleurActionListener (Vue v) {
     super(v);
   }

   public void actionPerformed (ActionEvent e) {
     map.setCliquee(null);
     map.setJoueur(1);
     vue.informations(map.getJoueur());
     vue.informations();
     map.repaint();
   }
 }
