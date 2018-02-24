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
  public final static String[] pathToImages2 = {"foret/", "plaine/", "eau/", "montagne/",""};

  public final static String[] tStrBaseTer = {"foret.png", "plaine.png", "eau.png", "montagne.png", "noir.jpg"};
  public final static String[] tStrBaseUni = {"zombie.png", "ninja.png", "nosaure.png", "magicalGirl.png"};


  public final static String[] tStrUni;

  public final static String[] tStrTer2; //images dans le dossier propre a chaque terrain
  public final static String[] tStrDossierTer; //chemin pour aller dans le dossier du terrain qu'on veut

  public final static File[] tStrTerFile;
  public final static File[] tStrUniFile;
  public final static BufferedImage[] tImTer;
  public final static BufferedImage[] tImUni;

  //liaison foret
  public final static String borderPlaineForet = "foret/plaine-foret-";
  public final static String[] tStrPlaineForet;
  public final static File[] tStrPlaineForetFile;
  public final static BufferedImage[] tImPlaineForet;

  //liaison eau
  public final static String borderPlaineEau = "plage/plaine-plage-";
  public final static String[] tStrPlaineEau;
  public final static File[] tStrPlaineEauFile;
  public final static BufferedImage[] tImPlaineEau;
  // plage
  public final static String borderPlage = "eau-";
  public final static String borderPlageCoin = "eau-coin-";
  public final static String[] tStrEauPlage;
  public final static String[] tStrEauPlageCoin;
  public final static File[] tStrEauPlageFile;
  public final static File[] tStrEauPlageCoinFile;
  public final static BufferedImage[] tImEauPlage;
  public final static BufferedImage[] tImEauPlageCoin;

  static {
    // Remplissage du tableau avec les images des terrains
    tStrDossierTer = new String[tStrBaseTer.length];
    tStrTer2 = new String[tStrBaseTer.length];
    tStrTerFile = new File[tStrTer2.length];
    tImTer = new BufferedImage[tStrTerFile.length];
    for (int i = 0; i < tStrTer2.length ; i++) {
      tStrDossierTer[i] = pathToImages + pathToImages2[i];
      tStrTer2[i] = tStrDossierTer[i]+ tStrBaseTer[i];
      tStrTerFile[i] = new File(tStrTer2[i]);
      try {
        tImTer[i] = ImageIO.read(tStrTerFile[i]);
      } catch (IOException e) {}
    }

    // liaison foret
    tStrPlaineForet = new String[16]; //il y a 16 images differentes
    tStrPlaineForetFile = new File[tStrPlaineForet.length];
    tImPlaineForet = new BufferedImage[tStrPlaineForet.length];
    for (int i=0; i<tStrPlaineForet.length; i++){
      String s = Integer.toBinaryString(i);
      s = taille(s);
      tStrPlaineForet[i] = tStrDossierTer[1] + borderPlaineForet + s + ".png";
      tStrPlaineForetFile[i] = new File(tStrPlaineForet[i]);
      try {
        tImPlaineForet[i] = ImageIO.read(tStrPlaineForetFile[i]);
      } catch (IOException e) {}
    }

    //liaison eau (toutes les possibilites n'ont pas ete dessine, donc on complete les tableaux avec de la plaine classique)
    tStrPlaineEau = new String[16];
    tStrPlaineEauFile = new File[tStrPlaineEau.length];
    tImPlaineEau = new BufferedImage[tStrPlaineEau.length];
    for (int i=0; i<tStrPlaineEau.length;i++){
      tStrPlaineEau[i] = tStrDossierTer[1] + borderPlaineEau + "0000.png";
      if (i!= 5 && i!= 7 && i!= 10 && i!=11 && i<13){
        String s = Integer.toBinaryString(i);
        s = taille(s);
        tStrPlaineEau[i]=tStrDossierTer[1] + borderPlaineEau + s + ".png";
      }
      tStrPlaineEauFile[i] = new File(tStrPlaineEau[i]);
      try {
        tImPlaineEau[i] = ImageIO.read(tStrPlaineEauFile[i]);
      } catch (IOException e) {}
    }

    //liaison plage (pas encore tous les dessins)
    tStrEauPlage = new String[16];
    tStrEauPlageFile = new File[tStrEauPlage.length];
    tImEauPlage = new BufferedImage[tStrEauPlage.length];
    for (int i=0; i<tStrEauPlage.length; i++){
      tStrEauPlage[i] = tStrDossierTer[2] + borderPlage +"1111.png";
      if (i==3 || i==6 || i==7 || i==9 || i>10){
        String s = Integer.toBinaryString(i);
        s = taille(s);
        tStrEauPlage[i]=tStrDossierTer[2]+ borderPlage + s + ".png";
      }
      tStrEauPlageFile[i] = new File(tStrEauPlage[i]);
      try {
        tImEauPlage[i] = ImageIO.read(tStrEauPlageFile[i]);
      } catch (IOException e) {}
    }
    tStrEauPlageCoin = new String[5];
    tStrEauPlageCoinFile = new File[tStrEauPlageCoin.length];
    tImEauPlageCoin = new BufferedImage[tStrEauPlageCoin.length];
    for (int i=0; i<tStrEauPlageCoin.length; i++){
      tStrEauPlageCoin[i] = tStrDossierTer[2] + borderPlageCoin +i +".png";
      tStrEauPlageCoinFile[i] = new File(tStrEauPlageCoin[i]);
      try {
        tImEauPlageCoin[i] = ImageIO.read(tStrEauPlageCoinFile[i]);
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

  public final static BufferedImage noir = test(tImTer[tImTer.length-1], 0.99f);
  public final static BufferedImage gris = test(tImTer[tImTer.length-1], 0.70f);

  public static BufferedImage test(BufferedImage img, float opacite) {
    BufferedImage newImg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = newImg.createGraphics();
    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacite));
    g.drawImage(img, 0, 0, 100, 100, null);
    g.dispose();
    return newImg;
  }

  public static String taille(String s) {
    if (s.length() == 1)
      return s = "000"+s;
    if (s.length() == 2)
      return s = "00"+s;
    if (s.length() ==3)
      return s = "0"+s;
    return s;
  }



}
