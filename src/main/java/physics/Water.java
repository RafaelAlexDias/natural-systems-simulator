package physics;

import processing.core.PApplet;
import tools.SubPlot;

public class Water extends Fluid {
    private float waterHeight;
    private int color;

    // Construtor do "Fluid" "Water"
    protected Water(float waterHeight, int color) {
        super(1000.0f);
        this.waterHeight = waterHeight;
        this.color = color;
    }

    // Método que verifica se um "Mover" está dentro da área da água
    public boolean isInside(Mover m) {
        return (m.getPos().y <= waterHeight);
    }

    // Método "display" para a água
    public void display(PApplet p, SubPlot plt) {
        p.pushStyle();
        p.noStroke();
        p.fill(color);
        float[] pp = plt.getBox(0, 0, plt.getWindow()[1], waterHeight);
        p.rect(pp[0], pp[1], pp[2], pp[3]);
        p.popStyle();
    }
}
