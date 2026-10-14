package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

/**
 * App demonstrating a single boid seeking a target, with keyboard controls for
 * increasing or decreasing the boid's maximum speed.
 */
public class BoidAceleradorTravaoApp implements IProcessingApp {
    private Boid b;
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;
    private Body target;
    private List<Body> allTrackingBodies;

    /**
     * Sets up the simulation: subplot, boid, target, and vision.
     */
    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        b = new Boid(new PVector(), 1, 0.5f, parent.color(0), parent, plt);
        b.addBehavior(new Seek(1f));
        target = new Body(new PVector(), new PVector(), 1f, 0.2f, parent.color(255, 0, 0));
        allTrackingBodies = new ArrayList<Body>();
        allTrackingBodies.add(target);
        Eye eye = new Eye(b, allTrackingBodies);
        b.setEye(eye);
    }

    /**
     * Updates and renders the simulation each frame.
     */
    @Override
    public void draw(PApplet parent, float dt) {
        parent.background(255);
        b.applyBehaviors(dt);
        b.display(parent, plt);
        target.display(parent, plt);
    }

    /**
     * Handles key press events to adjust boid speed.
     */
    @Override
    public void keyPressed(PApplet parent) {
        if (parent.key == '+') {
            b.increaseSpeed();
        }
        else if (parent.key == '-') {
            b.decreaseSpeed();
        }
    }

    /**
     * Moves the target to the mouse position on mouse press.
     */
    @Override
    public void mousePressed(PApplet parent) {
        double[] ww = plt.getWorldCoord(parent.mouseX, parent.mouseY);
        target.setPos(new PVector((float) ww[0], (float) ww[1]));
    }

    @Override
    public void mouseDragged(PApplet parent) {

    }
}
