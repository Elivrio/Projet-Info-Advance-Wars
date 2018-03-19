package src.vue;

import java.awt.Color;


  class MyColor extends Color {
    private String colorName;

    public MyColor(int rgb, String colorName) {
      super(rgb);
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

    public String getColorName() {
      return colorName;
    }
  }
