package physics;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

/**
 * A body with a mass, radius and either a solid colour or a shape.
 *
 * <p>It moves under applied forces and can draw itself on a {@link SubPlot}.
 */
public class Body extends Mover{
    protected int color;
    protected float radius;
    protected String shape;

    // Creates a body rendered as a solid circle of the given colour.
    public Body(PVector pos, PVector acc, float mass, float radius, int color) {
        super(pos, acc, mass);
        this.radius = radius;
        this.color = color;
    }

    // Creates a body rendered from an external shape given by name.
    public Body(PVector pos, PVector acc, float mass, float radius, String shape) {
        super(pos, acc, mass);
        this.radius = radius;
        this.shape = shape;
    }

    // Creates a massless body, e.g. a static reference point.
    public Body(PVector pos) {
        super(pos, new PVector(), 0f);
    }

    // Draws the body as a filled circle at its current position.
    public void display(PApplet p, SubPlot plt) {
        p.pushStyle();
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        float[] r = plt.getVectorCoord(radius, radius);
        p.noStroke();
        p.fill(color);
        p.circle(pp[0], pp[1], 2*r[0]);
        p.popStyle();
    }
}
