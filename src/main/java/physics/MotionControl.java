package physics;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Turns a pointer vector into position, velocity or force commands applied to a
 * {@link RigidBody}.
 */
public class MotionControl {
    private RigidBody rb;
    private RigidBody.ControlType ct;
    private PVector vector;

    // Creates a controller for the body using the given control type.
    public MotionControl(RigidBody.ControlType ct, RigidBody rb) {
        this.ct = ct;
        this.rb = rb;
        vector = new PVector();
    }

    // Applies the vector to the body according to the current control type.
    public void setVector(PVector vector) {
        this.vector = vector;
        switch(ct) {
            case POSITION:
                rb.setPos(vector);
                break;
            case VELOCITY:
                rb.setVel(vector);
                break;
            case FORCE:
                rb.applyForce(vector);
                break;
        }
    }

    // Draws the axes and the current control vector.
    public void display(PApplet p) {
        p.strokeWeight(1);
        p.line(-p.width/2, 0, p.width/2, 0);
        p.line(0, -p.height/2, 0, p.height/2);
        p.textSize(24);
        p.fill(0);
        p.text("Control by " + ct.toString(), -140, -280);

        p.strokeWeight(3);
        p.line(0, 0, vector.x, vector.y);
    }
}
