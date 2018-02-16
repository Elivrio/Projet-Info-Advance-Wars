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
  public final static String[] tStrBaseUni = {"zombie.png", "ninja.png"};
  public final static String[] tStrTer;
  public final static String[] tStrUni;
  public final static File[] tStrTerFile;
  public final static File[] tStrUniFile;
  public final static BufferedImage[] tImTer;
  public final static BufferedImage[] tImUni;
  public final static long serialVersionUID = 0; //Pour ne pas avoir une erreur jaune à la compilation

  static {
    // Remplissage du tableau avec les chemins vers les images des terrains
    tStrTer = new String[tStrBaseTer.length];
    for (int i = 0; i < tStrTer.length ; i++)
      tStrTer[i] = pathToImages + tStrBaseTer[i];

    // Remplissage du tableau avec les chemins vers les images des unités
    tStrUni = new String[tStrBaseUni.length];
    for (int i = 0; i < tStrUni.length ; i++)
      tStrUni[i] = pathToImages + tStrBaseUni[i];

    tStrTerFile = new File[tStrTer.length];
    for (int i = 0; i < tStrTer.length ; i++)
      tStrTerFile[i] = new File(tStrTer[i]);

    tStrUniFile = new File[tStrUni.length];
    for (int i = 0; i < tStrUni.length ; i++)
      tStrUniFile[i] = new File(tStrUni[i]);

    // Remplissage du tableau avec les images des terrains
    tImTer = new BufferedImage[tStrTerFile.length];
    for (int i = 0; i < tImTer.length; i++)
      try {
        tImTer[i] = ImageIO.read(tStrTerFile[i]);
      } catch (IOException e) {}

    // Remplissage du tableau avec les images des unités
    tImUni = new BufferedImage[tStrUniFile.length];
    for (int i = 0; i < tImUni.length; i++)
      try {
        tImUni[i] = ImageIO.read(tStrUniFile[i]);
      } catch (IOException e) {}
  }
}
