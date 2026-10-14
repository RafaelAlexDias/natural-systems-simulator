package aa;

import physics.Body;
import processing.core.PApplet;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.List;

/**
 * Debugging app that renders a flock and highlights the boids currently visible
 * to one selected boid, visualizing the vision system.
 */
public class DebuggingApp implements IProcessingApp {

    private Boid boid;
    private Flock flock;
    private float[] sacWeights  = {1f, 1f, 1f};
    private double[] window = {-10, 10, -10, 10};
    private float[] view = {0, 0, 1, 1};
    private SubPlot plt;

    /**
     * Sets up the debugging application with a flock and a selected boid for inspection.
     */
    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, view, parent.width, parent.height);
        flock = new Flock(200, 0.1f, 0.3f, parent.color(163, 13, 229), sacWeights, parent, plt);
        boid = flock.getBoid(4);
    }

    /**
     * Renders the flock and highlights boids visible to the selected boid.
     */
    @Override
    public void draw(PApplet parent, float dt) {
        float[] bb = plt.getBoundingBox();
        parent.fill(255, 128);
        parent.rect(bb[0], bb[1], bb[2], bb[3]);

        for (Boid b : flock.getBoids()) {
            b.getEye().look();
        }

        List<Body> boidsInSight = boid.getEye().getBoidsInSight();
        for (Boid b : flock.getBoids()) {
            if (boidsInSight.contains(b)) {
                b.setShape(parent, plt, parent.color(229, 0, 33));
            } else {
                b.setShape(parent, plt, parent.color(163, 13, 229));
            }
        }

        flock.applyBehavior(dt);
        flock.display(parent, plt);
        boid.getEye().display(parent, plt);
    }

    @Override
    public void keyPressed(PApplet parent) {}

    @Override
    public void mousePressed(PApplet parent) {}

    @Override
    public void mouseDragged(PApplet parent) {}
}
