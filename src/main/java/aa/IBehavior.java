package aa;

import processing.core.PVector;

/**
 * Contract for steering behaviors that compute a desired velocity for a boid
 * and expose a configurable weight.
 */
public interface IBehavior {
    /**
     * Computes the desired velocity for the given boid.
     *
     * @param me the boid applying this behavior
     * @return the desired velocity vector
     */
    public PVector getDesiredVelocity(Boid me);

    /**
     * Sets the weight of this behavior.
     *
     * @param weight the new steering weight
     */
    public void setWeight(float weight);

    /**
     * Gets the weight of this behavior.
     *
     * @return the current steering weight
     */
    public float getWeight();
}
