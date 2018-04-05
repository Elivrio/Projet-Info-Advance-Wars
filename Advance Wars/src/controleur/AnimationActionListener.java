package src.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.vue.Vue;

public class AnimationActionListener implements ActionListener {

  private Vue vue;

  public AnimationActionListener (Vue v) {
    vue = v;
  }

  public void actionPerformed (ActionEvent e) {
    vue.animationStatus(!vue.getAnimationStatus());
    vue.repaint();
  }

}
