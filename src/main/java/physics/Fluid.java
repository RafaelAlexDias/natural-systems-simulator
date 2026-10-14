package physics;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Base class for fluids that apply a drag force to a moving body.
 */
public abstract class Fluid {
    private float density;

    // Creates a fluid with the given density.
    protected Fluid(float density) {
        this.density = density;
    }

    // Computes the drag force opposing the body's velocity.
    public PVector drag(Body b) {
        float area = PApplet.pow(b.radius, 2.0f) * PApplet.PI;
        float mag = b.vel.mag();

        return PVector.mult(b.vel, -0.5f*density*area*mag);
    }

}
