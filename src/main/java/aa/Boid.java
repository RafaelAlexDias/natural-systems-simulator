package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

/**
 * An autonomous agent ("bird-oid object") that moves using a weighted
 * combination of steering behaviors. Boids also carry DNA parameters and a
 * vision system used by those behaviors.
 */
public class Boid extends Body {

    private SubPlot plt;
    protected String shapeString;
    protected PShape shape;
    protected DNA dna;
    protected Eye eye;
    protected List<Behavior> behaviors;
    protected float phiWander;
    private double[] window;
    private float sumWeights;
    private boolean dead = false;

    /**
     * Creates a new boid with a solid color shape.
     *
     * @param pos the initial position
     * @param mass the boid's mass
     * @param radius the boid's radius
     * @param color the fill color
     * @param p the Processing applet
     * @param plt the subplot for coordinate mapping
     */
    protected Boid(PVector pos, float mass, float radius,
                   int color, PApplet p, SubPlot plt) {
        super(pos, new PVector(), mass, radius, color);
        dna = new DNA();
        behaviors = new ArrayList<Behavior>();
        this.plt = plt;
        window = plt.getWindow();
        setShape(p, plt);
    }

    /**
     * Creates a new boid with a custom SVG shape.
     *
     * @param pos the initial position
     * @param mass the boid's mass
     * @param radius the boid's radius
     * @param shapeString the path to the shape file
     * @param p the Processing applet
     * @param plt the subplot for coordinate mapping
     */
    protected Boid(PVector pos, float mass, float radius,
                   String shapeString, PApplet p, SubPlot plt) {
        super(pos, new PVector(), mass, radius, shapeString);
        dna = new DNA();
        behaviors = new ArrayList<Behavior>();
        this.shapeString = shapeString;
        this.shape = p.loadShape(shapeString);
        this.plt = plt;
        window = plt.getWindow();
    }

    /**
     * Randomly mutates behavior weights for behaviors that support mutation.
     * Currently applied to AvoidObstacle behaviors.
     */
    public void mutateBehaviors() {
        for(Behavior behavior : behaviors) {
            if(behavior instanceof AvoidObstacle) {
                behavior.weight += DNA.random(-0.5f, 0.5f);
                behavior.weight = Math.max(0, behavior.weight);
            }
        }
        updateSumWeights();
    }

    public List<Behavior> getBehaviors() {
        return behaviors;
    }

    public DNA getDNA() {
        return dna;
    }

    /**
     * Sets the boid's vision system.
     *
     * @param eye the eye instance to attach
     */
    public void setEye(Eye eye) {
        this.eye = eye;
    }

    /**
     * Returns the boid's vision system.
     *
     * @return the eye instance
     */
    public Eye getEye() {
        return eye;
    }

    /**
     * Returns the boid's radius.
     *
     * @return the radius
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Sets the boid's shape color and regenerates the shape.
     *
     * @param p the Processing applet
     * @param plt the subplot for coordinate mapping
     * @param color the new fill color
     */
    public void setShape(PApplet p, SubPlot plt, int color) {
        this.color = color;
        setShape(p, plt);
    }

    /**
     * Creates and sets the boid's triangular shape based on its radius and subplot scale.
     *
     * @param p the Processing applet
     * @param plt the subplot for coordinate mapping
     */
    public void setShape(PApplet p, SubPlot plt) {
        float[] rr = plt.getVectorCoord(radius, radius);
        shape = p.createShape();
        shape.beginShape();
        shape.noStroke();
        shape.fill(color);
        shape.vertex(-rr[0], rr[0]/2);
        shape.vertex(rr[0], 0);
        shape.vertex(-rr[0], -rr[0]/2);
        shape.vertex(-rr[0], 0);
        shape.endShape(PConstants.CLOSE);
    }

    /**
     * Recalculates the sum of all behavior weights.
     */
    private void updateSumWeights() {
        sumWeights = 0;
        for (Behavior behavior : behaviors) {
            sumWeights += behavior.getWeight();
        }
    }

    /**
     * Adds a steering behavior to this boid.
     *
     * @param behavior the behavior to add
     */
    public void addBehavior(Behavior behavior) {
        behaviors.add(behavior);
        updateSumWeights();
    }

    /**
     * Removes a steering behavior from this boid if present.
     *
     * @param behavior the behavior to remove
     */
    public void removeBehavior(Behavior behavior) {
        if (behaviors.contains(behavior)) {
            behaviors.remove(behavior);
        }
        updateSumWeights();
    }

    /**
     * Applies a single behavior by index and updates the boid's movement.
     *
     * @param i the index of the behavior to apply
     * @param dt the time step
     */
    public void applyBehavior(int i, float dt) {
        if (eye!= null) eye.look();
        Behavior behavior = behaviors.get(i);
        PVector vd = behavior.getDesiredVelocity(this);
        move(dt, vd);
    }

    /**
     * Applies all behaviors with their respective weights and updates the boid's movement.
     *
     * @param dt the time step
     */
    public void applyBehaviors(float dt) {
        if (eye!= null) eye.look();

        PVector vd = new PVector();
        for (Behavior behavior : behaviors) {
            PVector vdd = behavior.getDesiredVelocity(this);
            vdd.mult(behavior.getWeight()/sumWeights);
            vd.add(vdd);
        }
        move(dt, vd);
    }

    /**
     * Calculates the distance from this boid to the given target.
     *
     * @param target the target body
     * @return the distance between positions
     */
    public float distanceToTarget(Body target) {
        return PVector.dist(this.pos, target.getPos());
    }

    /**
     * Increases the boid's maximum speed by a fixed amount.
     */
    public void increaseSpeed() {
        dna.maxSpeed += 1;
    }

    /**
     * Decreases the boid's maximum speed by a fixed amount, clamped to zero.
     */
    public void decreaseSpeed() {
        dna.maxSpeed -= 1;
        if (dna.maxSpeed < 0) {
            dna.maxSpeed = 0;
        }
    }

    /**
     * Moves the boid according to the given desired velocity and time step.
     * Handles steering forces, wrapping around the simulation window.
     *
     * @param dt the time step
     * @param vd the desired velocity
     */
    private void move(float dt, PVector vd) {
        vd.normalize().mult(dna.maxSpeed);
        PVector fs = PVector.sub(vd, vel);
        applyForce(fs.limit(dna.maxForce));
        super.move(dt);
        if (pos.x < window[0]) {
            pos.x += window[1] - window[0];
        }
        if (pos.y < window[2]) {
            pos.y += window[3] - window[2];
        }
        if (pos.x >= window[1]) {
            pos.x -= window[1] - window[0];
        }
        if (pos.y >= window[3]) {
            pos.y -= window[3] - window[2];
        }
    }

    /**
     * Displays the boid on the canvas if it is alive.
     */
    @Override
    public void display(PApplet p, SubPlot plt) {
        if (!dead) {
            p.pushMatrix();
            float[] pp = plt.getPixelCoord(pos.x, pos.y);
            p.shape(shape, pp[0] - 10f, pp[1] - 10f, 20f, 20f);
            p.popMatrix();
        }
    }

    /**
     * Checks if the boid is dead.
     *
     * @return true if dead, false otherwise
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Sets the boid's alive/dead state.
     *
     * @param b true to mark as dead, false otherwise
     */
    public void setDead(boolean b) {
        dead = b;
    }

}
