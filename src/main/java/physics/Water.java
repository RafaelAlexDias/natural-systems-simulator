package physics;

import processing.core.PApplet;
import tools.SubPlot;

/**
 * A rectangular body of water that applies drag to submerged movers.
 */
public class Water extends Fluid {
    private float waterHeight;
    private int color;

    // Creates water up to the given height, drawn in the given colour.
    protected Water(float waterHeight, int color) {
        super(1000.0f);
        this.waterHeight = waterHeight;
        this.color = color;
    }

    // Returns whether the mover is below the water surface.
    public boolean isInside(Mover m) {
        return (m.getPos().y <= waterHeight);
    }

    // Draws the water rectangle.
    public void display(PApplet p, SubPlot plt) {
        p.pushStyle();
        p.noStroke();
        p.fill(color);
        float[] pp = plt.getBox(0, 0, plt.getWindow()[1], waterHeight);
        p.rect(pp[0], pp[1], pp[2], pp[3]);
        p.popStyle();
    }
}
