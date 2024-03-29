package src.variable;

import java.io.File;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;

import src.modele.AbstractUnite;
import src.modele.chairacanon.aerienne.*;
import src.modele.chairacanon.maritime.*;
import src.modele.chairacanon.terrestre.*;

public class Variable {

  public static AbstractUnite[][] listeUnites = {{ new AAir(null, 0, 0),
                                                   new Artillerie(null, 0, 0),
                                                   new Bazooka(null, 0, 0),
                                                   new DCA(null, 0, 0),
                                                   new Fantassin(null, 0, 0),
                                                   new LMiss(null, 0, 0),
                                                   new Recon(null, 0, 0),
                                                   new Tank(null, 0, 0),
                                                   new TankM(null, 0, 0),
                                                   new VTB(null, 0, 0) },

                                                 { new Barge(null, 0, 0),
                                                   new SousMarin(null, 0, 0),
                                                   new Destroyeur(null, 0, 0),
                                                   new Cuirasse(null, 0, 0) },

                                                 { new Helico(null, 0, 0),
                                                   new HelicoptereTransport(null, 0, 0),
                                                   new Chasseur(null, 0, 0),
                                                   new Bombardier(null, 0, 0), }};


  public final static Color foret = new Color(0f,0.50f,0.25f);
  public final static Color plaine = new Color(0f,0.75f,0.50f);
  public final static Color eau = new Color(0f,0.60f,1f);
  public final static Color montagne = new Color(0.60f,0.60f,0.60f);
  public final static Color limite = new Color(0f,0f,0f);
  public final static Color[] tCou = {foret, plaine, eau, montagne, limite};
  public final static String pathToGeneraux = "src/variable/images/generaux/";
  public final static String pathToUnites = "src/variable/images/unites/";
  public final static String pathToTerrains = "src/variable/images/terrains/";
  public final static String pathToIcones = "src/variable/images/unites/unitesPrix/";
  public final static String[] pathToImages2 = {"foret/", "plaine/", "eau/", "montagne/", "fonds/", "", "", "", "", "", "", "", ""};
  public final static String pathToDegats = "src/variable/images/degats/";

  public final static String[] tStrBaseTer = {"foret.png", "plaine.png", "eau.png", "montagne.png", "noir.jpg", "ville.png", "port.png", "aeroport.png", "mine.png", "qgZombie.png", "qgNinja.png", "qgNosaure.png", "qgMagical.png"};
  public final static String[] tStrBaseUni1 = {"zombie1.png", "ninja1.png", "nosaure1.png", "magicalGirl1.png", "tank1.png", "fantassin1.png", "recon1.png", "bazooka1.png", "tankM1.png", "artillerie1.png", "vtb1.png", "antiAerien1.png", "dca1.png", "lanceMissiles1.png", "barge1.png", "sousMarin1.png", "destroyeur1.png", "cuirasse1.png", "helicoptere1.png", "helicoptereTransport1.png", "chasseur1.png", "bombardier1.png"};
  public final static String[] tStrBaseUni2 = {"zombie2.png", "ninja2.png", "nosaure2.png", "magicalGirl2.png", "tank2.png", "fantassin2.png", "recon2.png", "bazooka2.png", "tankM2.png", "artillerie2.png", "vtb2.png", "antiAerien2.png", "dca2.png", "lanceMissiles2.png", "barge2.png", "sousMarin2.png", "destroyeur2.png", "cuirasse2.png", "helicoptere2.png", "helicoptereTransport2.png", "chasseur2.png", "bombardier2.png"};
  public final static String[] tStrBaseIcone = {"tankPrix.png", "fantassinPrix.png", "reconPrix.png", "bazookaPrix.png", "tankMPrix.png", "artilleriePrix.png", "vtbPrix.png", "antiAerienPrix.png", "dcaPrix.png", "lanceMissilesPrix.png",  "bargePrix.png", "sousMarinPrix.png", "destroyeurPrix.png", "cuirassePrix.png", "helicoPrix.png", "helicoTransportPrix.png", "chasseurPrix.png", "bombardierPrix.png"};
  public final static String[] tStrBaseIconeTropCher = {"tankTropCher.png", "fantassinTropCher.png", "reconTropCher.png", "bazookaTropCher.png", "tankMTropCher.png", "artillerieTropCher.png", "vtbTropCher.png", "antiAerienTropCher.png", "dcaTropCher.png", "lanceMissilesTropCher.png",  "bargeTropCher.png", "sousMarinTropCher.png", "destroyeurTropCher.png", "cuirasseTropCher.png", "helicoTropCher.png", "helicoTransportTropCher.png", "chasseurTropCher.png", "bombardierTropCher.png"};
  public final static String[] tStrBaseFond = {"vert.jpg", "rouge.jpg", "bleu.jpg", "point.png"};
  public final static String[] tStrBaseDegats = {"0.png", "0.png", "0.png", "0.png", "0.png", "5-1.png", "5-2.png", "5-3.png", "5-4.png", "0.png", "10-1.png", "10-2.png", "10-3.png", "10-4.png", "O.png","0.png", "0.png", "0.png", "0.png", "0.png", "20-1.png", "20-2.png", "20-3.png", "20-4.png", "0.png","0.png", "0.png", "0.png", "0.png", "0.png", "30-1.png", "30-2.png", "30-3.png", "30-4.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "40-1.png", "40-2.png", "40-3.png", "40-4.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "0.png", "100-1.png", "100-2.png", "100-3.png", "100-4.png", "0.png" };

  public final static String[] tStrUni1;
  public final static String[] tStrUni2;
  public final static String[] tStrFond;
  public final static String[] tStrIcone;
  public final static String[] tStrIconeTropCher;
  public final static String[] tStrDegats;

  public final static String[] tStrTer2; //images dans le dossier propre a chaque terrain
  public final static String[] tStrDossierTer; //chemin pour aller dans le dossier du terrain qu'on veut

  public final static File[] tStrTerFile;
  public final static File[] tStrUniFile1;
  public final static File[] tStrUniFile2;
  public final static File[] tStrFondFile;
  public final static File[] tStrIconeFile;
  public final static File[] tStrIconeTropCherFile;
  public final static File[] tStrDegatsFile;

  public final static BufferedImage[] tImTer;
  public final static BufferedImage[] tImUni1;
  public final static BufferedImage[] tImUni2;
  public final static BufferedImage[] tImFond;
  public final static BufferedImage[] tImIcone;
  public final static BufferedImage[] tImIconeTropCher;
  public final static BufferedImage[] tImDegats;

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
    for (int i = 0; i < tStrTer2.length; i++) {
      tStrDossierTer[i] = pathToTerrains + pathToImages2[i];
      tStrTer2[i] = tStrDossierTer[i] + tStrBaseTer[i];
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

    // Remplissage du tableau avec les images des unites v1
    tStrUni1 = new String[tStrBaseUni1.length];
    tStrUniFile1 = new File[tStrUni1.length];
    tImUni1 = new BufferedImage[tStrUniFile1.length];
    for (int i = 0; i < tStrUni1.length; i++) {
      if (i < 4)
        tStrUni1[i] = pathToGeneraux + tStrBaseUni1[i];
      else
        tStrUni1[i] = pathToUnites + tStrBaseUni1[i];
      tStrUniFile1[i] = new File(tStrUni1[i]);
      try {
        tImUni1[i] = ImageIO.read(tStrUniFile1[i]);
      } catch (IOException e) {}
    }

    // Remplissage du tableau avec les images des unites v2
    tStrUni2 = new String[tStrBaseUni2.length];
    tStrUniFile2 = new File[tStrUni2.length];
    tImUni2 = new BufferedImage[tStrUniFile2.length];
    for (int i = 0; i < tStrUni2.length; i++) {
      if (i < 4)
        tStrUni2[i] = pathToGeneraux + tStrBaseUni2[i];
      else
        tStrUni2[i] = pathToUnites + tStrBaseUni2[i];
      tStrUniFile2[i] = new File(tStrUni2[i]);
      try {
        tImUni2[i] = ImageIO.read(tStrUniFile2[i]);
      } catch (IOException e) {}
    }

    // Remplissage du tableau avec les icones des unites
    tStrIcone = new String[tStrBaseIcone.length];
    tStrIconeFile = new File[tStrIcone.length];
    tImIcone = new BufferedImage[tStrIconeFile.length];
    for (int i = 0; i < tStrIcone.length; i++) {
      tStrIcone[i] = pathToIcones + tStrBaseIcone[i];
      tStrIconeFile[i] = new File(tStrIcone[i]);
      try {
        tImIcone[i] = ImageIO.read(tStrIconeFile[i]);
      } catch (IOException e) {}
    }

    // Remplissage du tableau avec les icones des unites trop cheres
    tStrIconeTropCher = new String[tStrBaseIconeTropCher.length];
    tStrIconeTropCherFile = new File[tStrIconeTropCher.length];
    tImIconeTropCher = new BufferedImage[tStrIconeTropCherFile.length];
    for (int i = 0; i < tStrIconeTropCher.length; i++) {
      tStrIconeTropCher[i] = pathToIcones + tStrBaseIconeTropCher[i];
      tStrIconeTropCherFile[i] = new File(tStrIconeTropCher[i]);
      try {
        tImIconeTropCher[i] = ImageIO.read(tStrIconeTropCherFile[i]);
      } catch (IOException e) {}
    }

    // Remplissage du tableau avec les fonds colores (deplacement, portee)
    tStrFond = new String[tStrBaseFond.length];
    tStrFondFile = new File[tStrFond.length];
    tImFond = new BufferedImage[tStrFondFile.length];
    for (int i = 0; i < tStrFond.length; i++) {
      tStrFond[i] = pathToTerrains + pathToImages2[4] + tStrBaseFond[i];
      tStrFondFile[i] = new File(tStrFond[i]);
      try {
        tImFond[i] = ImageIO.read(tStrFondFile[i]);
      } catch (IOException e) {}
    }
    // Remplissage du tableau avec les images des degats reçus
    tStrDegats = new String[tStrBaseDegats.length];
    tStrDegatsFile = new File[tStrDegats.length];
    tImDegats = new BufferedImage[tStrDegatsFile.length];
    for (int i = 0; i < tStrDegats.length; i++) {
      tStrDegats[i] = pathToDegats + tStrBaseDegats[i];
      tStrDegatsFile[i] = new File(tStrDegats[i]);
      try {
        tImDegats[i] = ImageIO.read(tStrDegatsFile[i]);
      } catch (IOException e) {}
    }

  }

  public final static BufferedImage noir = opacifier(tImTer[4], 1f);
  public final static BufferedImage gris = opacifier(tImTer[4], 0.70f);
  public final static BufferedImage vert = opacifier(tImFond[0], 0.60f);
  public final static BufferedImage rouge = opacifier(tImFond[1], 0.60f);
  public final static BufferedImage bleu = opacifier(tImFond[2], 0.60f);
  public final static BufferedImage point = opacifier(tImFond[3], 0.70f);


  public static BufferedImage opacifier(BufferedImage img, float opacite) {
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
