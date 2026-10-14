package aa;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

/**
 * App where a flock flocks together while one selected boid can be steered
 * manually with the WASD keys.
 */
public class FlockWASDApp implements IProcessingApp {

    private Flock flock;
    private int numBoidControl = 0;
    private Boid boidControl;
    private float velocity = 3f;
    private float[] sacWeights  = {1f, 1f, 1f};
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;

    /**
     * Sets up the flock and selects the boid controlled by the keyboard.
     */
    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        flock = new Flock(10, 0.1f, 0.3f, parent.color(0, 100, 200), sacWeights, parent, plt);
        boidControl = flock.getBoid(numBoidControl);
    }

    /**
     * Updates and renders the flock.
     */
    @Override
    public void draw(PApplet parent, float dt) {
        float[] bb = plt.getBoundingBox();
        parent.fill(255, 64);
        parent.rect(bb[0], bb[1], bb[2], bb[3]);
        flock.applyBehavior(dt);
        flock.display(parent, plt);
    }

    /**
     * Applies a directional force to the controlled boid when WASD keys are pressed.
     */
    @Override
    public void keyPressed(PApplet parent) {
        if (parent.key == 'w' || parent.key == 'W') {
            boidControl.applyForce(new PVector(0, velocity));
        }
        if (parent.key == 'a' || parent.key == 'A') {
            boidControl.applyForce(new PVector(-velocity, 0));
        }
        if (parent.key == 's' || parent.key == 'S') {
            boidControl.applyForce(new PVector(0, -velocity));
        }
        if (parent.key == 'd' || parent.key == 'D') {
            boidControl.applyForce(new PVector(velocity, 0));
        }
    }

    @Override
    public void mousePressed(PApplet parent) {}

    @Override
    public void mouseDragged(PApplet parent) {}
}
