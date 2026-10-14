package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

/**
 * App where a predator boid pursues flock members, switching its target to
 * whichever flock member comes within range.
 */
public class FlockPredatorApp implements IProcessingApp {

    private Flock flock;
    private Boid predator;
    private float[] sacWeights  = {1f, 1f, 1f, 1f};
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private List<Body> allTrackingBodies;
    private float minBoidTargetDistance = 2f;
    private float distanceToTarget;
    private SubPlot plt;

    /**
     * Sets up the flock, the predator, and the predator's vision.
     */
    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        flock = new Flock(10, 0.1f, 0.3f, parent.color(0, 100, 200), sacWeights, parent, plt);
        predator = new Boid(new PVector(), 1, 0.5f, parent.color(0), parent, plt);
        predator.addBehavior(new Seek(1f));
        allTrackingBodies = new ArrayList<Body>();
        allTrackingBodies.add(flock.getBoid(1));
        Eye eye = new Eye(predator, allTrackingBodies);
        predator.setEye(eye);
    }

    /**
     * Updates the predator's target, applies behaviors, and renders everything.
     */
    @Override
    public void draw(PApplet parent, float dt) {
        float[] bb = plt.getBoundingBox();
        parent.fill(255, 64);
        parent.rect(bb[0], bb[1], bb[2], bb[3]);

        for (int i = 0; i < flock.getNBoids(); i++) {
            distanceToTarget = predator.distanceToTarget(flock.getBoid(i));
            // When a flock member is close enough, the predator switches to chasing it.
            if (distanceToTarget < minBoidTargetDistance) {
                allTrackingBodies.clear();
                allTrackingBodies.add(flock.getBoid(i));
            }
        }
        flock.applyBehavior(dt);
        predator.applyBehaviors(dt);
        flock.display(parent, plt);
        predator.display(parent, plt);
    }

    @Override
    public void keyPressed(PApplet parent) {}

    @Override
    public void mousePressed(PApplet parent) {}

    @Override
    public void mouseDragged(PApplet parent) {}
}
