package physics;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

/**
 * Interactive demo of a {@link RigidBody} driven by a {@link MotionControl}
 * whose mode (position, velocity or force) is switched with the P, V and F keys.
 */
public class ControlGUIApp implements IProcessingApp {

    private RigidBody rb;
    private MotionControl mc;
    private float mass = 1f;
    private RigidBody.ControlType ct = RigidBody.ControlType.POSITION;

    @Override
    public void setup(PApplet parent) {
        // Create the rigid body and its controller.
        rb = new RigidBody(mass);
        mc = new MotionControl(ct, rb);
    }

    @Override
    public void draw(PApplet parent, float dt) {
        // Clear the window.
        parent.background(255);

        parent.translate(parent.width/2, parent.height/2);
        // Advance the rigid body.
        rb.move(dt, ct);
        // Draw the rigid body and the controller.
        rb.display(parent);
        mc.display(parent);
    }

    @Override
    public void keyPressed(PApplet parent) {
        // Switch the control mode according to the pressed key.
        if (parent.key == 'p') {
            ct = RigidBody.ControlType.POSITION;
        }
        if (parent.key == 'v') {
            ct = RigidBody.ControlType.VELOCITY;
        }
        if (parent.key == 'f') {
            ct = RigidBody.ControlType.FORCE;
        }
        rb = new RigidBody(mass);
        mc = new MotionControl(ct, rb);
    }

    @Override
    public void mousePressed(PApplet parent) {
        // Set the control vector from the click position.
        float x = parent.mouseX - parent.width/2;
        float y = parent.mouseY - parent.height/2;
        mc.setVector(new PVector(x, y));
    }

    @Override
    public void mouseDragged(PApplet parent) {}
}
