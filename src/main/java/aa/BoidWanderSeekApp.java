package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

import static aa.DNA.random;

/**
 * App where a boid combines wander and seek behaviors, chasing a target that
 * respawns at a random position once it gets close enough.
 */
public class BoidWanderSeekApp implements IProcessingApp {

    private Boid b;
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;
    private Body target;
    private List<Body> allTrackingBodies;
    private float minBoidTargetDistance = 2.0f;

    /**
     * Sets up the simulation with wander and seek behaviors.
     */
    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        b = new Boid(new PVector(), 1, 0.5f, parent.color(0), parent, plt);
        b.addBehavior(new Seek(1f));
        b.addBehavior(new Wander(1f));
        target = new Body(new PVector(), new PVector(), 1f, 0.2f, parent.color(255, 0, 0));
        allTrackingBodies = new ArrayList<Body>();
        allTrackingBodies.add(target);
        Eye eye = new Eye(b, allTrackingBodies);
        b.setEye(eye);
    }

    /**
     * Updates and renders the simulation, respawning the target when reached.
     */
    @Override
    public void draw(PApplet parent, float dt) {
        parent.background(255);
        b.applyBehaviors(dt);
        float distanceToTarget = b.distanceToTarget(target);
        if (distanceToTarget < minBoidTargetDistance) {
            target.setPos(new PVector(random((float)window[0], (float)window[1]),
                    random((float)window[2], (float)window[3])));
        }
        b.display(parent, plt);
        target.display(parent, plt);
    }

    @Override
    public void keyPressed(PApplet parent) {}

    @Override
    public void mousePressed(PApplet parent) {}

    @Override
    public void mouseDragged(PApplet parent) {}
}
