package physics;

import processing.core.PVector;

/**
 * Base class for anything that moves under forces: it integrates velocity and
 * acceleration over time using simple Newtonian mechanics.
 */
public abstract class Mover {
    protected PVector pos;
    protected PVector acc;
    protected PVector vel;
    protected float mass;
    private static double G = 6.67e-11;

    // Creates a mover at a position with an initial velocity and mass.
    protected Mover(PVector pos, PVector vel, float mass) {
        this.pos = pos.copy();
        this.vel = vel;
        this.mass = mass;
        acc = new PVector();
    }

    // Returns the gravitational force this mover exerts on another mover.
    public PVector attraction(Mover m) {
        PVector r = PVector.sub(pos, m.pos);
        float dist = r.mag();
        float strenght = (float) (G * mass * m.mass / Math.pow(dist, 2));

        return r.normalize().mult(strenght);
    }

    // Accumulates a force (converted to acceleration using the mass).
    public void applyForce(PVector force) {
        acc.add(PVector.div(force, mass));
    }

    // Advances the position and velocity by one time step dt.
    public void move(float dt) {
        vel.add(acc.mult(dt));
        pos.add(PVector.mult(vel, dt));
        acc.mult(0);
    }

    // Sets the position.
    public void setPos(PVector pos) {
        this.pos = pos;
    }

    // Sets the velocity.
    public void setVel(PVector vel) {
        this.vel = vel;
    }

    // Returns the position.
    public PVector getPos() {
        return pos;
    }

    // Returns the velocity.
    public PVector getVel() {
        return vel;
    }
}
