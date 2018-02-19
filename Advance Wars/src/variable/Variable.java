package src.variable;

import java.io.*;
import java.awt.*;
import java.lang.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
import java.awt.event.*;

public class Variable {
  public final static Color foret = new Color(0f,0.50f,0.25f);
  public final static Color plaine = new Color(0f,0.75f,0.50f);
  public final static Color eau = new Color(0f,0.60f,1f);
  public final static Color montagne = new Color(0.60f,0.60f,0.60f);
  public final static Color limite = new Color(0f,0f,0f);
  public final static Color[] tCou = {foret, plaine, eau, montagne, limite};
  public final static String pathToImages = "src/variable/images/";
  public final static String[] tStrBaseTer = {"foret.png", "plaine.png", "eau.png", "montagne.png", "noir.jpg"};
  public final static String[] tStrBaseUni = {"zombie.png", "ninja.png", "nosaure.png", "magicalGirl.png"};
  public final static String[] tStrTer;
  public final static String[] tStrUni;
  public final static File[] tStrTerFile;
  public final static File[] tStrUniFile;
  public final static BufferedImage[] tImTer;
  public final static BufferedImage[] tImUni;

  static {
    // Remplissage du tableau avec les images des terrains
    tStrTer = new String[tStrBaseTer.length];
    tStrTerFile = new File[tStrTer.length];
    tImTer = new BufferedImage[tStrTerFile.length];
    for (int i = 0; i < tStrTer.length ; i++) {
      tStrTer[i] = pathToImages + tStrBaseTer[i];
      tStrTerFile[i] = new File(tStrTer[i]);
      try {
        tImTer[i] = ImageIO.read(tStrTerFile[i]);
      } catch (IOException e) {}
    }
    
    // Remplissage du tableau avec les images des unités
    tStrUni = new String[tStrBaseUni.length];
    tStrUniFile = new File[tStrUni.length];
    tImUni = new BufferedImage[tStrUniFile.length];
    for (int i = 0; i < tStrUni.length; i++) {
      tStrUni[i] = pathToImages + tStrBaseUni[i];
      tStrUniFile[i] = new File(tStrUni[i]);
      try {
        tImUni[i] = ImageIO.read(tStrUniFile[i]);
      } catch (IOException e) {}
    }
  }

}
