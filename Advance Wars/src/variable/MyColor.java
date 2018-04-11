package src.variable;

import java.awt.Color;

// Cette classe permet d'avoir une classe couleur manipulable, notamment d'avoir le nom de chaque couleur.
// Nous avions également besoin de divers constructeur suplémentaire afin de simplifier le travail.
// La classe Color ne permet en effet pas de créer des couleurs avec des alphas efficacement.

@SuppressWarnings("serial")
public class MyColor extends Color {
  // Le nom de la couleur.
  private String colorName;

  // *************** Constructeurs ***************
  // Divers Constructeurs, reprenant la plupart des Constructeurs de Color et en ajoutant la variable Colorname.
  public MyColor(int rgb, String colorName) {
    super(rgb);
    this.colorName = colorName;
  }

  public MyColor(int rgb, int alpha, String colorName) {
    super(getRed(rgb), getGreen(rgb), getBlue(rgb), alpha);
    this.colorName = colorName;
  }

  public MyColor(int rgba, boolean hasalpha, String colorName) {
    super(rgba, hasalpha);
    this.colorName = colorName;
  }

  public MyColor(int r, int g, int b, String colorName) {
    super(r, g, b);
    this.colorName = colorName;
  }

  public MyColor(float r, float g, float b, String colorName) {
    super(r, g, b);
    this.colorName = colorName;
  }

  public MyColor(int r, int g, int b, int a, String colorName) {
    super(r, g, b, a);
    this.colorName = colorName;
  }

  public MyColor(float r, float g, float b, float a, String colorName) {
    super(r, g, b, a);
    this.colorName = colorName;
  }

  public MyColor(MyColor original, int alpha) {
    this(original.getRGB(), alpha, original.getColorName());
  }

  public MyColor(Color original, int alpha, String s) {
    this(original.getRGB(), alpha, s);
  }

  public MyColor(Color original, int alpha) {
    this(original.getRGB(), alpha, "");
  }

  // *************** Fin des Constructeurs ***************
  // *************** Getter ***************

  public String getColorName() {
    return colorName;
  }


  // *************** Fonctions utilitaires ***************
  // Renvoie la partie contenant la couleur rouge d'un entier codant une couleur.
  public static int getRed(int rgb) {
    return (rgb >> 16) & 0x000000FF;
  }

  // Renvoie la partie contenant la couleur verte d'un entier codant une couleur.
  public static int getGreen(int rgb) {
    return (rgb >> 8) & 0x000000FF;
  }

  // Renvoie la partie contenant la couleur bleue d'un entier codant une couleur.
  public static int getBlue(int rgb) {
    return (rgb) & 0x000000FF;
  }

  // Vérifie que deux couleurs ont la même Couleur (en comparant uniquement les entiers, pas les noms).
  public boolean equals(MyColor m) {
    return (this.getRGB() == m.getRGB());
  }
}
