package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

/**
 * A group of boids that flock together using separation, alignment, and cohesion.
 */
public class Flock {
    private List<Boid> boids;
    private int nboids;

    /**
     * Creates a flock of boids with the specified steering behaviors.
     *
     * @param nboids the number of boids in the flock
     * @param mass the mass of each boid
     * @param radius the radius of each boid
     * @param color the fill color of each boid
     * @param sacWeights the weights for separation, alignment, and cohesion
     * @param p the Processing applet
     * @param plt the subplot for coordinate mapping
     */
    public Flock(int nboids, float mass, float radius, int color, float[] sacWeights,
                 PApplet p, SubPlot plt) {
        this.nboids = nboids;
        double[] w = plt.getWindow();
        boids = new ArrayList<Boid>();
        for (int i = 0; i < nboids; i++) {
            float x = p.random((float)w[0], (float)w[1]);
            float y = p.random((float)w[2], (float)w[3]);
            Boid b = new Boid(new PVector(x, y), mass, radius, color, p, plt);
            b.addBehavior(new Separate(sacWeights[0]));
            b.addBehavior(new Align(sacWeights[1]));
            b.addBehavior(new Cohesion(sacWeights[2]));
            boids.add(b);
        }
        List<Body> bodies = boidList2BodyList(boids);
        for (Boid b : boids) {
            b.setEye(new Eye(b, bodies));
        }
    }

    /**
     * Returns the list of boids in this flock.
     *
     * @return the boids
     */
    public List<Boid> getBoids() {
        return this.boids;
    }

    /**
     * Converts a list of boids into a list of bodies for vision tracking.
     *
     * @param boids the boids to convert
     * @return the equivalent list of bodies
     */
    private List<Body> boidList2BodyList(List<Boid> boids) {
        List<Body> bodies = new ArrayList<Body>();
        for(Boid b : boids) {
            bodies.add(b);
        }
        return bodies;
    }

    /**
     * Applies all behaviors to every boid in the flock.
     *
     * @param dt the time step
     */
    public void applyBehavior(float dt) {
        for(Boid b : boids) {
            b.applyBehaviors(dt);
        }
    }


    /**
     * Returns the total number of boids in the flock.
     *
     * @return the boid count
     */
    public int getNBoids() {
        return this.nboids;
    }

    /**
     * Returns the boid at the given index.
     *
     * @param i the index of the boid
     * @return the boid at that index
     */
    public Boid getBoid(int i) {
        return boids.get(i);
    }

    /**
     * Draws every boid in the flock.
     */
    public void display(PApplet p, SubPlot plt) {
        for (Boid b :boids) {
            b.display(p, plt);
        }
    }
}
