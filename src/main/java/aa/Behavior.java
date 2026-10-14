package aa;

import processing.core.PVector;

/**
 * Base class for steering behaviors used by boids.
 * Each behavior computes a desired velocity and has an associated weight.
 */
public abstract class Behavior {
    protected float weight;

    /**
     * Creates a new behavior with the specified weight.
     *
     * @param weight the steering weight applied to this behavior
     */
    public Behavior(float weight) {
        this.weight = weight;
    }

    /**
     * Computes and returns the desired velocity for the given boid.
     *
     * @param me the boid applying this behavior
     * @return the desired velocity vector
     */
    public PVector getDesiredVelocity(Boid me) {
        return null;
    }

    /**
     * Sets the weight of this behavior.
     *
     * @param weight the new steering weight
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * Gets the weight of this behavior.
     *
     * @return the current steering weight
     */
    public float getWeight() {
        return weight;
    }
}
