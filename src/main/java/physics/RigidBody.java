package physics;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * A simple point body whose position, velocity or force can be driven by a
 * {@link MotionControl}.
 */
public class RigidBody {
    public enum ControlType {
        POSITION,
        VELOCITY,
        FORCE
    }
    private PVector pos;
    private PVector vel;
    private PVector acc;
    private float mass;

    // Creates a rigid body of the given mass at the origin.
    public RigidBody(float mass) {
        pos = new PVector();
        vel = new PVector();
        acc = new PVector();
        this.mass = mass;
    }

    // Sets the position directly.
    public void setPos(PVector pos) {
        this.pos = pos;
    }

    // Sets the velocity directly.
    public void setVel(PVector vel) {
        this.vel = vel;
    }

    // Sets the acceleration from a force (acc = force / mass).
    public void applyForce(PVector force) {
        this.acc = PVector.div(force, mass);
    }

    // Integrates the body's state according to the control type.
    public void move(float dt, ControlType ct) {
        switch (ct) {
            case POSITION:
                break;
            case VELOCITY:
                pos.add(PVector.mult(vel, dt));
                break;
            case FORCE:
                pos.add(PVector.mult(vel, dt));
                vel.add(PVector.mult(acc, dt));
                break;
        }
    }

    // Draws the body as a circle.
    public void display(PApplet p) {
        p.circle(pos.x, pos.y, 30);
    }
}
